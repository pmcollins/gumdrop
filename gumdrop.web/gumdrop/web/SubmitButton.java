package gumdrop.web;

import static gumdrop.web.TagLib.input;

class SubmitButton extends Widget {

  private final String buttonText;

  SubmitButton(String buttonText) {
    this.buttonText = buttonText;
  }

  @Override
  protected Buildable getBuildable() {
    return input().attr("type", "submit").attr("value", buttonText);
  }

}
