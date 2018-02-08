package gumdrop.web;

import static gumdrop.web.TagLib.input;

class SubmitButtonWidget extends Widget {

  private final Tag button;

  SubmitButtonWidget(String buttonText) {
    button = input().attr("type", "submit").attr("value", buttonText);
  }

  @Override
  protected Buildable getBuildable() {
    return button;
  }

  public void disable(boolean disable) {
    if (disable) {
      button.attr("disabled").attr("class", "disabled");
    }
  }

}
