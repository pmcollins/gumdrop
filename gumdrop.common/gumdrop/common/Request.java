package gumdrop.common;

public interface Request {

  String getPostString();

  String getPath();

  String getAttr(String key);

  String getCookieString();

}
