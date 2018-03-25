package gumdrop.common;

public interface SessionSupplier<T> {

  Session<T> getSession(String sessionId);

}
