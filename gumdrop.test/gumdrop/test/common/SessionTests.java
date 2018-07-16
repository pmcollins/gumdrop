package gumdrop.test.common;

import gumdrop.common.Entity;
import gumdrop.common.Session;
import gumdrop.test.util.Test;

import java.util.Objects;

import static gumdrop.test.util.Asserts.assertEquals;

public class SessionTests extends Test {

  public static void main(String[] args) throws CloneNotSupportedException {
    new SessionTests().run();
  }

  @Override
  public void run() throws CloneNotSupportedException {
    testEquals();
    copyConstructor();
  }

  private void testEquals() {
    Session<Thing> a = new Session<>();
    a.put("aaa", "bbb");
    Session<Thing> b = new Session<>();
    b.put("aaa", "bbb");
    assertEquals(a, b);
  }

  private void copyConstructor() throws CloneNotSupportedException {
    Session<Thing> a = new Session<>();
    a.put("aaa", "bbb");
    a.setEntity(new Thing("foo"));
    Session<Thing> b = new Session<>(a);
    assertEquals(a, b);
  }

  private static class Thing extends Entity implements Cloneable {

    private final String name;

    Thing(String name) {
      this.name = name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Thing thing = (Thing) o;
      return Objects.equals(name, thing.name);
    }

    @Override
    public String toString() {
      return "Thing{name='" + name + '\'' + '}';
    }

  }

}
