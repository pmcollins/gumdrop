package gumdrop.json2;

public class CollectionCreatorNode<T> extends BaseCreatorNode<T> {

  private final Binding<T, ?> binding;

  protected CollectionCreatorNode(T t, Binding<T, ?> binding) {
    super(t);
    this.binding = binding;
  }

  @Override
  public final BuilderNode next() {
    return binding.bind(get());
  }

}
