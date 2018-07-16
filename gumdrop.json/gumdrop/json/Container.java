package gumdrop.json;

class Container<T> {

  private T contents;

  T getContents() {
    return contents;
  }

  void setContents(T contents) {
    this.contents = contents;
  }

}
