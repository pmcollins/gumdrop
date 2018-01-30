package gumdrop.test.server;

import gumdrop.server.nio.HttpReaderDelegate;

import java.util.ArrayList;
import java.util.List;

class FakeHttpReaderDelegate implements HttpReaderDelegate {

  private final List<String> words = new ArrayList<>();
  private final List<String> keys = new ArrayList<>();
  private final List<String> values = new ArrayList<>();
  private String post;

  @Override
  public void word(String word) {
    words.add(word);
  }

  @Override
  public void key(String key) {
    keys.add(key);
  }

  @Override
  public void value(String value) {
    values.add(value);
  }

  @Override
  public void post(String post) {
    this.post = post;
  }

  public String getPost() {
    return post;
  }

  public List<String> getWords() {
    return words;
  }

  public List<String> getKeys() {
    return keys;
  }

  public List<String> getValues() {
    return values;
  }

}
