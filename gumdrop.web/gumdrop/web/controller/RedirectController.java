package gumdrop.web.controller;

import gumdrop.common.Entity;
import gumdrop.common.Session;
import gumdrop.common.SessionService;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;

public abstract class RedirectController<T extends Session<E>, E extends Entity> extends SessionController<T, E> {

  protected RedirectController(SessionService<T> sessionService) {
    super(sessionService);
  }

  protected abstract String getRedirectTarget();

  protected String getPostString() {
    return getRequest().getPostString();
  }

  protected byte[] getPost() {
    return getRequest().getPost();
  }

  protected String getHeader(String key) {
    return getRequest().getHeader(key);
  }

  @Override
  protected void process(HttpResponse response) {
    HeaderUtil.setRedirect(response.getHeader(), getRedirectTarget());
  }

}
