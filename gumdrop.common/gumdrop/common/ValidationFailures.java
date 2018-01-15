package gumdrop.common;

import java.util.ArrayList;
import java.util.List;

public class ValidationFailures {

  private final List<ValidationFailure> list = new ArrayList<>();

  public void add(ValidationFailure validationFailure) {
    list.add(validationFailure);
  }

  public List<ValidationFailure> getList() {
    return list;
  }

}
