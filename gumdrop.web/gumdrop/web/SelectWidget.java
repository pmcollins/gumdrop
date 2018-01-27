package gumdrop.web;

import java.util.ArrayList;
import java.util.List;

import static gumdrop.web.TagLib.option;
import static gumdrop.web.TagLib.select;

public class SelectWidget extends Widget {

  private final String name;
  private final List<ValueTextPair> list = new ArrayList<>();
  private String value;

  SelectWidget(Enum<?> e) {
    this(e.toString().toLowerCase());
  }

  public SelectWidget(String name) {
    this.name = name;
  }

  public void addOption(String value, String text) {
    addOption(new ValueTextPair(value, text));
  }

  private void addOption(ValueTextPair pair) {
    list.add(pair);
  }

  public void addOptions(List<ValueTextPair> pairs) {
    list.addAll(pairs);
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  protected Buildable getBuildable() {
    return getTag();
  }

  Tag getTag() {
    Tag select = select().attr("name", name);
    for (ValueTextPair pair : list) {
      String pairValue = pair.getValue();
      Tag option = option(pair.getText()).attr("value", pairValue);
      if (pairValue.equals(value)) {
        option.attr("selected");
      }
      select.add(option);
    }
    return select;
  }

}
