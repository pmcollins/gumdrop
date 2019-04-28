package gumdrop.common.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

  public ValidationFailures(ValidationFailure... validationFailures) {
    this(Arrays.asList(validationFailures));
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValidationFailures that = (ValidationFailures) o;
    return list.equals(that.list);
  }

  @Override
  public int hashCode() {
    return Objects.hash(list);
  }

  @Override
  public String toString() {
    return "ValidationFailures{" +
      "list=" + list +
      '}';
  }

}
