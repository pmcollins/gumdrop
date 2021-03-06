package gumdrop.web.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Tag implements Buildable {

  private static final char LT = '<';
  private static final String LT_SLASH = "</";
  private static final char GT = '>';
  private static final String SLASH_GT = "/>";

  private final String name;
  private final List<Buildable> children = new ArrayList<>();
  private final List<TagAttribute> tagAttributes = new ArrayList<>();
  private final List<BareAttribute> bareAttributes = new ArrayList<>();
  private boolean nonAutoClosing;

  Tag(String name) {
    this.name = name;
  }

  protected String getName() {
    return name;
  }

  protected List<Buildable> getChildren() {
    return children;
  }

  public Tag add(String text) {
    children.add(new Text(text));
    return this;
  }

  public Tag add(Buildable... nodes) {
    Collections.addAll(children, nodes);
    return this;
  }

  public Tag add(List<Buildable> list) {
    children.addAll(list);
    return this;
  }

  public Tag attr(String name, String value) {
    tagAttributes.add(new TagAttribute(name, value));
    return this;
  }

  public Tag attr(String name) {
    bareAttributes.add(new BareAttribute(name));
    return this;
  }

  public Tag withClass(String cssClass) {
    return attr("class", cssClass);
  }

  void setNonAutoClosing() {
    nonAutoClosing = true;
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(LT).append(name);
    for (TagAttribute attr : tagAttributes) {
      attr.build(sb);
    }
    for (BareAttribute bareAttribute : bareAttributes) {
      bareAttribute.build(sb);
    }
    if (children.isEmpty() && !nonAutoClosing) {
      sb.append(SLASH_GT);
    } else {
      sb.append(GT);
      children.forEach(child -> child.build(sb));
      sb.append(LT_SLASH).append(name).append(GT);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tag tag = (Tag) o;
    return nonAutoClosing == tag.nonAutoClosing &&
      name.equals(tag.name) &&
      Objects.equals(children, tag.children) &&
      Objects.equals(tagAttributes, tag.tagAttributes) &&
      Objects.equals(bareAttributes, tag.bareAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, children, tagAttributes, bareAttributes, nonAutoClosing);
  }

  @Override
  public String toString() {
    return "Tag{" +
      "name='" + name + '\'' +
      ", children=" + children +
      ", tagAttributes=" + tagAttributes +
      ", bareAttributes=" + bareAttributes +
      ", nonAutoClosing=" + nonAutoClosing +
      '}';
  }

}
