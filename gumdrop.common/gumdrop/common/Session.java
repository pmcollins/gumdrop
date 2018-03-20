package gumdrop.common;

import java.util.HashMap;
import java.util.Map;

public class Session<T> {

  private final String id;
  private Flash flash;
  private T login;
  private final Map<String, String> map = new HashMap<>();

  protected Session(String id) {
    this.id = id;
  }

  public boolean isLoggedIn() {
    return login != null;
  }

  public String getId() {
    return id;
  }

  public T getLogin() {
    return login;
  }

  public void setLogin(T login) {
    this.login = login;
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

  public String get(String key) {
    return map.get(key);
  }

}
