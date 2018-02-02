package gumdrop.server.nio;

import java.nio.ByteBuffer;

interface RequestParser {

  void append(ByteBuffer bb);

  void parse();

}
