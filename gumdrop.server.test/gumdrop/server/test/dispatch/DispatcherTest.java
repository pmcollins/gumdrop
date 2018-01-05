package gumdrop.server.test.dispatch;

import gumdrop.server.HttpRequest;
import gumdrop.server.nio.Dispatcher;
import gumdrop.server.nio.RestHandler;
import gumdrop.test.Test;

import java.util.Arrays;
import java.util.Optional;

import static gumdrop.server.HttpMethod.GET;
import static gumdrop.server.HttpMethod.POST;
import static gumdrop.test.TestUtil.*;

public class DispatcherTest extends Test {

  public static void main(String[] args) {
    DispatcherTest test = new DispatcherTest();
    test.run();
  }

  @Override
  public void run() {
    dispatch();
    nonDispatch();
    pattern();
  }

  private void dispatch() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.register(GET, "/", HomePageHandler::new);

    HttpRequest httpRequest = new HttpRequest();
    httpRequest.setHttpMethod(GET);
    httpRequest.setPath("/");

    Optional<RestHandler> handler = dispatcher.getHandler(httpRequest);
    assertEquals(HomePageHandler.class, handler.get().getClass());
  }

  private void nonDispatch() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.register(GET, "/", HomePageHandler::new);

    HttpRequest httpRequest = new HttpRequest();
    httpRequest.setHttpMethod(POST);
    httpRequest.setPath("/");

    Optional<RestHandler> handler = dispatcher.getHandler(httpRequest);
    assertFalse(handler.isPresent());
  }

  private void pattern() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.register(GET, "/", HomePageHandler::new);
    dispatcher.register(GET, "/person/#", PersonHandler::new);

    HttpRequest request = new HttpRequest(GET, "/person/42");
    Optional<RestHandler> handler = dispatcher.getHandler(request);
    assertTrue(handler.isPresent());
    assertTrue(Arrays.equals(new String[] {"42"}, handler.get().getMatches()));
  }

}
