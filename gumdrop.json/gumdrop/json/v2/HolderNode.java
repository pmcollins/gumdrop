package gumdrop.json.v2;

public class HolderNode extends AbstractChainable {

  private final Chainable node;

  public HolderNode(Chainable node) {
    this.node = node;
  }

  @Override
  public Chainable next() {
    return node;
  }

}
