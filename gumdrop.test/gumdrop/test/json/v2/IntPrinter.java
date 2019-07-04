package gumdrop.test.json.v2;

class IntPrinter implements JsonPrinter<Integer> {

  @Override
  public void print(StringBuilder sb, Integer integer) {
    sb.append(integer);
  }

}
