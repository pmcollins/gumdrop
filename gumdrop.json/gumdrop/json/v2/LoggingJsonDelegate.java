package gumdrop.json.v2;

import gumdrop.common.Logger;
import gumdrop.common.StdoutLogger;

public class LoggingJsonDelegate extends StandardJsonDelegate {

  private final Logger logger = new StdoutLogger("LoggingJsonDelegate");

  public LoggingJsonDelegate(Chainable root) {
    super(root);
  }

  @Override
  public void push() {
    logger.line("push");
    super.push();
  }

  @Override
  public void push(String key) {
    logger.line("push", key);
    super.push(key);
  }

  @Override
  public void pop(String val) {
    logger.line("pop", val);
    super.pop(val);
  }

  @Override
  public void pop() {
    logger.line("pop");
    super.pop();
  }

}
