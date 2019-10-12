package gumdrop.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListDeserializer<T> extends CollectionDeserializer<List<T>, T> {

  public ListDeserializer(Function<Consumer<T>, Deserializer<T>> nodeConstructor) {
    super(ArrayList::new, List::add, nodeConstructor);
  }

}
