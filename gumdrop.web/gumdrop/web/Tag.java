package gumdrop.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tag implements Buildable {

  private static final char LT = '<';
  private static final String LT_SLASH = "</";
  private static final char GT = '>';
  private static final String SLASH_GT = "/>";

  private final String name;
  private final List<Buildable> children = new ArrayList<>();
  private final List<TagAttribute> tagAttributes = new ArrayList<>();
  private boolean nonAutoClosing;

  public Tag(String name) {
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

  public Tag attr(String name, String value) {
    tagAttributes.add(new TagAttribute(name, value));
    return this;
  }

  public Tag setNonAutoClosing() {
    nonAutoClosing = true;
    return this;
  }

  protected List<TagAttribute> getTagAttributes() {
    return tagAttributes;
  }

  @Override
  public void build(StringBuilder sb) {
    sb.append(LT).append(name);
    for (TagAttribute attr : tagAttributes) {
      attr.build(sb);
    }
    if (children.isEmpty() && !nonAutoClosing) {
      sb.append(SLASH_GT);
    } else {
      sb.append(GT);
      children.forEach(child -> child.build(sb));
      sb.append(LT_SLASH).append(name).append(GT);
    }
  }

}