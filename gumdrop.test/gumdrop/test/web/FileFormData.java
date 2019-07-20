package gumdrop.test.web;

import java.util.ArrayList;
import java.util.List;

class FileFormData {

  private final List<String> fileNames = new ArrayList<>();

  void addFileName(String fileName) {
    fileNames.add(fileName);
  }

  List<String> getFileNames() {
    return fileNames;
  }

}
