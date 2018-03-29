package gumdrop.test.fake;

import gumdrop.common.http.Request;

public class FakeHttpRequest implements Request {

  private String postString, path;

  @Override
  public String getPostString() {
    return postString;
  }

  @Override
  public byte[] getPost() {
    return new byte[0];
  }

  public void setPostString(String postString) {
    this.postString = postString;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public String getHeader(String key) {
    return null;
  }

  @Override
  public String getCookieString() {
    return null;
  }

  public void setPath(String path) {
    this.path = path;
  }

}
