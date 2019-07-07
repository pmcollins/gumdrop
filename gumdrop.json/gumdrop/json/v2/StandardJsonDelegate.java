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
  public void accept(String val) {
    stack.peek().accept(val);
  }

  @Override
  public void pop(String val) {
    stack.pop().accept(val);
  }

  @Override
  public void pop() {
    stack.pop();
  }

  @Override
  public void nullValue() {
    stack.peek().nullValue();
  }

}
