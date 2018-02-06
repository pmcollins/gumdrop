package gumdrop.web;

import gumdrop.common.CharIterator;

public class MultiParser {

  public static byte[] parseSinglePart(String delimStr, CharIterator it) {
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

  public static String parseBoundary(String header) {
    // Content-Type: multipart/form-data; boundary=----WebKitFormBoundarynwAxopXoFg6rtPYX
    int i = header.indexOf('=');
    return header.substring(i + 1, header.length());
  }

}
