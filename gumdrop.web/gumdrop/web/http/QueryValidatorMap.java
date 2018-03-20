package gumdrop.web.http;

import gumdrop.common.validation.Validator;

import java.util.HashMap;
import java.util.Map;

class QueryValidatorMap {

  private final Map<String, Validator<String>> map = new HashMap<>();

  public void put(String key, Validator<String> validator) {
    map.put(key, validator);
  }

  public Validator<String> get(String key) {
    return map.get(key);
  }

}
