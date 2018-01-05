package gumdrop.server.test.dispatch;

import gumdrop.test.Test;

import java.util.Arrays;
import java.util.Optional;

import static gumdrop.test.TestUtil.assertEquals;
import static gumdrop.test.TestUtil.assertTrue;

public class MethodDispatcherTest extends Test {

  public static void main(String[] args) {
    new MethodDispatcherTest().run();
  }

  @Override
  public void run() {
    getRegexPattern();
    methodDispatch();
    patternDispatch();
    twoMatches();
  }

  private void getRegexPattern() {
    assertEquals("/person/(\\d+)", MethodDispatcher.getRegexPattern("/person/#"));
    assertEquals("/person/(\\d+)/submission/(\\d+)", MethodDispatcher.getRegexPattern("/person/#/submission/#"));
  }

  private void methodDispatch() {
    MethodDispatcher methodDispatcher = new MethodDispatcher();
    methodDispatcher.register("/", HomePageHandler::new);
    Optional<RestHandler> handler = methodDispatcher.getHandler("/");
    assertEquals(HomePageHandler.class, handler.get().getClass());
  }

  private void patternDispatch() {
    MethodDispatcher methodDispatcher = new MethodDispatcher();
    methodDispatcher.register("/person/#", PersonHandler::new);
    RestHandler handler = methodDispatcher.getHandler("/person/42").get();
    assertTrue(Arrays.equals(new String[] {"42"}, handler.getMatches()));
  }

  private void twoMatches() {
    MethodDispatcher methodDispatcher = new MethodDispatcher();
    methodDispatcher.register("/person/#/submission/#", PersonHandler::new);
    RestHandler handler = methodDispatcher.getHandler("/person/42/submission/111").get();
    assertTrue(Arrays.equals(new String[] {"42", "111"}, handler.getMatches()));
  }

}
