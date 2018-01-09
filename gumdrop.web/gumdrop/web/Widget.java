package gumdrop.web;

class Widget implements Buildable {

  private final Tag tag;

  Widget(Tag tag) {
    this.tag = tag;
  }

  @Override
  public void build(StringBuilder sb) {
    tag.build(sb);
  }

}
