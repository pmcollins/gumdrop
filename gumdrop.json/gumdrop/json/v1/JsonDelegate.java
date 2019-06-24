package gumdrop.json.v1;

public interface JsonDelegate {

  void quotedString(String string);

  void bareword(String bareword);

  void objectStart();

  void objectEnd();

  void arrayStart();

  void arrayEnd();

}
