# Gumdrop-SQL

A thin wrapper around JDBC for ORM-like operations.

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

To insert a row, we call `insert`, passing in a db connection object and the entity we want to persist:

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

Unlike `Inserter` and `Selector`, which are thread safe and can be global singletons, an `Updater` is intended to be
used just once.

### Discussion

Why are selectors, inserters, and updaters set up separately? Because selecting, inserting, and updating often have
very different scopes and requirements.

##### Insert

The scope of an insert is just that of a single table.

##### Select

The scope of a select ranges from requiring multiple tables to just a column subset of a single table. A select also
often needs to perform SQL operations on its columns. A `Selector`, therefore, may select
* from just a few columns
* from something other than real tables
* columns that are the result of SQL operations/functions
* multiple tables via a join

##### Update

The scope of an update may not lend itself to object relational mapping (without a lot of complication): given an
entity, which of its attributes are to be explicitly updated? And if we want to update a huge number of rows, we don't
want to pre-construct the entities we want to update. In Gumdrop, an `Updater`, therefore, is a separate class which
doesn't use O/R mapping.
