module gumdrop.test {
  requires transitive gumdrop.common;
  requires gumdrop.server;
  requires gumdrop.json;
  requires gumdrop.web;
  requires gumdrop.db;
  requires java.sql;
  exports gumdrop.test.util;
}
