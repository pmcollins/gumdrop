package gumdrop.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Session<T extends Entity> {

  private String id;
  private Flash flash;
  private T entity;
  private Map<String, String> map = new HashMap<>();

  public Session() {
  }

  public Session(String id) {
    this.id = id;
  }

  @SuppressWarnings("unchecked")
  public Session(Session<T> other) throws CloneNotSupportedException {
    id = other.id;
    if (other.flash != null) {
      flash = new Flash(other.flash);
    }
    entity = (T) other.entity.clone();
    map = new HashMap<>(other.map);
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

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Session<?> session = (Session<?>) o;
    boolean a = Objects.equals(id, session.id);
    boolean b = Objects.equals(flash, session.flash);
    boolean c = Objects.equals(entity, session.entity);
    boolean d = Objects.equals(map, session.map);
    return a && b && c && d;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, flash, entity, map);
  }

  @Override
  public String toString() {
    return "Session{" +
      "id='" + id + '\'' +
      ", flash=" + flash +
      ", entity=" + entity +
      ", map=" + map +
      '}';
  }
}
