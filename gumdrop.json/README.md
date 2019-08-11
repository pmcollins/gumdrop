# Gumdrop-JSON

JSON/Java serialization and deserialization.

### Overview

Gumdrop-JSON has a simple, imperative API for wiring up field-attribute relationships at compile time. The motivation for
this isn't performance, but compile-time safety and avoidance of both annotations and reflection.

### Simple Example

Consider a `Person` class:

```java

import java.time.Instant;
import java.util.Objects;

class Person {

  private String name;
  private int age;
  private Instant birthday;

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  int getAge() {
    return age;
  }

  void setAge(int age) {
    this.age = age;
  }

  Instant getBirthday() {
    return birthday;
  }

  void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return age == person.age &&
      Objects.equals(name, person.name) &&
      Objects.equals(birthday, person.birthday);
  }

}
