package gumdrop.json;

public interface StringConverter<T> {

  String toString(T t);

  T fromString(String s);

}
