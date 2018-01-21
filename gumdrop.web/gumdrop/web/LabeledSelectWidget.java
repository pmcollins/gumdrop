package gumdrop.web;

public class LabeledSelectWidget extends SelectWidget {

  private final String label;

  public LabeledSelectWidget(Enum<?> e, String label) {
    super(e);
    this.label = label;
  }

  @Override
  protected Buildable getBuildable() {
    return new LabeledWidget(label, super.getBuildable());
  }

}
