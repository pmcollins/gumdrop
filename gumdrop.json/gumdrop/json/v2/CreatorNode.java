package gumdrop.json.v2;

public interface CreatorNode {

  CreatorNode next();

  CreatorNode next(String key);

  void accept(String value);

}
