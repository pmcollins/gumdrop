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

Given a `Person` table with at least these columns...

```sql

CREATE TABLE person (
  id    SERIAL PRIMARY KEY,
  name  VARCHAR(128),
  age   INTEGER
);

```

...we set up an `Inserter`:

```java

InsertColumnCollection<Person> columns = new InsertColumnCollection<>();
columns.add(new InsertStringColumn<>("name", Person::getName));
columns.add(new InsertIntegerColumn<>("age", Person::getAge));
Inserter<Person> inserter = new Inserter<>("person", columns);

```

Gumdrop-SQL assumes we have a serial primary key, so in this case, we just have to tell it about our name and age
columns. Once we have our colum collection defined, we just pass it in to the `Inserter` constructor along with the name
of our table.

Now that we have our `Inserter`, we can insert a row. Given an existing JDBC connection, we just say:

```java

Person person = new Person();
person.setName("Bilbo Baggins");
person.setAge(50);
inserter.insert(connection, person); // once this returns, the insert is done
int id = person.getId();

```

After insert is performed, our `person` object is populated with the primary key it received from the database,
made available via `person.getId()`.

### Selecting

To get data out of our database, we use a `Selector`. As with the `Inserter` case, we set up our columns, then give the
`Selector` constructor those columns along with the name of our table. In this case, however, because we're asking
the selector to create entity instances for each row, we also have to tell it how to construct `Person` objects. In
our case, a reference to the constructor, `Person::new`, will do.

```java

SelectColumns<Person> columns = new SelectColumns<>(Person::new);
columns.add(new SelectIntegerColumn<>("id", Person::setId));
columns.add(new SelectStringColumn<>("name", Person::setName));
columns.add(new SelectIntegerColumn<>("age", Person::setAge));
Selector<Person> selector = new Selector<>("person", columns);

```

Once we have our `Selector` set up, we can perform arbitrary queries:

```java

Optional<Person> person = selector.selectFirst(connection, new StringPredicate("name = ?", "Bilbo Baggins"));

```

### Discussion

Notice that selectors and inserters are set up separately. This because inserting and selecting often have very
different scopes. The scope of an insert is usually just that of a single table, whereas the scope of a select typically
ranges from requiring multiple tables to just a column subset of a single table. Also, we often need to perform some
SQL operation on our columns when we select them. A `Selector`, therefore, is defined independently so that it may have
any combination of selecting from just a few columns, selecting from something other than a real table, selecting
columns that are the result of SQL operations/functions, or selecting from multiple tables via a join.
