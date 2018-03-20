package gumdrop.web;

import gumdrop.common.builder.ByteIterator;

public class MultiPartParser {

  public static byte[] parseSinglePart(String delimStr, ByteIterator it) {
    Delimiter crlf = new Delimiter("\r\n");
    KvAccumulator cd = new KvAccumulator(": ", ";");
    KvAccumulator name = new KvAccumulator("=", ";");
    KvAccumulator fn = new KvAccumulator("=", "\r\n");
    KvAccumulator type = new KvAccumulator(": ", "\r\n");
    Accumulator[] q = new Accumulator[]{crlf, cd, name, fn, type, crlf};
    Delimiter startBoundary = new Delimiter("--" + delimStr);
    if (!startBoundary.match(it)) throw new IllegalStateException("start boundary didn't match");
    startBoundary.skip(it);
    for (Accumulator acc : q) {
      if (!acc.match(it)) throw new IllegalStateException("accumulator didn't match");
      acc.skip(it);
      it.mark();
    }
    WordAccumulator content = new WordAccumulator("\r\n--" + delimStr + "--");
    content.match(it);
    return content.getSubArray();
  }

  public static String parseMultipartHeader(String header) {
    // e.g. Content-Type: multipart/form-data; boundary=----WebKitFormBoundarynwAxopXoFg6rtPYX
    int i = header.indexOf('=');
    return header.substring(i + 1, header.length());
  }

  private static class KvAccumulator implements Accumulator {

    private final WordAccumulator key;
    private final WordAccumulator value;

    KvAccumulator(String delim1, String delim2) {
      key = new WordAccumulator(delim1);
      value = new WordAccumulator(delim2);
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
