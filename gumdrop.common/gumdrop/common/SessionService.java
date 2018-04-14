package gumdrop.common;

import java.util.Optional;

public interface SessionService<T extends Session> {

  Optional<T> getSession(String sessionId);

  T createSession(String sessionId);

  void persistSession(T session);

}
