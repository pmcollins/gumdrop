package gumdrop.test.web;

import gumdrop.common.http.HttpRequest;
import gumdrop.test.util.Test;
import gumdrop.test.util.Asserts;
import gumdrop.web.control.Controller;
import gumdrop.web.control.PathBuilder;
import gumdrop.web.control.Dispatcher;

import java.util.Arrays;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.common.http.HttpMethod.GET;
import static gumdrop.common.http.HttpMethod.POST;

class DispatcherTest extends Test {

  public static void main(String[] args) {
    DispatcherTest test = new DispatcherTest();
    test.run();
  }

  @Override
  public void run() {
    dispatch();
    nonDispatch();
    pattern();
    pathBuilder();
  }

  private void dispatch() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.register(GET, "/", FakeController::new);

    HttpRequest httpRequest = new HttpRequest();
    httpRequest.setHttpMethod(GET);
    httpRequest.setPath("/");

    Controller controller = dispatcher.dispatch(httpRequest);
    Asserts.assertEquals(FakeController.class, controller.getClass());
  }

  private void nonDispatch() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.setErrorController(FakeErrorController::new);
    dispatcher.register(GET, "/", FakeController::new);

    HttpRequest httpRequest = new HttpRequest();
    httpRequest.setHttpMethod(POST);
    httpRequest.setPath("/aaa");

    Controller controller = dispatcher.dispatch(httpRequest);
    assertEquals(FakeErrorController.class, controller.getClass());
  }

  private void pattern() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.register(GET, "/", FakeController::new);
    dispatcher.register(GET, "/person/#", FakeController::new);

    HttpRequest request = new HttpRequest(GET, "/person/42");
    Controller controller = dispatcher.dispatch(request);
    Asserts.assertTrue(Arrays.equals(new String[] {"42"}, controller.getPathArgs()));
  }

  private void pathBuilder() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.register(GET, "/", FakeController::new);
    dispatcher.register(GET, "/person/#", FakeController::new);
    PathBuilder pb = dispatcher.getBuilder(FakeController.class);
    String path = pb.build(42);
    assertEquals("/person/42", path);
  }

}
