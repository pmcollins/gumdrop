package gumdrop.json.v2;

import java.util.Stack;

public class StandardJsonDelegate implements JsonDelegate {

  private final Stack<Chainable> stack = new Stack<>();

  public StandardJsonDelegate(Chainable root) {
    stack.push(new HolderNode(root));
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
  public void pop(String val) {
    stack.pop().accept(val);
  }

  @Override
  public void pop() {
    stack.pop();
  }

}
