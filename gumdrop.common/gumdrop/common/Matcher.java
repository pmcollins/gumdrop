package gumdrop.common;

public interface Matcher {

  boolean match(ByteIterator it);

  void skip(ByteIterator it);

}
