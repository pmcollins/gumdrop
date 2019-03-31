package gumdrop.web.controller;

import gumdrop.common.Entity;
import gumdrop.common.Session;
import gumdrop.common.SessionService;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;

public abstract class DownloadController<T extends Session<E>, E extends Entity> extends SessionController<T, E> {

  protected DownloadController(SessionService<T> sessionService) {
    super(sessionService);
  }

  @Override
  protected final void process(HttpResponse response) {
    byte[] bytes = getBytes();
    HeaderUtil.setOkHeaders(response.getHeader(), bytes.length, getContentType());
    response.setBytes(bytes);
  }

  protected abstract String getContentType();

  protected abstract byte[] getBytes();

}
