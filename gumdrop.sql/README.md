# Gumdrop-SQL

A thin wrapper around JDBC for ORM-like operations. Postgres only, for now.

### Inserting

Consider a Person class, derived from `Entity`.

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

We create a table for our `Person` entities:

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

Gumdrop-SQL assumes we have a serial primary key. We just have to tell it about our name and age columns. Set up
consists of just passing in both the name of our person table and our column collection. We only have to do this once
per application. (And you're encouraged to subclass `Inserter` and do this setup in your subclass's constructor.)

Now that we have our `Inserter`, we can insert a row. Given an existing JDBC connection, we just say:

```java

Person person = new Person();
person.setName("Bilbo Baggins");
person.setAge(50);
inserter.insert(connection, person);
int id = person.getId();

```

After insert is performed, our `person` object is populated with the primary key it received from Postgres, available
via `person.getId()`.

### Selecting

To get data out of our database, we use a `Selector`. As with the `Inserter` case, we set up our columns, then give the
selector those columns along with the name of our table.

```java

// Person::new tells the selector how to create Person objects
SelectColumns<Person> columns = new SelectColumns<>(Person::new);
columns.add(new SelectIntegerColumn<>("id", Person::setId));
columns.add(new SelectStringColumn<>("name", Person::setName));
columns.add(new SelectIntegerColumn<>("age", Person::setAge));
Selector<Person> selector = new Selector<>("person", columns);

```

Once we have our selector set up, we can perform arbitrary queries:

```java

Optional<Person> person = selector.selectFirst(connection, new StringPredicate("name = ?", "Bilbo Baggins"));

```

### Discussion

Notice that Selectors and Inserters are set up separately. This because a `Selector` is a general purpose concept. A
given Selector may not select all columns, it may not select from a real table, or it may select from multiple tables
via a join.
