package gumdrop.web.controller;

import gumdrop.common.http.HttpMethod;
import gumdrop.common.http.HttpRequest;
import gumdrop.web.http.HttpResponse;

import java.util.Optional;
import java.util.function.Supplier;

public class Dispatcher {

  private final MethodDispatcher get = new MethodDispatcher();
  private final MethodDispatcher post = new MethodDispatcher();
  private final MethodDispatcher put = new MethodDispatcher();
  private final MethodDispatcher delete = new MethodDispatcher();
  private final ControllerIndex controllerIndex = new ControllerIndex();
  private Supplier<Controller> errorController;

  public void setErrorController(Supplier<Controller> errorController) {
    this.errorController = errorController;
  }

  public void register(HttpMethod method, String path, Supplier<Controller> controllerSupplier) {
    controllerIndex.put(controllerSupplier.get().getClass(), new PathBuilder(path));
    getMethodDispatcher(method).register(path, controllerSupplier);
  }

  public HttpResponse processRequest(HttpRequest httpRequest) {
    System.out.println("\n\n********************************************************************************");
    System.out.println(httpRequest);
    Controller controller = dispatch(httpRequest);
    System.out.println(controller);
    return processController(controller, httpRequest);
  }

  public HttpResponse processError(HttpRequest request) {
    return processController(errorController.get(), request);
  }

  private HttpResponse processController(Controller controller, HttpRequest request) {
    controller.setControllerIndex(controllerIndex);
    return controller.process(request);
  }

  public Controller dispatch(HttpRequest httpRequest) {
    MethodDispatcher methodDispatcher = getMethodDispatcher(httpRequest.getHttpMethod());
    Optional<Controller> optionalController = methodDispatcher.getController(httpRequest.getPath());
    return optionalController.orElseGet(errorController);
  }

  private MethodDispatcher getMethodDispatcher(HttpMethod method) {
    switch (method) {
      case GET:
      case HEAD:
        return get;
      case POST:
        return post;
      case PUT:
        return put;
      case DELETE:
        return delete;
    }
    throw new RuntimeException("dispatcher method not found: [" + method + "]");
  }

  public PathBuilder getBuilder(Class<? extends Controller> klass) {
    return controllerIndex.getPathBuilder(klass);
  }

}
