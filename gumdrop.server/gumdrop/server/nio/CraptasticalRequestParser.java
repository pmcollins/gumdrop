package gumdrop.server.nio;

import java.nio.ByteBuffer;

interface CraptasticalRequestParser {

  void append(ByteBuffer bb);

  void parse();

}
