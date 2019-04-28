package gumdrop.test.json2;

import gumdrop.json2.JsonListPrinter;
import gumdrop.json2.JsonObjectPrinter;
import gumdrop.test.fake.*;
import gumdrop.test.util.Test;

import java.time.Instant;
import java.util.List;

import static gumdrop.test.util.Asserts.assertEquals;

public class Json2PrinterTest extends Test {

  private static final JsonListPrinter<String> STRING_LIST_PRINTER = new JsonListPrinter<>(str -> '"' + str + '"');
  private static final Car SR3 = mkSRModel3();
  private static final Car LR3 = mkLRModel3();
  private static final JsonObjectPrinter<Car> CAR_PRINTER = mkCarPrinter();

  public static void main(String[] args) throws Exception {
    new Json2PrinterTest().run();
  }

  private static Car mkSRModel3() {
    Car tm3 = new Car();
    tm3.setModel("Tesla Model 3 SR");
    tm3.setRange(240);
    return tm3;
  }

  private static Car mkLRModel3() {
    Car lr3 = new Car();
    setLR3Attributes(lr3);
    return lr3;
  }

  private static void setLR3Attributes(Car lr3) {
    lr3.setModel("Tesla Model 3 LR");
    lr3.setRange(310);
  }

  private static JsonObjectPrinter<Car> mkCarPrinter() {
    JsonObjectPrinter.Builder<Car> carPrinterBuilder = new JsonObjectPrinter.Builder<>();
    carPrinterBuilder.addQuotedGetter("model", Car::getModel);
    carPrinterBuilder.addUnquotedGetter("range", car -> String.valueOf(car.getRange()));
    return carPrinterBuilder.build();
  }

  @Override
  public void run() throws Exception {
    testStringList();
    testListOfStringList();
    testSimpleObject();
    testListOfObjects();
    testSubList();
    testSubObject();
  }

  private static void testStringList() {
    assertEquals("[\"hello\"]", STRING_LIST_PRINTER.apply(List.of("hello")));
    assertEquals("[\"hello\",\"goodbye\"]", STRING_LIST_PRINTER.apply(List.of("hello", "goodbye")));
  }

  private static void testListOfStringList() {
    JsonListPrinter<List<String>> llp = new JsonListPrinter<>(STRING_LIST_PRINTER);
    String json = llp.apply(List.of(List.of("aaa", "bbb"), List.of("ccc", "ddd")));
    assertEquals("[[\"aaa\",\"bbb\"],[\"ccc\",\"ddd\"]]", json);
  }

  private static void testSimpleObject() {
    String json = CAR_PRINTER.apply(SR3);
    assertEquals("{\"model\":\"Tesla Model 3 SR\",\"range\":240}", json);
  }

  private static void testListOfObjects() {
    JsonListPrinter<Car> printer = new JsonListPrinter<>(CAR_PRINTER);
    String json = printer.apply(List.of(SR3, LR3));
    assertEquals("[{\"model\":\"Tesla Model 3 SR\",\"range\":240},{\"model\":\"Tesla Model 3 LR\",\"range\":310}]", json);
  }

  private static void testSubList() {
    PassengerCar car = new PassengerCar();
    setLR3Attributes(car);
    BirthdayPerson pablo = new BirthdayPerson().withName("Pablo").withAge(42).withBirthday(Instant.ofEpochSecond(1_000_000_000));
    car.addPassenger(pablo);

    JsonObjectPrinter<PassengerCar> carPrinter = buildCarPrinter();
    String json = carPrinter.apply(car);
    assertEquals(
      "{\"model\":\"Tesla Model 3 LR\",\"range\":310,\"passengers\":[{\"name\":\"Pablo\",\"age\":42,\"birthday\":\"2001-09-09T01:46:40Z\"}]}",
      json
    );
  }

  private static void testSubObject() {
    JsonObjectPrinter.Builder<Name> nameB = new JsonObjectPrinter.Builder<>();
    nameB.addQuotedGetter("first", Name::getFirst);
    nameB.addQuotedGetter("last", Name::getLast);
    JsonObjectPrinter<Name> namePrinter = nameB.build();

    JsonObjectPrinter.Builder<NamedPerson> personB = new JsonObjectPrinter.Builder<>();
    personB.addStringValueOfGetter("age", NamedPerson::getAge);
    personB.addSubPrinter("name", NamedPerson::getName, namePrinter);
    JsonObjectPrinter<NamedPerson> personPrinter = personB.build();

    NamedPerson namedPerson = new NamedPerson();
    namedPerson.setAge(42);
    namedPerson.setName(new Name("Bilbo", "Baggins"));
    String json = personPrinter.apply(namedPerson);
    assertEquals("{\"age\":42,\"name\":{\"first\":\"Bilbo\",\"last\":\"Baggins\"}}", json);
  }

  /* ------ */

  private static JsonObjectPrinter<PassengerCar> buildCarPrinter() {
    JsonObjectPrinter.Builder<PassengerCar> carPrinterB = new JsonObjectPrinter.Builder<>();
    carPrinterB.addQuotedGetter("model", Car::getModel);
    carPrinterB.addStringValueOfGetter("range", car -> car.getRange());
    JsonListPrinter<BirthdayPerson> personJsonListPrinter = buildPersonListPrinter();
    carPrinterB.addSubPrinter("passengers", PassengerCar::getPassengers, personJsonListPrinter);
    return carPrinterB.build();
  }

  private static JsonListPrinter<BirthdayPerson> buildPersonListPrinter() {
    JsonObjectPrinter<BirthdayPerson> personPrinter = buildPersonPrinter();
    return new JsonListPrinter<>(personPrinter);
  }

  private static JsonObjectPrinter<BirthdayPerson> buildPersonPrinter() {
    JsonObjectPrinter.Builder<BirthdayPerson> personPrinterB = new JsonObjectPrinter.Builder<>();
    personPrinterB.addQuotedGetter("name", BirthdayPerson::getName);
    personPrinterB.addStringValueOfGetter("age", BirthdayPerson::getAge);
    personPrinterB.addQuotedGetter("birthday", p -> p.getBirthday().toString());
    return personPrinterB.build();
  }

}
