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

To hold our `Person` objects, we create a table like this

```sql

CREATE TABLE person (
  id    SERIAL PRIMARY KEY,
  name  VARCHAR(128),
  age   INTEGER
);

```

and set up an `Inserter`:

```java

InsertColumnCollection<Person> columns = new InsertColumnCollection<>();
columns.add(new InsertStringColumn<>("name", Person::getName));
columns.add(new InsertIntegerColumn<>("age", Person::getAge));
Inserter<Person> inserter = new Inserter<>("person", columns);

```

Inserter assumes our table has a serial primary key, so in this case, we just have to tell it about our name and age
columns. Once we have our column collection defined, we just pass it in to the `Inserter` constructor along with the
name of the table.

With an `Inserter` instance (a singleton is fine because Inserter is thread safe) we can insert a row. We call `insert`,
passing in a db connection object and the entity we want to persist:

```java

Person person = new Person();
person.setName("Bilbo Baggins");
person.setAge(50);
inserter.insert(connection, person);
int id = person.getId();

```

After the insert is performed, our `person` object is populated with the primary key it received from the database. We can
read the primary key value by calling `getId`, defined by the parent `Entity` class.

### Selecting

To get data out of our database, we use a `Selector`. As with `Inserter`, we set up our columns, then give the
`Selector` constructor those columns along with the name of our table. However, because `Selector` has to create entity
instances for each row, we also have to tell it how to construct `Person` objects, so we pass in a constructor
reference, `Person::new`.

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

### Updating

To update existing rows, we set up an `Updater`.

```java

Updater updater = new Updater("person");
updater.setSetClause(new StringPredicate("name = ?", "Frodo"), new IntegerPredicate("age = ?", 25));
updater.setWhereClause(new IntegerPredicate("id = ?", 1));
updater.execute(connection);

```

### Discussion

Why are selectors, inserters, and updaters set up separately? This because selecting, inserting, and updating often have
very different scopes and requirements. The scope of an insert is usually just that of a single table, whereas the scope
of a select typically ranges from requiring multiple tables to just a column subset of a single table. Also, we often
need to perform SQL operations on our columns when we select them. And the scope of an update is also distinct --
something which may not even lend itself to object relational mapping without a lot of complication. Which of an entity's
attributes are to be explicitly updated? And if we want to update a huge number of values, why should we pre-construct
entities for each of those rows? An `Updater`, therefore is a separate class which doesn't use O/R mapping. And
a `Selector` is defined independently so that it may have any combination of selecting from just a few columns,
selecting from something other than real tables, selecting columns that are the result of SQL operations/functions, or
selecting from multiple tables via a join.
