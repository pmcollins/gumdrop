package gumdrop.common;

public interface Request {

  String getPostString();

  byte[] getPost();

  String getPath();

  String getHeader(String key);

  String getCookieString();

}
