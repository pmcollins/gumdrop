package gumdrop.web;

import gumdrop.common.http.HttpMethod;
import gumdrop.common.http.HttpRequest;
import gumdrop.web.controller.Controller;
import gumdrop.web.controller.ControllerIndex;
import gumdrop.web.controller.HasControllerIndex;
import gumdrop.web.http.HttpResponse;

import java.util.Optional;
import java.util.function.Supplier;

public class Dispatcher implements HasControllerIndex {

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
    System.out.println(path);
    controllerIndex.put(controllerSupplier.get().getClass(), new PathBuilder(path));
    getMethodDispatcher(method).register(path, controllerSupplier);
  }

  public HttpResponse processRequest(HttpRequest httpRequest) {
    Controller controller = dispatch(httpRequest);
    return processController(controller, httpRequest);
  }

  public HttpResponse processError(HttpRequest request) {
    return processController(errorController.get(), request);
  }

  private HttpResponse processController(Controller controller, HttpRequest request) {
    controller.visit(this);
    System.out.println(controller);
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
    return controllerIndex.get(klass);
  }

  @Override
  public ControllerIndex getControllerIndex() {
    return controllerIndex;
  }

}
