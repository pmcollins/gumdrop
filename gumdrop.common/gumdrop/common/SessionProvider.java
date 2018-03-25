package gumdrop.common;

public interface SessionProvider<T> {

  Session<T> getSession(String sessionId);

}
