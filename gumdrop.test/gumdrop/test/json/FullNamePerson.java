package gumdrop.test.json;

import java.util.Objects;

class FullNamePerson {

  private Name name;
  private int age;

  Name getName() {
    return name;
  }

  void setName(Name name) {
    this.name = name;
  }

  int getAge() {
    return age;
  }

  void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "ComplexPerson{" +
      "name=" + name +
      ", age=" + age +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FullNamePerson that = (FullNamePerson) o;
    return age == that.age &&
      Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age);
  }

}
