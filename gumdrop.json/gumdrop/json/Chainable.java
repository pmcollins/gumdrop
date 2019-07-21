package gumdrop.json;

public interface Chainable {

  Chainable next();

  Chainable next(String key);

  void acceptString(String value);

  void acceptBareword(String bareword);

}
