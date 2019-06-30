package gumdrop.json.v2.common;

public interface Chainable {

  Chainable next();

  Chainable next(String key);

  void accept(String value);

}
