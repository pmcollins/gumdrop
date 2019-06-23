package gumdrop.json2;

public class AbstractBuilderNode implements BuilderNode {

  @Override
  public BuilderNode next() {
    throw new NotImplementedException();
  }

  @Override
  public BuilderNode next(String key) {
    throw new NotImplementedException();
  }

  @Override
  public void accept(String value) {
    throw new NotImplementedException();
  }

}
