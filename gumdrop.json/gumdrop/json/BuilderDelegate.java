package gumdrop.json;

import gumdrop.common.Builder;
import gumdrop.common.BuilderInstance;

import java.util.Stack;

public class BuilderDelegate<T> implements JsonDelegate {

  private final Stack<BuilderInstance<?>> creatorStack = new Stack<>();
  private final BuilderInstance<Holder<T>> rootCreator;
  private String key = HolderBuilder.HOLDER_SET_KEY;

  public BuilderDelegate(Builder<T> builder) {
    rootCreator = new BuilderInstance<>(new HolderBuilder<>(builder));
    creatorStack.add(rootCreator);
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
    setValue(bareword);
  }

  @Override
  public void objectStart() {
    pushSubBuilder(key == null ? ListBuilder.ARRAY_ADD_KEY : key);
  }

  @Override
  public void objectEnd() {
    creatorStack.pop();
  }

  @Override
  public void arrayStart() {
    pushSubBuilder(key);
  }

  @Override
  public void arrayEnd() {
    creatorStack.pop();
  }

  private void setValue(String string) {
    BuilderInstance<?> currentBuilder = creatorStack.peek();
    currentBuilder.applyString(key, string);
    key = null;
  }

  private void pushSubBuilder(String memberKey) {
    BuilderInstance<?> currentBuilder = creatorStack.peek();
    BuilderInstance<?> subBuilder = currentBuilder.constructMember(memberKey);
    creatorStack.push(subBuilder);
    key = null;
  }

  public T getObject() {
    Holder<T> holder = rootCreator.getObject();
    return holder.getContents();
  }

}
