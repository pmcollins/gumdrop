package gumdrop.test.fake;

import java.time.Instant;
import java.util.Objects;

public class BirthdayPerson {

  private String name;
  private int age;
  private Instant birthday;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BirthdayPerson withName(String name) {
    setName(name);
    return this;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public BirthdayPerson withAge(int age) {
    setAge(age);
    return this;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }

  public BirthdayPerson withBirthday(Instant birthday) {
    setBirthday(birthday);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BirthdayPerson person = (BirthdayPerson) o;
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
    return "BirthdayPerson{" +
      "name='" + name + '\'' +
      ", age=" + age +
      ", birthday=" + birthday +
      '}';
  }

}
