package gumdrop.json.v2;

public interface Chainable {

  Chainable next();

  Chainable next(String key);

  void accept(String value);

}
