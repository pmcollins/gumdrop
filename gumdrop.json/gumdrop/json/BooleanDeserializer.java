package gumdrop.json;

class BooleanDeserializer extends Deserializer<Boolean> {

  @Override
  public void acceptBareword(String bareword) {
    setValue(Boolean.valueOf(bareword));
  }

}
