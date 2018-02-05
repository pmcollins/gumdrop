package gumdrop.test.server;

import gumdrop.common.CharIterator;
import gumdrop.server.nio.Accumulator;
import gumdrop.server.nio.Delimiter;
import gumdrop.server.nio.WordAccumulator;
import gumdrop.test.util.Test;

import java.util.LinkedList;
import java.util.Queue;

public class MultiParserTest extends Test {

  private static final String POST =
    "------WebKitFormBoundarynwAxopXoFg6rtPYX\r\n" +
    "Content-Disposition: form-data; name=\"foo\"; filename=\"hello.txt\"\r\n" +
    "Content-Type: text/plain\r\n" +
    "\r\n" +
    "hello!\n" +
    "\r\n" +
    "------WebKitFormBoundarynwAxopXoFg6rtPYX--\r\n";

  public static void main(String[] args) {
    new MultiParserTest().run();
  }

  @Override
  public void run() {
    dispositionLine();
  }

  private void dispositionLine() {
    String delimStr = "------WebKitFormBoundarynwAxopXoFg6rtPYX";
    CharIterator it = new CharIterator(POST);
    MultiParser multiParser = new MultiParser(delimStr);
    multiParser.parse(it);
  }

}

class MultiParser {

  private final Delimiter startBoundary;
  private final Delimiter crlf;
  private final KvAccumulator[] q;
  private final WordAccumulator content;

  MultiParser(String delimStr) {
    startBoundary = new Delimiter(delimStr);
    crlf = new Delimiter("\r\n");
    KvAccumulator cd = new KvAccumulator(": ", ";");
    KvAccumulator name = new KvAccumulator("=", ";");
    KvAccumulator fn = new KvAccumulator("=", "\r\n");
    KvAccumulator type = new KvAccumulator(": ", "\r\n");
    q = new KvAccumulator[] {cd, name, fn, type};
    content = new WordAccumulator("\r\n" + delimStr + "--");
  }

  void parse(CharIterator it) {
    startBoundary.match(it);
    startBoundary.skip(it);

    crlf.match(it);
    crlf.skip(it);

    for (KvAccumulator acc : q) {
      acc.match(it);
      acc.skip(it);
      it.mark();
    }

    crlf.match(it);
    crlf.skip(it);

    content.match(it);
    String contentVal = content.getVal();
    System.out.println("contentVal = [" + contentVal + "]");
  }

}

class KvAccumulator implements Accumulator {

  private final WordAccumulator w1;
  private final WordAccumulator w2;

  KvAccumulator(String delim1, String delim2) {
    w1 = new WordAccumulator(delim1);
    w2 = new WordAccumulator(delim2);
  }

  @Override
  public boolean match(CharIterator it) {
    w1.match(it);
    w1.skip(it);
    it.mark();
    w2.match(it);
    return true;
  }

  @Override
  public void skip(CharIterator it) {
    w2.skip(it);
  }

  String getKey() {
    return w1.getVal();
  }

  String getValue() {
    return w2.getVal();
  }

}
