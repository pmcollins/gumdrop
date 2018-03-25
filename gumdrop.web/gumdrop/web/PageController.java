package gumdrop.web;

import gumdrop.common.SessionProvider;
import gumdrop.web.SessionController;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;

public abstract class PageController<T> extends SessionController<T> {

  PageController(SessionProvider<T> sessionProvider) {
    super(sessionProvider);
  }

  private static final int CAPACITY = 1024 * 128;

  @Override
  protected void process(HttpResponse response) {
    StringBuilder sb = new StringBuilder(CAPACITY);
    withStringBuilder(sb);
    HeaderUtil.setHtmlResponseType(response.getHeader(), sb.length());
    response.setBytes(sb.toString().getBytes());
  }

  protected abstract void withStringBuilder(StringBuilder sb);

}
