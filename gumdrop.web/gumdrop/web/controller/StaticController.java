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
  private final int maxAgeSeconds;

  private String path;
  private String[] matches;

  public StaticController(String dir, Set<String> extensions, int maxAgeSeconds) {
    this.dir = dir;
    this.extensions = extensions;
    this.maxAgeSeconds = maxAgeSeconds;
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

    String extension = getExtension();
    checkExtension(extension);

    switch (extension) {
      case "css":
        HeaderUtil.setTextCssHeaders(h, bytes.length);
        break;
      case "svg":
        HeaderUtil.setImageSvgHeaders(h, bytes.length);
        break;
    }

    h.putAttr("Cache-Control", "public, max-age=" + maxAgeSeconds);

    HttpResponse response = new HttpResponse(h);
    response.setBytes(bytes);
    return response;
  }

  private void checkExtension(String extension) {
    if (!extensions.contains(extension)) {
      throw new RuntimeException("Invalid extension for path: [" + path + "]");
    }
  }

  private String getExtension() {
    int idx = path.lastIndexOf('.');
    return path.substring(idx + 1);
  }

}
