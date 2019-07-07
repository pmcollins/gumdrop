package gumdrop.json.v2.print;

public class IntPrinter extends JsonPrinter<Integer> {

  @Override
  public void printNonNull(StringBuilder sb, Integer integer) {
    sb.append(integer);
  }

}
