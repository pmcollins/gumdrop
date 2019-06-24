package gumdrop.json.v2;

public class AbstractCreatorNode implements CreatorNode {

  @Override
  public CreatorNode next() {
    throw new NotImplementedException();
  }

  @Override
  public CreatorNode next(String key) {
    throw new NotImplementedException();
  }

  @Override
  public void accept(String value) {
    throw new NotImplementedException();
  }

}
