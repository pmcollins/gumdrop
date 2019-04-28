package gumdrop.test.fake;

import java.util.ArrayList;
import java.util.List;

public class PassengerCar extends Car {

  private final List<BirthdayPerson> passengers = new ArrayList<>();

  public void addPassenger(BirthdayPerson namedPerson) {
    passengers.add(namedPerson);
  }

  public List<BirthdayPerson> getPassengers() {
    return passengers;
  }

}
