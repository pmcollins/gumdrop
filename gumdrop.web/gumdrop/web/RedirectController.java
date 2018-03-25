package gumdrop.web;

import gumdrop.common.SessionProvider;
import gumdrop.web.SessionController;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;

public abstract class RedirectController<T> extends SessionController<T> {

  protected RedirectController(SessionProvider<T> sessionProvider) {
    super(sessionProvider);
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
