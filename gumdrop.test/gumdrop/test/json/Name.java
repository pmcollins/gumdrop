package gumdrop.test.json;

import java.util.Objects;

class Name {

  private String first, last;

  Name() {
  }

  Name(String first, String last) {
    this.first = first;
    this.last = last;
  }

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

  @Override
  public String toString() {
    return "Name{" +
      "first='" + first + '\'' +
      ", last='" + last + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Name name = (Name) o;
    return Objects.equals(first, name.first) &&
      Objects.equals(last, name.last);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, last);
  }

}
