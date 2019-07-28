package gumdrop.web.controller;

import gumdrop.common.Entity;
import gumdrop.common.Flash;
import gumdrop.common.Session;
import gumdrop.common.SessionService;
import gumdrop.common.http.Request;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.http.HttpResponseHeader;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public abstract class SessionController<SessionT extends Session<EntityT>, EntityT extends Entity> implements Controller {

  private final SessionService<SessionT> sessionService;
  private String[] pathArgs;
  private Idx idx;
  private SessionT session;
  private Request request;

  SessionController(SessionService<SessionT> sessionService) {
    this.sessionService = sessionService;
  }

  protected abstract boolean isAuthorized();

  protected abstract void process(HttpResponse response);

  public String getPath() {
    return request.getPath();
  }

  @Override
  public void setControllerIndex(ControllerIndex controllerIndex) {
    setIdx(controllerIndex);
  }

  @Override
  public String[] getPathArgs() {
    return pathArgs;
  }

  protected int getIntArg() {
    String[] pathArgs = getPathArgs();
    return Integer.parseInt(pathArgs[0]);
  }

  @Override
  public void setPathArgs(String[] pathArgs) {
    this.pathArgs = pathArgs;
  }

  public void setIdx(Idx idx) {
    this.idx = idx;
  }

  public PathBuilder getPathBuilder(Class<? extends Controller> controllerClass) {
    return idx.getPathBuilder(controllerClass);
  }

  protected Idx idx() {
    return idx;
  }

  public final SessionT getSession() {
    return session;
  }

  protected void putSessionValue(String key, String value) {
    session.put(key, value);
  }

  protected String getSessionValue(String key) {
    return session.getString(key);
  }

  protected void persistSession() {
    sessionService.persistSession(session);
  }

  protected void clearSessionEntity() {
    session.setEntity(null);
    sessionService.persistSession(session);
  }

  protected void persistFlash(Flash flash) {
    setFlash(flash);
    persistSession();
  }

  @SuppressWarnings("WeakerAccess")
  protected void setFlash(Flash flash) {
    session.setFlash(flash);
  }

  protected boolean isLoggedIn() {
    return session.isLoggedIn();
  }

  protected EntityT getSessionEntity() {
    return session.getEntity();
  }

  protected int getLoggedInId() {
    return session.getEntity().getId();
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

  @Override
  public final HttpResponse process(Request request) {
    this.request = request;
    String cookieString = request.getCookieString();
    HttpResponseHeader responseHeader = new HttpResponseHeader();
    String sessionId = cookieString == null ? createSessionId(responseHeader) : cookieString.substring(2);
    Optional<SessionT> currentSession = sessionService.getSession(sessionId);
    if (currentSession.isPresent()) {
      session = currentSession.get();
    } else {
      session = sessionService.createSessionObject(sessionId);
      sessionService.persistSession(session);
    }
    System.out.println(session);
    return createResponse(responseHeader);
  }

  protected abstract String getUnauthorizedPath();

  private HttpResponse createResponse(HttpResponseHeader responseHeader) {
    HttpResponse response = new HttpResponse(responseHeader);
    if (isAuthorized()) {
      process(response);
      return response;
    } else {
      HeaderUtil.setRedirectHeaders(responseHeader, getUnauthorizedPath());
      return response;
    }
  }

  private static String createSessionId(HttpResponseHeader responseHeader) {
    String sessionId;
    String uuid = UUID.randomUUID().toString();
    responseHeader.setCookie("s", uuid);
    sessionId = uuid;
    return sessionId;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{pathArgs=" + Arrays.toString(pathArgs) + '}';
  }

}
