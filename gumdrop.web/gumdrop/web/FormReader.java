package gumdrop.web;

import gumdrop.common.Builder;
import gumdrop.common.BuilderInstance;

public class FormReader<T> {

  private final Builder<T> builder;

  public FormReader(Builder<T> builder) {
    this.builder = builder;
  }

  public T read(String q) {
    BuilderInstance<T> instance = new BuilderInstance<>(builder);
    String[] pairs = q.split("&");
    for (String pair : pairs) {
      int idx = pair.indexOf('=');
      String key = pair.substring(0, idx);
      String value = pair.substring(idx + 1, pair.length());
      instance.applyString(key, value);
    }
    return instance.getObject();
  }

}
