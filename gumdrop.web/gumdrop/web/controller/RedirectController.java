package gumdrop.web.controller;

import gumdrop.common.SessionSupplier;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;

public abstract class RedirectController<T> extends SessionController<T> {

  protected RedirectController(SessionSupplier<T> sessionSupplier) {
    super(sessionSupplier);
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
