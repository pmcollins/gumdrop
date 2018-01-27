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
    simple();
    selected();
  }

  private void simple() {
    SelectWidget selectWidget = getSelectWidget();
    StringBuilder sb = new StringBuilder();
    selectWidget.build(sb);
    assertEquals("<select name=\"foo\"><option value=\"1\">One</option><option value=\"2\">Two</option></select>", sb.toString());
  }

  private void selected() {
    SelectWidget selectWidget = getSelectWidget();
    selectWidget.setValue("2");
    StringBuilder sb = new StringBuilder();
    selectWidget.build(sb);
    assertEquals("<select name=\"foo\"><option value=\"1\">One</option><option value=\"2\" selected>Two</option></select>", sb.toString());
  }

  private static SelectWidget getSelectWidget() {
    SelectWidget selectWidget = new SelectWidget("foo");
    selectWidget.addOption("1", "One");
    selectWidget.addOption("2", "Two");
    return selectWidget;
  }

}
