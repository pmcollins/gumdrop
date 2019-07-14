package gumdrop.json.v2;

import java.util.Stack;

public class StandardJsonDelegate implements JsonDelegate {

  private final Stack<Chainable> stack = new Stack<>();

  public StandardJsonDelegate(Chainable root) {
    stack.push(root);
  }

  @Override
  public void push() {
    stack.push(stack.peek().next());
  }

  @Override
  public void push(String key) {
    stack.push(stack.peek().next(key));
  }

  @Override
  public void acceptString(String val) {
    stack.peek().acceptString(val);
  }

  @Override
  public void acceptBareword(String bareword) {
    stack.peek().acceptBareword(bareword);
  }

  @Override
  public void pop(String val) {
    stack.pop().acceptString(val);
  }

  @Override
  public void pop() {
    stack.pop();
  }

}
