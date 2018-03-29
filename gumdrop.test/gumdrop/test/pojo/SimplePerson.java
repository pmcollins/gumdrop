package gumdrop.test.pojo;

import java.time.Instant;
import java.util.Objects;

public class SimplePerson {

  private String name;
  private int age;
  private Instant birthday;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SimplePerson person = (SimplePerson) o;
    return age == person.age &&
      Objects.equals(name, person.name) &&
      Objects.equals(birthday, person.birthday);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age, birthday);
  }

  @Override
  public String toString() {
    return "Person{" +
      "name='" + name + '\'' +
      ", age=" + age +
      ", birthday=" + birthday +
      '}';
  }

}
