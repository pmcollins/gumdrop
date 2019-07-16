package gumdrop.json.v2;

class BooleanNode extends Node<Boolean> {

  @Override
  public void acceptBareword(String bareword) {
    setValue(Boolean.valueOf(bareword));
  }

}
