package gumdrop.json.v2;

import gumdrop.json.v2.common.Chainable;

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
