package gumdrop.json;

import gumdrop.common.builder.Builder;
import gumdrop.common.builder.BuilderNode;

import java.util.Stack;

public class BuilderDelegate<T> implements JsonDelegate {

  private final Stack<BuilderNode<?>> nodeStack = new Stack<>();
  private final BuilderNode<Container<T>> rootNode;
  private String key = ContainerBuilder.CONTAINER_SET_KEY;

  public BuilderDelegate(Builder<T> builder) {
    rootNode = new BuilderNode<>(new ContainerBuilder<>(builder));
    nodeStack.add(rootNode);
  }

  @Override
  public void quotedString(String string) {
    if (key == null) {
      key = string;
    } else {
      setValue(string);
    }
  }

  @Override
  public void bareword(String bareword) {
    setValue("null".equals(bareword) ? null : bareword);
  }

  @Override
  public void objectStart() {
    pushSubNode(key == null ? ListBuilder.ARRAY_ADD_KEY : key);
  }

  @Override
  public void objectEnd() {
    nodeStack.pop();
  }

  @Override
  public void arrayStart() {
    pushSubNode(key);
  }

  @Override
  public void arrayEnd() {
    nodeStack.pop();
  }

  private void setValue(String string) {
    BuilderNode<?> currentNode = nodeStack.peek();
    currentNode.applyString(key, string);
    key = null;
  }

  private void pushSubNode(String memberKey) {
    BuilderNode<?> currentNode = nodeStack.peek();
    BuilderNode<?> subNode = currentNode.create(memberKey);
    nodeStack.push(subNode);
    key = null;
  }

  public T getObject() {
    Container<T> container = rootNode.getObject();
    return container.getContents();
  }

}
