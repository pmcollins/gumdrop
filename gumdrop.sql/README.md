# Gumdrop-SQL

A thin wrapper around JDBC for ORM-like operations. Loosely inspired by Apple's CoreData library. Postgres only, for
now.

### Example

Consider a Person class:

```java

import gumdrop.common.Entity;

class Person extends Entity {

  private String name;
  private int age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

}

```

To persist `Person` objects to our Postgres db, we create a table like this:

```sql

CREATE TABLE person (
  id    SERIAL PRIMARY KEY,
  name  VARCHAR(128),
  age   INTEGER
);

```

Now we have to set up an `Inserter` to be able to insert Person rows.

```java

InsertColumnCollection<Person> columns = new InsertColumnCollection<>();
columns.add(new InsertStringColumn<>("name", Person::getName));
columns.add(new InsertIntegerColumn<>("age", Person::getAge));
Inserter<Person> inserter = new Inserter<>("person", columns);

```

Gumdrop-SQL already assumes we have a serial primary key, so we don't have to tell it about that. We just have to tell
it about our `name` and `age` columns. We the passin the name of the person table, `person` and our column collection
into the inserter and we're done with setup. Of course, we only have to do this once per application, and you're
encouraged to subclass `Inserter` and do the setup in the constructor.

Now that we have our inserter, we can insert a row. Given an existing JDBC connection, we just say:

```java

Person person = new Person();
person.setName("Bilbo Baggins");
person.setAge(50);
inserter.insert(connection, person);

```

After the insert is performed, our `person` object is populated with the primary key it received from Postgres.

