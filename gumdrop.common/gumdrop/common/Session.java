package gumdrop.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Session<T> {

  private String id;
  private Flash flash;
  private T entity;
  private final Map<String, String> map = new HashMap<>();

  protected Session() {
  }

  protected Session(String id) {
    this.id = id;
  }

  public boolean isLoggedIn() {
    return entity != null;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public T getEntity() {
    return entity;
  }

  public void setEntity(T entity) {
    this.entity = entity;
  }

  public Flash getFlash() {
    Flash out = flash;
    flash = null;
    return out;
  }

  public void setFlash(Flash flash) {
    this.flash = flash;
  }

  public void put(String key, String val) {
    map.put(key, val);
  }

  public String getString(String key) {
    return map.get(key);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Session<?> session = (Session<?>) o;
    return Objects.equals(id, session.id) &&
      Objects.equals(flash, session.flash) &&
      Objects.equals(entity, session.entity) &&
      Objects.equals(map, session.map);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, flash, entity, map);
  }

}
