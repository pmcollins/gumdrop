package gumdrop.common.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationFailures {

  private final List<ValidationFailure> list;

  public ValidationFailures() {
    this(new ArrayList<>());
  }

  public ValidationFailures(ValidationFailures other) {
    list = new ArrayList<>(other.list);
  }

  public ValidationFailures(List<ValidationFailure> list) {
    this.list = list;
  }

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
