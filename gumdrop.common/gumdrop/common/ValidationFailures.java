package gumdrop.common;

import java.util.ArrayList;
import java.util.List;

public class ValidationFailures {

  private final List<ValidationFailure> list = new ArrayList<>();

  public void add(ValidationFailure validationFailure) {
    list.add(validationFailure);
  }

  public void add(ValidationFailures validationFailures) {
    list.addAll(validationFailures.list);
  }

  public List<ValidationFailure> getList() {
    return list;
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public String toString() {
    return "ValidationFailures{" +
      "list=" + list +
      '}';
  }

}
