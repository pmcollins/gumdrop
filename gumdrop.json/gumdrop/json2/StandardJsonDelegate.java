package gumdrop.json2;

import java.util.Stack;

public class StandardJsonDelegate implements JsonDelegate {

  private final Stack<BuilderNode> stack = new Stack<>();

  public StandardJsonDelegate(BuilderNode root) {
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
  public void pop(String val) {
    stack.pop().accept(val);
  }

  @Override
  public void pop() {
    stack.pop();
  }

}
