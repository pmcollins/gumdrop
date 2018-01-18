package gumdrop.test.web;

import gumdrop.test.util.Test;
import gumdrop.web.SelectWidget;

import static gumdrop.test.util.Asserts.assertEquals;

public class SelectWidgetTest extends Test {

  public static void main(String[] args) {
    new SelectWidgetTest().run();
  }

  @Override
  public void run() {
    SelectWidget selectWidget = new SelectWidget("foo");
    selectWidget.addOption("1", "One");
    selectWidget.addOption("2", "Two");
    StringBuilder sb = new StringBuilder();
    selectWidget.build(sb);
    assertEquals("<select name=\"foo\"><option value=\"1\">One</option><option value=\"2\">Two</option></select>", sb.toString());
  }

}
