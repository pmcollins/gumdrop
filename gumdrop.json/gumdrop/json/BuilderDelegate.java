package gumdrop.json;

import gumdrop.common.CreatedInstance;
import gumdrop.common.Creator;

import java.util.Stack;

public class BuilderDelegate<T> implements JsonDelegate {

  private final Stack<CreatedInstance<?>> creatorStack = new Stack<>();
  private final CreatedInstance<Holder<T>> rootCreator;
  private String key = HolderCreator.HOLDER_SET_KEY;

  public BuilderDelegate(Creator<T> creator) {
    rootCreator = new CreatedInstance<>(new HolderCreator<>(creator));
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
    pushSubBuilder(key == null ? ListCreator.ARRAY_ADD_KEY : key);
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
    CreatedInstance<?> currentBuilder = creatorStack.peek();
    currentBuilder.applyString(key, string);
    key = null;
  }

  private void pushSubBuilder(String memberKey) {
    CreatedInstance<?> currentBuilder = creatorStack.peek();
    CreatedInstance<?> subBuilder = currentBuilder.constructMember(memberKey);
    creatorStack.push(subBuilder);
    key = null;
  }

  public T getObject() {
    Holder<T> holder = rootCreator.getObject();
    return holder.getContents();
  }

}
