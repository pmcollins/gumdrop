# Gumdrop-JSON

JSON <--> Java (de)serialization.

### Overview

Gumdrop's JSON deserializer is a fast, imperative way to turn JSON into objects and vice versa.

### Examples

#### JSON to Object

Given a `Name` class with a `first` and `last` attribute, define a deserializer

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

and call `toObject` to turn JSON into an object

```java
NameDeserializer deserializer = new NameDeserializer();
String json = "{\"first\":\"Bilbo\",\"last\":\"Baggins\"}";
Name name = deserializer.toObject(json);
```

Deserializers can be *composed* to support more complex object graphs. For example, if our `Name` class above were an
attibute of a `Person` class, we could use `NameDeserializer` to deserialize our `Person`'s name.

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

#### Objects to JSON

Serialization is equally straightforward.

```java
class NameSerializer extends ObjectSerializer<Name> {

  NameSerializer() {
    StringSerializer stringSerializer = new StringSerializer();
    addMethodSerializer("first", Name::getFirst, stringSerializer);
    addMethodSerializer("last", Name::getLast, stringSerializer);
  }

}
```

Define a serializer for a type by binding its fields to their respective serializers and call the `toJson` method.

```java
NameSerializer p = new NameSerializer();
String json = p.toJson(new Name("bilbo", "baggins"));
// {"first":"bilbo","last":"baggins"}
```

In a manner similar to the deserializer, serializers can be composed.

```java
class PersonSerializer extends ObjectSerializer<Person> {

  PersonSerializer() {
    addMethodSerializer("age", Person::getAge, new IntSerializer());
    addMethodSerializer("name", Person::getName, new NameSerializer());
  }

}
```

Above we reuse the `NameSerializer` and bind it to the name field. To turn `Person` objects to JSON, call the
`toJson` method.

```java
Name name = new Name("bilbo", "baggins");
Person person = new Person();
person.setName(name);
person.setAge(111);
PersonSerializer s = new PersonSerializer();
String json = s.toJson(person);
// {"age":111,"name":{"first":"bilbo","last":"baggins"}}
```
