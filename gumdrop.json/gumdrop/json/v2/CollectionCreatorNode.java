package gumdrop.json.v2;

public class CollectionCreatorNode<T> extends BaseCreatorNode<T> {

  private final Binding<T, ?> binding;

  protected CollectionCreatorNode(T t, Binding<T, ?> binding) {
    super(t);
    this.binding = binding;
  }

  @Override
  public final CreatorNode next() {
    return binding.bind(get());
  }

}
