package gumdrop.json;

import gumdrop.common.builder.Builder;

class ContainerBuilder<T> extends Builder<Container<T>> {

  static final String CONTAINER_SET_KEY = "set";

  ContainerBuilder(Builder<T> subBuilder) {
    super(Container::new);
    addBuilder(CONTAINER_SET_KEY, Container::setContents, subBuilder);
  }

}
