package gumdrop.common;

import java.util.Optional;

public interface SessionService<T extends Session> {

  T createSessionObject(String sessionId);

  Optional<T> getSession(String sessionId);

  void persistSession(T session);

}
