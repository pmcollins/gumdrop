package gumdrop.json.v2;

public class AbstractNode implements Node {

  @Override
  public Node next() {
    throw new NotImplementedException();
  }

  @Override
  public Node next(String key) {
    throw new NotImplementedException();
  }

  @Override
  public void accept(String value) {
    throw new NotImplementedException();
  }

}
