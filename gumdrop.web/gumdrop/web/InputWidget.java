package gumdrop.web;

import static gumdrop.web.TagLib.*;

abstract class InputWidget extends Widget {

  InputWidget(String type, String label, String name) {
    super(
      div(
        label(
          text(label),
          input().attr("type", type).attr("name", name)
        )
      )
    );
  }

}
