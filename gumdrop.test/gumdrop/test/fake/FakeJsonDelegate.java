package gumdrop.test.fake;

import gumdrop.json.JsonDelegate;

import java.util.ArrayList;
import java.util.List;

public class FakeJsonDelegate implements JsonDelegate {

  private final List<String> quotedStrings = new ArrayList<>();
  private final List<String> barewords = new ArrayList<>();
  private int objectStartCount, objectEndCount, arrayStartCount, arrayEndCount;

  @Override
  public void quotedString(String string) {
    quotedStrings.add(string);
  }

  @Override
  public void bareword(String bareword) {
    barewords.add(bareword);
  }

  @Override
  public void objectStart() {
    objectStartCount++;
  }

  @Override
  public void objectEnd() {
    objectEndCount++;
  }

  @Override
  public void arrayStart() {
    arrayStartCount++;
  }

  @Override
  public void arrayEnd() {
    arrayEndCount++;
  }

  public List<String> getQuotedStrings() {
    return quotedStrings;
  }

  public List<String> getBarewords() {
    return barewords;
  }

  public int getObjectStartCount() {
    return objectStartCount;
  }

  public int getObjectEndCount() {
    return objectEndCount;
  }

  public int getArrayStartCount() {
    return arrayStartCount;
  }

  public int getArrayEndCount() {
    return arrayEndCount;
  }

  public boolean containsKey(String key) {
    return key.contains(key);
  }

  public boolean containsBareword(String bareword) {
    return barewords.contains(bareword);
  }

}
