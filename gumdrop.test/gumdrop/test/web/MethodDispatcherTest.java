package gumdrop.test.web;

import gumdrop.test.fake.FakeController;
import gumdrop.test.util.Asserts;
import gumdrop.test.util.Test;
import gumdrop.web.controller.Controller;
import gumdrop.web.controller.MethodDispatcher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertTrue;

class MethodDispatcherTest extends Test {

  public static void main(String[] args) {
    new MethodDispatcherTest().run();
  }

  @Override
  public void run() {
    getRegexPattern();
    methodDispatch();
    patternDispatch();
    twoMatches();
    wildcardMatch();
    guidMatch();
  }

  private void getRegexPattern() {
    assertEquals("/person/(\\d+)", MethodDispatcher.getRegexPattern("/person/#"));
    assertEquals("/person/(\\d+)/submission/(\\d+)", MethodDispatcher.getRegexPattern("/person/#/submission/#"));
  }

  private void methodDispatch() {
    MethodDispatcher methodDispatcher = new MethodDispatcher();
    methodDispatcher.register("/", FakeController::new);
    Optional<Controller> controller = methodDispatcher.getController("/");
    assertEquals(FakeController.class, controller.get().getClass());
  }

  private void patternDispatch() {
    MethodDispatcher methodDispatcher = new MethodDispatcher();
    methodDispatcher.register("/person/#", FakeController::new);
    Controller controller = methodDispatcher.getController("/person/42").get();
    Asserts.assertTrue(Arrays.equals(new String[] {"42"}, controller.getPathArgs()));
  }

  private void twoMatches() {
    MethodDispatcher methodDispatcher = new MethodDispatcher();
    methodDispatcher.register("/person/#/submission/#", FakeController::new);
    Controller controller = methodDispatcher.getController("/person/42/submission/111").get();
    Asserts.assertTrue(Arrays.equals(new String[] {"42", "111"}, controller.getPathArgs()));
  }

  private void wildcardMatch() {
    String regexPattern = MethodDispatcher.getRegexPattern("/static/*");
    assertEquals("/static/(.+)", regexPattern);
    MethodDispatcher methodDispatcher = new MethodDispatcher();
    methodDispatcher.register("/static/*", FakeController::new);
    Controller controller = methodDispatcher.getController("/static/foo.bar").get();
    String[] pathArgs = controller.getPathArgs();
    assertEquals("foo.bar", pathArgs[0]);
  }

  private void guidMatch() {
    String uuid = UUID.randomUUID().toString();
    MethodDispatcher methodDispatcher = new MethodDispatcher();
    methodDispatcher.register("/foo/#/bar/*", FakeController::new);
    Optional<Controller> controller = methodDispatcher.getController("/foo/42/bar/" + uuid);
    assertTrue(controller.isPresent());
  }

}
