package gumdrop.web.controller;

import gumdrop.common.http.Request;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.http.HttpResponseHeader;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static gumdrop.common.ExceptionUtil.get;

public class StaticController implements Controller {

  private static final FileSystem FILE_SYSTEM = FileSystems.getDefault();

  private final String dir;
  private String path;
  private String[] matches;

  public StaticController(String dir) {
    this.dir = dir;
  }

  @Override
  public void visit(HasControllerIndex dispatcher) {
  }

  @Override
  public void setPathArgs(String[] matches) {
    this.matches = matches;
    path = matches[0];
  }

  @Override
  public String[] getPathArgs() {
    return matches;
  }

  @Override
  public HttpResponse process(Request request) {
    Path fullPath = FILE_SYSTEM.getPath(dir, path);
    byte[] bytes = get(() -> Files.readAllBytes(fullPath));
    HttpResponseHeader h = new HttpResponseHeader();
    if (path.endsWith(".css")) {
      HeaderUtil.setTextCss(h, bytes.length);
    } else {
      throw new RuntimeException("don't know nothin about no " + path);
    }
    HttpResponse response = new HttpResponse(h);
    response.setBytes(bytes);
    return response;
  }

}
