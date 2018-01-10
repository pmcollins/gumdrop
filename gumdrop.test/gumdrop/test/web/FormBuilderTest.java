package gumdrop.test.web;

import gumdrop.common.Builder;
import gumdrop.test.util.Test;
import gumdrop.web.FormReader;

import static gumdrop.test.util.TestUtil.assertEquals;

public class FormBuilderTest extends Test {

  public static void main(String[] args) {
    new FormBuilderTest().run();
  }

  @Override
  public void run() {
    Builder<UserFormData> builder = new Builder<>(UserFormData::new);
    builder.addSetter("first", UserFormData::setFirst);
    builder.addSetter("last", UserFormData::setLast);
    builder.addSetter("email", UserFormData::setEmail);
    String q = "first=fff&last=lll&email=eee";

    FormReader<UserFormData> reader = new FormReader<>(builder);
    UserFormData built = reader.read(q);

    assertEquals("fff", built.getFirst());
    assertEquals("lll", built.getLast());
    assertEquals("eee", built.getEmail());
  }

}

class UserFormData {

  private String first, last, email;

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
