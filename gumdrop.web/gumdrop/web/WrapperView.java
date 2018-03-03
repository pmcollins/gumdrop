package gumdrop.web;

public interface WrapperView<M> {

  void wrap(StringBuilder sb, M m, Buildable buildable);

}
