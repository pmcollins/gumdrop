package gumdrop.test.web;

import gumdrop.common.ValidationFailure;
import gumdrop.common.Validator;
import gumdrop.test.util.Test;
import gumdrop.web.FormReadResult;
import gumdrop.web.HttpFormReader;

import java.util.List;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertTrue;

public class HttpFormReaderTest extends Test {

  public static void main(String[] args) {
    new HttpFormReaderTest().run();
  }

  @Override
  public void run() {
    simple();
    invalid();
  }

  private void simple() {
    HttpFormReader<UserFormData> reader = new HttpFormReader<>(UserFormData::new);
    reader.addSetter("first", UserFormData::setFirst);
    reader.addSetter("last", UserFormData::setLast);
    reader.addSetter("email", UserFormData::setEmail);

    FormReadResult<UserFormData> result = reader.read("first=fff&last=lll&email=foo%40bar");

    UserFormData userFormData = result.getFormObject();

    assertEquals("fff", userFormData.getFirst());
    assertEquals("lll", userFormData.getLast());
    assertEquals("foo@bar", userFormData.getEmail());
  }

  private void invalid() {
    HttpFormReader<UserFormData> reader = new HttpFormReader<>(UserFormData::new);
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
  private String email;

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

  String getEmail() {
    return email;
  }

  void setEmail(String email) {
    this.email = email;
  }

}
