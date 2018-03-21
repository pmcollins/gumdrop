# Gumdrop-JSON

A library for turning JSON into POJOs and vice-versa.

This library doesn't use reflection or require annotations: it uses a simple, imperative API, which you use to
wire up Field-Attribute relationships at compile time.

For example, given a `Person` class:

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

  @Override
  public int hashCode() {
    return Objects.hash(name, age, birthday);
  }

  @Override
  public String toString() {
    return "Person{" +
      "name='" + name + '\'' +
      ", age=" + age +
      ", birthday=" + birthday +
      '}';
  }
  
}
```

You would first create a `JsonBuilder` for the `Person` class. This wires up the relationships between a
`Person`'s getters/setters and JSON keys. You do this once, and typically use the same `JsonBuilder` instance
throughout your application.

```java
    JsonBuilder<Person> personBuilder = new JsonBuilder<>(Person::new);
    personBuilder.addStringField("name", Person::getName, Person::setName);
    personBuilder.addIntField("age", Person::getAge, Person::setAge);
```

Alternatively, create a subclass:

```java
  class PersonBuilder extends JsonBuilder<Person> {

    PersonBuilder() {
      super(Person::new);
      addStringField("name", Person::getName, Person::setName);
      addIntField("age", Person::getAge, Person::setAge);
    }
    
  }
```

Then convert `Person` instances to and from JSON:

```java
    Person p = new Person();
    p.setName("Bilbo");
    p.setAge(50);
    String json = personBuilder.toJson(p);
    TestUtil.assertEquals("{\"name\":\"Bilbo\",\"age\":50}", json);
    Person fromJson = personBuilder.fromJson(json);
    TestUtil.assertEquals(p, fromJson);
```

What about the birthday field in `Person`? Let's add support for that now:

```java
    DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
    personBuilder.addStringField(
      "birthday",
      person -> formatter.format(person.getBirthday()),
      (person, str) -> person.setBirthday(Instant.from(formatter.parse(str)))
    );
```

Now the entire thing looks like this:

```java
    // Typically, you'd put the JsonBuilder setup somewhere else. You'll want to create just one instance of a
    // JsonBuilder (at least per thread) rather than re-create it every time you want to (de)serialize an object. 
    JsonBuilder<Person> personBuilder = new JsonBuilder<>(Person::new);
    personBuilder.addStringField("name", Person::getName, Person::setName);
    personBuilder.addIntField("age", Person::getAge, Person::setAge);
    DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
    personBuilder.addStringField(
      "birthday",
      person -> formatter.format(person.getBirthday()),
      (person, str) -> person.setBirthday(Instant.from(formatter.parse(str)))
    );

    Person person = new Person();
    person.setName("Frodo");
    person.setAge(25);
    person.setBirthday(Instant.ofEpochMilli(700_000_000_000L));

    String json = personBuilder.toJson(person);
    assertEquals(
      "{\"name\":\"Frodo\",\"age\":25,\"birthday\":\"1992-03-07T20:26:40Z\"}",
      json
    );
    Person fromJson = personBuilder.fromJson(json);
    assertEquals(person, fromJson);
```

As an alternative to the birthday lambas, you could use a `Converter`:

```java
class InstantConverter implements Converter<Instant> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

  @Override
  public String convertToString(Instant instant) {
    return formatter.format(instant);
  }

  @Override
  public Instant convertFromString(String s) {
    return Instant.from(formatter.parse(s));
  }

}
```

...and pass an instance into your `JsonBuilder`:

```java
    personBuilder.addField(
      "birthday",
      Person::getBirthday,
      Person::setBirthday,
      new InstantConverter()
    );
```

Now we have:

```java
    JsonBuilder<Person> personBuilder = new JsonBuilder<>(Person::new);
    personBuilder.addStringField("name", Person::getName, Person::setName);
    personBuilder.addIntField("age", Person::getAge, Person::setAge);
    personBuilder.addField(
      "birthday",
      Person::getBirthday,
      Person::setBirthday,
      new InstantConverter()
    );

    Person person = new Person();
    person.setName("Frodo");
    person.setAge(25);
    person.setBirthday(Instant.ofEpochMilli(700_000_000_000L));

    String json = personBuilder.toJson(person);
    assertEquals(
      "{\"name\":\"Frodo\",\"age\":25,\"birthday\":\"1992-03-07T20:26:40Z\"}",
      json
    );
    Person fromJson = personBuilder.fromJson(json);
    assertEquals(person, fromJson);
```

The motivation for this library isn't performance, but compile-time safety, and avoidance of annotations.
As a happy side effect, however, because it doesn't use reflection, this library happens to be many times faster than
Google's Gson library at serializing and deserializing JSON.
