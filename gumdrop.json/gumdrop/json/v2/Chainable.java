package gumdrop.json.v2;

public interface Chainable {

  Chainable next();

  Chainable next(String key);

  void acceptString(String value);

  void acceptBareword(String bareword);

}
