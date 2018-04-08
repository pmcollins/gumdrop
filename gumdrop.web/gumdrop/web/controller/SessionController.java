package gumdrop.web.controller;

import gumdrop.common.Flash;
import gumdrop.common.Session;
import gumdrop.common.SessionService;
import gumdrop.common.http.Request;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.http.HttpResponseHeader;

import java.util.UUID;

public abstract class SessionController<S extends Session<E>, E> implements Controller {

  private final SessionService<S> sessionService;
  private String[] pathArgs;
  private PathBuilderIndex pathBuilderIndex;
  private S session;
  private Request request;
  private String unauthorizedPath;

  SessionController(SessionService<S> sessionService) {
    this.sessionService = sessionService;
  }

  protected abstract boolean isAuthorized();

  protected abstract void process(HttpResponse response);

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

  public PathBuilder getPathBuilder(Class<? extends Controller> controllerClass) {
    return pathBuilderIndex.get(controllerClass);
  }

  protected PathBuilderIndex getPathBuilderIndex() {
    return pathBuilderIndex;
  }

  public final S getSession() {
    return session;
  }

  protected void sessionPut(String key, String value) {
    session.put(key, value);
  }

  protected String sessionGet(String key) {
    return session.getString(key);
  }

  protected void persistSession() {
    sessionService.persistSession(session);
  }

  protected void setFlash(Flash flash) {
    getSession().setFlash(flash);
  }

  protected boolean isLoggedIn() {
    return session.isLoggedIn();
  }

  protected E getSessionEntity() {
    return session.getEntity();
  }

  Request getRequest() {
    return request;
  }

  protected String getStringArg(int i) {
    return pathArgs[i];
  }

  protected int getIntArg(int i) {
    return Integer.parseInt(getStringArg(i));
  }

  protected <C extends Controller> void setUnauthorizedController(Class<C> klass) {
    unauthorizedPath = getPathBuilder(klass).build();
  }

  @Override
  public final HttpResponse process(Request request) {
    this.request = request;
    HttpResponseHeader responseHeader = handleSessionCookie(request);
    HttpResponse response = new HttpResponse(responseHeader);
    if (isAuthorized()) {
      process(response);
      return response;
    } else {
      HeaderUtil.setRedirect(responseHeader, unauthorizedPath);
      return response;
    }
  }

  private HttpResponseHeader handleSessionCookie(Request request) {
    String cookieString = request.getCookieString();
    HttpResponseHeader responseHeader = new HttpResponseHeader();
    String sessionId;
    if (cookieString == null) {
      String uuid = UUID.randomUUID().toString();
      responseHeader.setCookie("s", uuid);
      sessionId = uuid;
    } else {
      sessionId = cookieString.substring(2);
    }
    session = sessionService.getSession(sessionId).get();
    return responseHeader;
  }

}
