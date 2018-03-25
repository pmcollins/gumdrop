package gumdrop.web;

import gumdrop.common.Flash;
import gumdrop.common.Session;
import gumdrop.common.SessionProvider;
import gumdrop.common.http.Request;
import gumdrop.web.control.Controller;
import gumdrop.web.control.ControllerIndex;
import gumdrop.web.control.PathBuilder;
import gumdrop.web.control.PathBuilderIndex;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.http.HttpResponseHeader;

import java.util.UUID;

public abstract class SessionController<T> implements Controller, ControllerContext<T> {

  private final SessionProvider<T> sessionProvider;
  private String[] pathArgs;
  private PathBuilderIndex pathBuilderIndex;
  private Session<T> session;
  private Request request;
  private String unauthorizedPath;

  protected SessionController(SessionProvider<T> sessionProvider) {
    this.sessionProvider = sessionProvider;
  }

  protected abstract boolean isAuthorized();

  protected abstract void process(HttpResponse response);

  @Override
  public String getPath() {
    return request.getPath();
  }

  @Override
  public void setControllerIndex(ControllerIndex controllerIndex) {
    setPathBuilderIndex(controllerIndex);
  }

  @Override
  public String[] getPathArgs() {
    return pathArgs;
  }

  protected int getIntArg() {
    String[] pathArgs = getPathArgs();
    return Integer.valueOf(pathArgs[0]);
  }

  @Override
  public void setPathArgs(String[] pathArgs) {
    this.pathArgs = pathArgs;
  }

  public void setPathBuilderIndex(PathBuilderIndex pathBuilderIndex) {
    this.pathBuilderIndex = pathBuilderIndex;
  }

  @Override
  public PathBuilder getPathBuilder(Class<? extends Controller> controllerClass) {
    return pathBuilderIndex.get(controllerClass);
  }

  @Override
  public final Session<T> getSession() {
    return session;
  }

  protected void sessionPut(String key, String value) {
    session.put(key, value);
  }

  protected String sessionGet(String key) {
    return session.get(key);
  }

  protected void setFlash(Flash flash) {
    getSession().setFlash(flash);
  }

  protected boolean isLoggedIn() {
    return session.isLoggedIn();
  }

  protected T getSessionEntity() {
    return session.getEntity();
  }

  Request getRequest() {
    return request;
  }

  protected int getPathArg(int i) {
    return Integer.parseInt(getPathArgs()[i]);
  }

  protected <U extends Controller> void setUnauthorizedController(Class<U> klass) {
    unauthorizedPath = getPathBuilder(klass).build();
  }

  @Override
  public final HttpResponse process(Request request) {
    this.request = request;
    String cookieString = request.getCookieString();
    HttpResponseHeader responseHeader = new HttpResponseHeader();
    String sessionId;
    if (cookieString == null) {
      String uuid = UUID.randomUUID().toString();
      responseHeader.putAttr("Set-Cookie", "s=" + uuid);
      sessionId = uuid;
    } else {
      // TODO process cookies correctly. This breaks logins when there's more than one cookie.
      sessionId = cookieString.substring(2);
    }
    session = sessionProvider.getSession(sessionId);
    HttpResponse response = new HttpResponse(responseHeader);
    if (isAuthorized()) {
      process(response);
      return response;
    } else {
      HeaderUtil.setRedirect(responseHeader, unauthorizedPath);
      return response;
    }
  }

}
