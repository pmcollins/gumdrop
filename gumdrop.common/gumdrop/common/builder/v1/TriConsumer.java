package gumdrop.common.builder.v1;

/**
 * @param <M> almost always a Map, but not necessarily
 * @param <V> the value to be put in the Map
 */
public interface TriConsumer<M, V> {

  void accept(M m, String key, V v);

}
