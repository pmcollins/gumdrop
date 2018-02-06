package gumdrop.server.nio;

import gumdrop.common.CharIterator;
import gumdrop.web.Accumulator;

class PostProcessor implements Accumulator {

  private final RequestParser requestParser;
  private byte[] post;

  PostProcessor(RequestParser requestParser) {
    this.requestParser = requestParser;
  }

  @Override
  public boolean match(CharIterator it) {
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
  public void skip(CharIterator it) {
  }

  public String getPostString() {
    return new String(post);
  }

  public byte[] getPostBytes() {
    return post;
  }

}
