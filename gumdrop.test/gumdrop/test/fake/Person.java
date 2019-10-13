package gumdrop.test.fake;

import java.util.Objects;

public class Person {

  private Name name;
  private int age;

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "NamedPerson{" +
      "name=" + name +
      ", age=" + age +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person that = (Person) o;
    return age == that.age &&
      Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age);
  }

}
