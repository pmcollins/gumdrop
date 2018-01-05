package gumdrop.json;

import java.util.Stack;

public class BuilderDelegate<T> implements JsonDelegate {

  private final Stack<ObjectBuilder<?>> builders = new Stack<>();
  private final ObjectBuilder<Holder<T>> rootBuilder;
  private String key = HolderSetters.HOLDER_SET_KEY;

  public BuilderDelegate(Setters<T> setters) {
    rootBuilder = new ObjectBuilder<>(new HolderSetters<>(setters));
    builders.add(rootBuilder);
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
    pushSubBuilder(key == null ? ListSetters.ARRAY_ADD_KEY : key);
  }

  @Override
  public void objectEnd() {
    builders.pop();
  }

  @Override
  public void arrayStart() {
    pushSubBuilder(key);
  }

  @Override
  public void arrayEnd() {
    builders.pop();
  }

  private void setValue(String string) {
    ObjectBuilder<?> currentBuilder = builders.peek();
    currentBuilder.applyString(key, string);
    key = null;
  }

  private void pushSubBuilder(String memberKey) {
    ObjectBuilder<?> currentBuilder = builders.peek();
    ObjectBuilder<?> subBuilder = currentBuilder.constructMember(memberKey);
    builders.push(subBuilder);
    key = null;
  }

  public T getObject() {
    Holder<T> holder = rootBuilder.getObject();
    return holder.getContents();
  }

}
