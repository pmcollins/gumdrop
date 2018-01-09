package gumdrop.test.json;

import gumdrop.json.JsonDelegate;

import java.util.ArrayList;
import java.util.List;

class FakeJsonDelegate implements JsonDelegate {

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

  List<String> getQuotedStrings() {
    return quotedStrings;
  }

  List<String> getBarewords() {
    return barewords;
  }

  int getObjectStartCount() {
    return objectStartCount;
  }

  int getObjectEndCount() {
    return objectEndCount;
  }

  int getArrayStartCount() {
    return arrayStartCount;
  }

  int getArrayEndCount() {
    return arrayEndCount;
  }

  boolean containsKey(String key) {
    return key.contains(key);
  }

  boolean containsBareword(String bareword) {
    return barewords.contains(bareword);
  }

}
