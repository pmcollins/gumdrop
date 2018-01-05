package gumdrop.json;

public interface Converter<T> {

  String convertToString(T t);

  T convertFromString(String s);

}
