package gumdrop.web.controller;

import gumdrop.common.http.Request;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;
import gumdrop.web.http.HttpResponseHeader;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static gumdrop.common.ExceptionUtil.get;

public class StaticController implements Controller {

  private static final FileSystem FILE_SYSTEM = FileSystems.getDefault();

  private final String dir;
  private final Set<String> extensions;
  private String path;
  private String[] matches;

  public StaticController(String dir, Set<String> extensions) {
    this.dir = dir;
    this.extensions = extensions;
  }

  @Override
  public void setControllerIndex(ControllerIndex dispatcher) {
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
    if (path.contains("..")) {
      throw new RuntimeException("Illegal path access attempt: [" + path + "]");
    }
    Path fullPath = FILE_SYSTEM.getPath(dir, path);
    byte[] bytes = get(() -> Files.readAllBytes(fullPath));
    HttpResponseHeader h = new HttpResponseHeader();
    checkExtension();

    // TODO expand
    HeaderUtil.setTextCss(h, bytes.length);

    HttpResponse response = new HttpResponse(h);
    response.setBytes(bytes);
    return response;
  }

  private void checkExtension() {
    int idx = path.lastIndexOf('.');
    String extension = path.substring(idx + 1);
    if (!extensions.contains(extension)) {
      throw new RuntimeException("Invalid extension for path: [" + path + "]");
    }
  }

}
