package gumdrop.web.controller;

import gumdrop.web.html.Buildable;

public interface WrapperView<M> {

  void wrap(StringBuilder sb, M m, Buildable buildable, String className);

}
