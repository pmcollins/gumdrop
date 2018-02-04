package gumdrop.server.nio;

interface RequestParser {

  String getMethod();

  String getAttr(String s);

}
