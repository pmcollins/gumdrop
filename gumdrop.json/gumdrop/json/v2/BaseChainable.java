package gumdrop.json.v2;

public class BaseChainable implements Chainable {

  @Override
  public Chainable next() {
    throw new UnhandledCommandException(this.getClass() + "#next()");
  }

  @Override
  public Chainable next(String key) {
    throw new UnhandledCommandException(this.getClass() + "#next(\"" + key + "\")");
  }

  @Override
  public void acceptString(String value) {
    throw new UnhandledCommandException(this.getClass() + "#acceptString(\"" + value + "\")");
  }

  @Override
  public void acceptBareword(String bareword) {
    throw new UnhandledCommandException(this.getClass() + "#acceptBareword(\"" + bareword + "\")");
  }

}
