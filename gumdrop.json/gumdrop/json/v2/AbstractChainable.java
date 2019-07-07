package gumdrop.json.v2;

public class AbstractChainable implements Chainable {

  @Override
  public Chainable next() {
    throw new BadCommandException(this.getClass() + ".next()");
  }

  @Override
  public Chainable next(String key) {
    throw new BadCommandException(this.getClass() + ".next(\"" + key + "\")");
  }

  @Override
  public void accept(String value) {
    throw new BadCommandException(this.getClass() + ".accept(\"" + value + "\")");
  }

  @Override
  public void nullValue() {
    throw new BadCommandException(this.getClass() + ".nullValue()");
  }

}
