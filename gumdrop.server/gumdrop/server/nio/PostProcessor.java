package gumdrop.server.nio;

import gumdrop.common.ByteIterator;
import gumdrop.common.Matcher;

class PostProcessor implements Matcher {

  private final RequestParser requestParser;
  private byte[] post;

  PostProcessor(RequestParser requestParser) {
    this.requestParser = requestParser;
  }

  @Override
  public boolean match(ByteIterator it) {
    if (requestParser.getMethod().equals("POST")) {
      int contentLength = Integer.parseInt(requestParser.getAttr("Content-Length"));
      if (it.remaining() == contentLength) {
        post = it.tail();
        return true;
      }
    } else {
      return true;
    }
    return false;
  }

  @Override
  public void skip(ByteIterator it) {
  }

  public String getPostString() {
    return new String(post);
  }

  public byte[] getPostBytes() {
    return post;
  }

}
