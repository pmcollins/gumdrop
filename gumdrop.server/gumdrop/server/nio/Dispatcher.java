package gumdrop.server.nio;

import gumdrop.server.HttpMethod;
import gumdrop.server.HttpRequest;

import java.util.Optional;
import java.util.function.Supplier;

public class Dispatcher {

  private final MethodDispatcher get = new MethodDispatcher();
  private final MethodDispatcher post = new MethodDispatcher();
  private final MethodDispatcher put = new MethodDispatcher();
  private final MethodDispatcher delete = new MethodDispatcher();

  public void register(HttpMethod method, String path, Supplier<RestHandler> handlerSupplier) {
    getDispatcherForMethod(method).register(path, handlerSupplier);
  }

  public Optional<RestHandler> getHandler(HttpRequest httpRequest) {
    return getDispatcherForMethod(httpRequest.getHttpMethod()).getHandler(httpRequest.getPath());
  }

  private MethodDispatcher getDispatcherForMethod(HttpMethod method) {
    switch (method) {
      case GET:     return get;
      case POST:    return post;
      case PUT:     return put;
      case DELETE:  return delete;
    }
    throw new RuntimeException("dispatcher method not found: [" + method + "]");
  }

}
