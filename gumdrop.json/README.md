# Gumdrop-JSON

JSON <--> Java (de)serialization.

### Overview

Gumdrop's JSON deserializer is a fast, imperative way to turn JSON into objects and vice versa.

### Examples

#### JSON to Objects

Given a `Name` class with a `first` and `last` attribute, we define a deserializer

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

and call `toObject` on it to create an object

```java
NameDeserializer deserializer = new NameDeserializer();
String json = "{\"first\":\"Bilbo\",\"last\":\"Baggins\"}";
Name name = deserializer.toObject(json);
```

Deserializers can be *composed* to support more complex object graphs. For example, if our `Name` class above were an
attibute of a `Person` class, we could use our `NameDeserializer` to deserialize our `Person`'s name.

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

Using a `FieldBinding` above, we bind the `age` field to an `IntDeserializer`, and the `name` field to our
`NameDeserializer`.

#### Objects to JSON

Serialization is equally straightforward. We can define a serializer for a type by binding each of its fields to an
appropriate deserializer

```java
class NameSerializer extends ObjectSerializer<Name> {

  NameSerializer() {
    StringSerializer stringSerializer = new StringSerializer();
    addMethodSerializer("first", Name::getFirst, stringSerializer);
    addMethodSerializer("last", Name::getLast, stringSerializer);
  }

}
```

and call the `toJson` method.

```java
NameSerializer d = new NameSerializer();
Name name = new Name("bilbo", "baggins");
String json = d.toJson(name);
// {"first":"bilbo","last":"baggins"}
```

To deserialize more complex objects, deserializers can also be composed. We'll use the `NameSerializer`
we just defined to define a `PersonSerializer`

```java
class PersonSerializer extends ObjectSerializer<Person> {

  PersonSerializer() {
    addMethodSerializer("age", Person::getAge, new IntSerializer());
    addMethodSerializer("name", Person::getName, new NameSerializer());
  }

}
```

and call the `toJson` method.

```java
Name name = new Name("bilbo", "baggins");
Person person = new Person();
person.setName(name);
person.setAge(111);
PersonSerializer s = new PersonSerializer();
String json = s.toJson(person);
// {"age":111,"name":{"first":"bilbo","last":"baggins"}}
```
