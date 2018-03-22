# Gumdrop-Common

### Builder

A facility for creating Java objects from Strings. Used by [Gumdrop-Json](../gumdrop.json/) and
[HttpFormReader](../gumdrop.web/gumdrop/web/http/HttpFormReader.java).

##### Explanation

A [Builder](gumdrop/common/builder/Builder.java) is very simple: it maps strings to setters and uses that to populate
instances.

Consider a `Name` class:

```java

import java.util.Objects;

public class Name {

  private String first, last;

  public Name() {
  }

  public Name(String first, String last) {
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

```

Let's create a `Builder` for our `Name` class:

```java

Builder<Name> nameBuilder = new Builder<>(Name::new);
nameBuilder.addSetter("first", Name::setFirst);

```

What we've done is bind the string "first" to the setter `setFirst`. Having done that, we can now send strings into our
Builder object and have them be applied to an instance of Name:

```java

Name name = nameBuilder.construct();
nameBuilder.apply(name, "first", "Bilbo");
assertEquals("Bilbo", name.getFirst());

```

This simple concept is the foundation of how Gumdrop populates Java obects from Json, from URL parameters, and from
form submissions. The idea is that you wire up the relationships using a simple API, giving you compile-time type safety.
As a side benefit, this happens to be very fast at runtime.

### HTTP

Basic HTTP classes.

### Validation

Simple validation facilites used throughout Gumdrop.
