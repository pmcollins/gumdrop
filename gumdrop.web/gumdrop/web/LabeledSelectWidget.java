package gumdrop.web;

public class LabeledSelectWidget extends SelectWidget {

  private final String label;

  public LabeledSelectWidget(Enum<?> e, String label) {
    super(e);
    this.label = label;
  }

  @Override
  public void build(StringBuilder sb) {
    new LabeledWidget(label, getTag()).build(sb);
  }

}
