package gumdrop.test.server;

import gumdrop.common.ByteIterator;
import gumdrop.server.nio.AttributeAccumulator;
import gumdrop.server.nio.AttributeCollectionAccumulator;
import gumdrop.test.util.Test;

import java.util.Map;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertFalse;
import static gumdrop.test.util.Asserts.assertTrue;

public class AttributeAccumulatorTest extends Test {

  public static void main(String[] args) {
    new AttributeAccumulatorTest().run();
  }

  @Override
  public void run() {
    fullSingleAttribute();
    emptyAttribute();
    singleAttributeInterrupted();
    twoAttributes();
    twoAttributesScan();
  }

  private void fullSingleAttribute() {
    AttributeAccumulator accumulator = new AttributeAccumulator();
    boolean completed = accumulator.match(new ByteIterator("Host: localhost:8080\r\n"));
    assertTrue(completed);
  }

  private void emptyAttribute() {
    AttributeAccumulator accumulator = new AttributeAccumulator();
    boolean complete = accumulator.match(new ByteIterator("\r\n"));
    assertFalse(complete);
  }

  private void singleAttributeInterrupted() {
    AttributeAccumulator accumulator = new AttributeAccumulator();
    String line = "Host: localhost:8080\r\n";
    int splitPt = 2;
    String head = line.substring(0, splitPt);
    String tail = line.substring(splitPt);
    ByteIterator it = new ByteIterator(head);
    assertFalse(accumulator.match(it));
    it.append(tail);
    assertTrue(accumulator.match(it));
    assertEquals("Host", accumulator.getKey());
    assertEquals("localhost:8080", accumulator.getValue());
  }

  private void twoAttributes() {
    String lines = "Host: localhost:8080\r\nUser-Agent: Mozilla\r\n\r\n";
    AttributeCollectionAccumulator accumulator = new AttributeCollectionAccumulator();
    boolean completed = accumulator.match(new ByteIterator(lines));
    assertTrue(completed);
    Map<String, String> map = accumulator.getMap();
    assertEquals(2, map.size());
    assertEquals("localhost:8080", map.get("Host"));
    assertEquals("Mozilla", map.get("User-Agent"));
  }

  private void twoAttributesScan() {
    String lines = "Host: localhost:8080\r\nUser-Agent: Mozilla\r\n\r\n";
    for (int i = 0; i < lines.length(); i++) {
      assertSplitGet(lines, i);
    }
  }

  private void assertSplitGet(String lines, int splitPt) {
    String head = lines.substring(0, splitPt);
    String tail = lines.substring(splitPt);
    AttributeCollectionAccumulator accumulator = new AttributeCollectionAccumulator();
    ByteIterator it = new ByteIterator(head);
    assertFalse(accumulator.match(it));
    it.append(tail);
    assertTrue(accumulator.match(it));
    Map<String, String> map = accumulator.getMap();
    assertEquals(2, map.size());
    assertEquals("localhost:8080", map.get("Host"));
    assertEquals("Mozilla", map.get("User-Agent"));
  }

}
