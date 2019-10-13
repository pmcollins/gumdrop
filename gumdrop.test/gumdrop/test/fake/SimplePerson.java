package gumdrop.test.fake;

import java.util.Objects;

public class SimplePerson {

  private String first, last;

  public SimplePerson() {
  }

  public SimplePerson(String first, String last) {
    this.first = first;
    this.last = last;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SimplePerson person = (SimplePerson) o;
    return Objects.equals(first, person.first) &&
      Objects.equals(last, person.last);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, last);
  }

}
