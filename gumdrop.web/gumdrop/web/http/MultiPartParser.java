package gumdrop.web.http;

import gumdrop.common.ByteIterator;
import gumdrop.common.Delimiter;
import gumdrop.common.Matcher;
import gumdrop.common.SubstringMatcher;

public class MultiPartParser {

  public static byte[] parseSinglePart(String delimStr, ByteIterator it) {
    Delimiter crlf = new Delimiter("\r\n");
    KvMatcher cd = new KvMatcher(": ", ";");
    KvMatcher name = new KvMatcher("=", ";");
    KvMatcher fn = new KvMatcher("=", "\r\n");
    KvMatcher type = new KvMatcher(": ", "\r\n");
    Matcher[] q = new Matcher[]{crlf, cd, name, fn, type, crlf};
    Delimiter startBoundary = new Delimiter("--" + delimStr);
    if (!startBoundary.match(it)) throw new IllegalStateException("start boundary didn't match");
    startBoundary.skip(it);
    for (Matcher acc : q) {
      if (!acc.match(it)) throw new IllegalStateException("accumulator didn't match");
      acc.skip(it);
      it.mark();
    }
    SubstringMatcher content = new SubstringMatcher("\r\n--" + delimStr + "--");
    content.match(it);
    return content.getSubArray();
  }

  public static String parseMultipartHeader(String header) {
    // e.g. Content-Type: multipart/form-data; boundary=----WebKitFormBoundarynwAxopXoFg6rtPYX
    int i = header.indexOf('=');
    return header.substring(i + 1);
  }

  private static class KvMatcher implements Matcher {

    private final SubstringMatcher key;
    private final SubstringMatcher value;

    KvMatcher(String delim1, String delim2) {
      key = new SubstringMatcher(delim1);
      value = new SubstringMatcher(delim2);
    }

    @Override
    public boolean match(ByteIterator it) {
      key.match(it);
      key.skip(it);
      it.mark();
      value.match(it);
      return true;
    }

    @Override
    public void skip(ByteIterator it) {
      value.skip(it);
    }

    String getKey() {
      return key.getSubstring();
    }

    String getValue() {
      return value.getSubstring();
    }

  }

}
