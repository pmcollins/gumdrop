# Gumdrop-Common

### Builder

A facility for creating Java objects from Strings. Used by [Gumdrop-Json](../gumdrop.json/) and
[HttpFormReader](../gumdrop.web/gumdrop/web/http/HttpFormReader.java).

##### Basic Builder

A [Builder](gumdrop/common/builder/Builder.java) is very simple: it binds Strings to setters and uses those bindings
to populate instances.

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
Builder object and have them be applied to our `Name` instance:

```java

Name name = nameBuilder.construct();
nameBuilder.apply(name, "first", "Bilbo");
assertEquals("Bilbo", name.getFirst());

```

This simple concept is the foundation for how Gumdrop populates Java obects from JSON, from URL parameters, and from
form submissions. The idea is that you wire up the relationships using a simple API, giving you compile-time type
safety, and as a side-benefit you get excellent performance.

##### Graph Builder

When building an object from a JSON string, we often don't just build one simple object, but rather a tree or graph of
nested objects. To handle the creation of these sub-objects, Gumdrop provides `InstanceBuilder`.

To take an example where we populate a `List` of `Name`s:

```java

Builder<Name> nameBuilder = new Builder<>(Name::new);
nameBuilder.addSetter("first", Name::setFirst);
nameBuilder.addSetter("last", Name::setLast);
Builder<List<Name>> listBuilder = new Builder<>(ArrayList::new);
listBuilder.addMember("name", List::add, nameBuilder);
InstanceBuilder<List<Name>> listInstance = new InstanceBuilder<>(listBuilder);
InstanceBuilder<?> nb1 = listInstance.constructAndSet("name");
nb1.applyString("first", "foo");
nb1.applyString("last", "bar");
InstanceBuilder<?> nb2 = listInstance.constructAndSet("name");
nb2.applyString("first", "baz");
nb2.applyString("last", "glarch");
List<Name> list = listInstance.getObject();
assertEquals(List.of(new Name("foo", "bar"), new Name("baz", "glarch")), list);

```

The functionality in  above example doesn't have much use, but it should illustrate what happens when a `[` is reached
during JSON deserialization, or when an array argument is posted in a web form.

For more examples, see [BuilderTests](../gumdrop.test/gumdrop/test/common/BuilderTests.java) and others.

### HTTP

Basic HTTP classes.

### Validation

Simple validation facilites used throughout Gumdrop.
