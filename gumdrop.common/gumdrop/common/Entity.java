package gumdrop.common;

public abstract class Entity {

  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Entity{" +
      "id=" + id +
      '}';
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

}
