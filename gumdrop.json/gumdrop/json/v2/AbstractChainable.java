package gumdrop.json.v2;

public class AbstractChainable implements Chainable {

  @Override
  public Chainable next() {
    throw new NotImplementedException();
  }

  @Override
  public Chainable next(String key) {
    throw new NotImplementedException();
  }

  @Override
  public void accept(String value) {
    throw new NotImplementedException();
  }

}
