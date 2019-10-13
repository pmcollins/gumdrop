# Gumdrop-JSON

JSON <--> Java (de)serialization.

### Overview

Gumdrop's JSON deserializer is a fast, imperative way to turn JSON into objects and vice versa that doesn't use refection or
annotations that can give you surprises at runtime.

### Examples

#### Deserialization:

Let's say you have a `Name` class with a `first` and `last` attribute. Define deserializer

```java
class NameDeserializer extends ObjectDeserializer<Name> {

  NameDeserializer() {
    super(Name::new, List.of(
      new FieldBinding<>("first", Name::setFirst, StringDeserializer::new),
      new FieldBinding<>("last", Name::setLast, StringDeserializer::new)
    ));
  }

}
```

and deserialize

```java
NameDeserializer deserializer = new NameDeserializer();
String json = "{\"first\":\"Bilbo\",\"last\":\"Baggins\"}";
Name name = deserializer.toObject(json);
```

Deserializers can be composed to support more complex object graphs. For example, if our `Name` class above were an
attibute of a `Person` class, we could use it to deserialize our `Person`'s name.

```java
class PersonDeserializer extends ObjectDeserializer<Person> {

  PersonDeserializer() {
    super(Person::new, List.of(
      new FieldBinding<>("age", Person::setAge, IntDeserializer::new),
      new FieldBinding<>("name", Person::setName, NameDeserializer::new)
    ));
  }

}
```

Notice the `FieldBinding` of the name attribute to the `NameDeserializer` we created above. And notice the
built-in `IntDeserializer` that binds to the `age` field.

#### Serialization

Serialization is equally straightforward.

```java

```
