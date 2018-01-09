package web;

import gumdrop.test.util.Test;

import java.util.HashMap;
import java.util.Map;

public class FormBuilderTest extends Test {

  public static void main(String[] args) {
    new FormBuilderTest().run();
  }

  @Override
  public void run() {
  }

}

class Form<T> {

  private final Map<String, Field<T>> map = new HashMap<>();

}

class Field<T> {


}

class PersonFormData {

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
