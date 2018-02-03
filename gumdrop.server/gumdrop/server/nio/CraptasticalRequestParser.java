package gumdrop.server.nio;

import java.nio.ByteBuffer;

@Deprecated
interface CraptasticalRequestParser {

  void append(ByteBuffer bb);

  void parse();

}
