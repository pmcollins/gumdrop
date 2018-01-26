package gumdrop.web;

import static gumdrop.web.TagLib.input;

class SubmitButton extends Widget {

  private final Tag button;

  SubmitButton(String buttonText) {
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
