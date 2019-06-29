package gumdrop.json.v2;

import gumdrop.json.v2.common.Node;

import java.util.Stack;

public class StandardJsonDelegate implements JsonDelegate {

  private final Stack<Node> stack = new Stack<>();

  public StandardJsonDelegate(Node root) {
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

  private static class HolderNode extends AbstractNode {

    private final Node node;

    HolderNode(Node node) {
      this.node = node;
    }

    @Override
    public Node next() {
      return node;
    }

  }

}
