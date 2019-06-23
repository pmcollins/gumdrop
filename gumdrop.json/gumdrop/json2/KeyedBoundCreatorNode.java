package gumdrop.json2;

public class KeyedBoundCreatorNode<T> extends BaseCreatorNode<T> {

  private final TriBinding<T, ?> triBinding;

  protected KeyedBoundCreatorNode(T t, TriBinding<T, ?> triBinding) {
    super(t);
    this.triBinding = triBinding;
  }

  @Override
  public BuilderNode next(String key) {
    return triBinding.bind(get(), key);
  }

}
