package gumdrop.test.web;

import gumdrop.common.ValidationFailure;
import gumdrop.common.Validator;
import gumdrop.test.util.Test;
import gumdrop.web.FormReadResult;
import gumdrop.web.FormReader;

import java.util.List;

import static gumdrop.test.util.TestUtil.assertEquals;
import static gumdrop.test.util.TestUtil.assertTrue;

public class FormReaderTest extends Test {

  public static void main(String[] args) {
    new FormReaderTest().run();
  }

  @Override
  public void run() {
    simple();
    invalid();
  }

  private void simple() {
    FormReader<UserFormData> reader = new FormReader<>(UserFormData::new);
    reader.addSetter("first", UserFormData::setFirst);
    reader.addSetter("last", UserFormData::setLast);

    FormReadResult<UserFormData> result = reader.read("first=fff&last=lll");

    UserFormData userFormData = result.getT();

    assertEquals("fff", userFormData.getFirst());
    assertEquals("lll", userFormData.getLast());
  }

  private void invalid() {
    FormReader<UserFormData> reader = new FormReader<>(UserFormData::new);
    reader.addSetter("first", UserFormData::setFirst, new Validator<>(s -> s.length() > 1, "must be longer than 1 character"));
    reader.addSetter("last", UserFormData::setLast);
    FormReadResult<UserFormData> read = reader.read("first=f&last=l");
    assertTrue(read.hasValidationFailures());
    List<ValidationFailure> failures = read.getValidationFailures();
    assertEquals(1, failures.size());
    ValidationFailure validationFailure = failures.get(0);
    assertEquals("f", validationFailure.getValue());
  }

}

class UserFormData {

  private String first;
  private String last;

  String getFirst() {
    return first;
  }

  void setFirst(String first) {
    this.first = first;
  }

  String getLast() {
    return last;
  }

  void setLast(String last) {
    this.last = last;
  }

}
