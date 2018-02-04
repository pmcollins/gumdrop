package gumdrop.common;

public interface Request {

  String getPostString();

  String getPath();

  String getHeader(String key);

  String getCookieString();

}
