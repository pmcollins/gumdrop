# Gumdrop-JSON

A library for turning JSON into POJOs and vice-versa.

### Overview

The motivation for Gumdrop-JSON isn't performance, but compile-time safety and avoidance of both annotations and
reflection. Instead it has a simple, imperative API for wiring up field-attribute relationships at compile time.

As a happy side effect, however, because it doesn't use reflection, this library happens to be over twice as fast as
Google's Gson library at serializing and deserializing to and from JSON, all without having made any explicit
performance optimizations.

For a little more information about how this works under the hood, see [Gumdrop Common](../gumdrop.common/).

### Simple Example

Consider a `Person` class:

```java

package gumdrop.json;

import java.time.Instant;
import java.util.Objects;

class Person {

  private String name;
  private int age;
  private Instant birthday;

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  int getAge() {
    return age;
  }

  void setAge(int age) {
    this.age = age;
  }

  Instant getBirthday() {
    return birthday;
  }

  void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return age == person.age &&
      Objects.equals(name, person.name) &&
      Objects.equals(birthday, person.birthday);
  }

}

```

Let's create a `JsonConverter` for the `Person` class. We'll wire up the relationships between `Person`'s getters/setters
and the JSON keys we're interested in.

```java

JsonConverter<Person> personConverter = new JsonConverter<>(Person::new);
personConverter.addStringField("name", Person::getName, Person::setName);
personConverter.addIntField("age", Person::getAge, Person::setAge);

```

Now we can convert a `Person` instance to JSON and back:

```java

Person p = new Person();
p.setName("Bilbo");
p.setAge(50);
String json = personConverter.toString(p);
TestUtil.assertEquals("{\"name\":\"Bilbo\",\"age\":50}", json);
Person fromJson = personConverter.fromString(json);
TestUtil.assertEquals(p, fromJson);

```

What about the birthday field in `Person`? Let's add support for that now:

```java

DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
personConverter.addStringField(
  "birthday",
  person -> formatter.format(person.getBirthday()),
  (person, str) -> person.setBirthday(Instant.from(formatter.parse(str)))
);

```

Overall, we now have:

```java

JsonConverter<Person> personConverter = new JsonConverter<>(Person::new);
personConverter.addStringField("name", Person::getName, Person::setName);
personConverter.addIntField("age", Person::getAge, Person::setAge);
DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
personConverter.addStringField(
  "birthday",
  person -> formatter.format(person.getBirthday()),
  (person, str) -> person.setBirthday(Instant.from(formatter.parse(str)))
);
Person person = new Person();
person.setName("Frodo");
person.setAge(25);
person.setBirthday(Instant.parse("1900-01-01T01:00:00Z"));
String json = personConverter.toString(person);
assertEquals(
  "{\"name\":\"Frodo\",\"age\":25,\"birthday\":\"1900-01-01T01:00:00Z\"}",
  json
);
Person fromJson = personConverter.fromString(json);
assertEquals(person, fromJson);

```

As an alternative to the formatter lambas above, we can use a `StringConverter`:

```java

class InstantConverter implements StringConverter<Instant> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

  @Override
  public String toString(Instant instant) {
    return formatter.format(instant);
  }

  @Override
  public Instant fromString(String s) {
    return Instant.from(formatter.parse(s));
  }

}

```

...and pass an instance into our `JsonConverter`:

```java

personConverter.addField(
  "birthday",
  Person::getBirthday,
  Person::setBirthday,
  new InstantConverter()
);

```

Now we have:

```java

JsonConverter<Person> personConverter = new JsonConverter<>(Person::new);
personConverter.addStringField("name", Person::getName, Person::setName);
personConverter.addIntField("age", Person::getAge, Person::setAge);
personConverter.addField(
  "birthday",
  Person::getBirthday,
  Person::setBirthday,
  new InstantConverter()
);
Person person = new Person();
person.setName("Frodo");
person.setAge(25);
person.setBirthday(Instant.parse("1900-01-01T01:00:00Z"));
String json = personConverter.toString(person);
assertEquals(
  "{\"name\":\"Frodo\",\"age\":25,\"birthday\":\"1900-01-01T01:00:00Z\"}",
  json
);
Person fromJson = personConverter.fromString(json);
assertEquals(person, fromJson);

```

### Nested Object Example

TODO
