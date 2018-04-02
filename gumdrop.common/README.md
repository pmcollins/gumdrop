# Gumdrop-Common

### Builder

Creates Java objects from strings without using reflection or annotations. Used by [Gumdrop-Json](../gumdrop.json/) and
[HttpFormReader](../gumdrop.web/gumdrop/web/http/HttpFormReader.java).

##### Basic Builder

[Builder](gumdrop/common/builder/Builder.java) is very simple: it binds strings to setters and uses those bindings
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Name name = (Name) o;
    return Objects.equals(first, name.first) &&
      Objects.equals(last, name.last);
  }

}

```

Let's create a `Builder` for our `Name` class:

```java

Builder<Name> nameBuilder = new Builder<>(Name::new);
nameBuilder.addSetter("first", Name::setFirst);

```

What we've done is bind the string "first" to the setter `setFirst`. Having done that, we can now send strings into our
`Builder` object and have them be applied to our `Name` instance:

```java

Name name = nameBuilder.construct();
nameBuilder.apply(name, "first", "Bilbo");
assertEquals("Bilbo", name.getFirst());

```

This simple concept is the foundation for how Gumdrop populates Java obects from JSON, from URL parameters, and from
form submissions. The idea is that we wire up the relationships using a simple API, giving us compile-time type
safety (and as a side-benefit, excellent performance), and `Builder` creates and populates objects from string values.

##### Building an Object Graph

When building objects (for example from JSON), we often don't just build simple objects, but rather trees or graphs of
nested objects. To handle object graphs, Gumdrop provides [BuilderNode](gumdrop/common/builder/BuilderNode.java).

Let's look at an example where we populate a `List` of `Name` objects:

```java

Builder<Name> nameBuilder = new Builder<>(Name::new);
nameBuilder.addSetter("first", Name::setFirst);
nameBuilder.addSetter("last", Name::setLast);

Builder<List<Name>> listBuilder = new Builder<>(ArrayList::new);
listBuilder.addMember("name", List::add, nameBuilder);

BuilderNode<List<Name>> listNode = new BuilderNode<>(listBuilder);

BuilderNode<?> nameNode1 = listNode.create("name");
nameNode1.applyString("first", "foo");
nameNode1.applyString("last", "bar");

BuilderNode<?> nameNode2 = listNode.create("name");
nameNode2.applyString("first", "baz");
nameNode2.applyString("last", "glarch");

List<Name> list = listNode.getObject();
assertEquals(List.of(new Name("foo", "bar"), new Name("baz", "glarch")), list);

```

We set up a `nameBuilder` and a `listBuilder`, then bind our `nameBuilder` to our `listBuilder`, passing in a key
("name") and a setter (`List::add`). This wires up `listBuilder` to -- when we call `create` with a `"name"`
argument -- instantiate and return a new `Name` node, and pass in an instance of the object wrapped by that `Name` node
to the provided method reference.

> A `BuilderNode` always has one and only one instance of the object it's building

Now when our `listBuilder` node is told to `create("name")`, it emits a new name builder node, and adds an instance of
that node's `Name` to the `listNode`'s `ArrayList`. After sending strings into our name nodes, which in turn populate
their `Name` instances, we get a hold of a reference to the `ArrayList` of the `Name`s we just created.

This example should provide a basic idea of how Gumdrop handles JSON deserialization as well as how it builds objects
from form submissions.

For more examples, see [BuilderTests](../gumdrop.test/gumdrop/test/common/BuilderTests.java) and others.

### HTTP

Basic HTTP classes.

### Validation

Simple validation facilities used throughout Gumdrop.
