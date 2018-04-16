# Gumdrop-SQL

A thin wrapper around JDBC for ORM-like operations. Loosely inspired by Apple's CoreData library. Postgres only, for
now.

### Example

Consider a Person class, which we necessarily derive from `Entity`.

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

We create a table:

```sql

CREATE TABLE person (
  id    SERIAL PRIMARY KEY,
  name  VARCHAR(128),
  age   INTEGER
);

```

Then set up an `Inserter`:

```java

InsertColumnCollection<Person> columns = new InsertColumnCollection<>();
columns.add(new InsertStringColumn<>("name", Person::getName));
columns.add(new InsertIntegerColumn<>("age", Person::getAge));
Inserter<Person> inserter = new Inserter<>("person", columns);

```

Gumdrop-SQL assumes we have a serial primary key. We just have to tell it about our `name` and `age` columns. We then
pass in both the name of the person table, `"person"` and our column collection into the inserter and we're done with
setup. Of course, we only have to do this once per application. You're encouraged to subclass `Inserter` and do
this setup in your subclass's constructor.

Now that we have our inserter, we can insert a row. Given an existing JDBC connection, we just say:

```java

Person person = new Person();
person.setName("Bilbo Baggins");
person.setAge(50);
inserter.insert(connection, person);

```

After insert is performed, our `person` object is populated with the primary key it received from Postgres, available
via `person.getId()`.
