package gumdrop.test.web;

import gumdrop.common.validation.ValidationFailure;
import gumdrop.common.validation.ValidationFailures;
import gumdrop.common.validation.Validator;
import gumdrop.test.util.Test;
import gumdrop.web.FormReadResult;
import gumdrop.web.HttpFormReader;

import java.util.ArrayList;
import java.util.List;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertTrue;

public class HttpFormReaderTest extends Test {

  public static void main(String[] args) {
    new HttpFormReaderTest().run();
  }

  @Override
  public void run() {
    simple();
    invalid();
    multi();
  }

  private void simple() {
    HttpFormReader<UserFormData> reader = new HttpFormReader<>(UserFormData::new);
    reader.addSetter("first", UserFormData::setFirst);
    reader.addSetter("last", UserFormData::setLast);
    reader.addSetter("email", UserFormData::setEmail);

    FormReadResult<UserFormData> result = reader.read("first=fff&last=lll&email=foo%40bar");

    UserFormData userFormData = result.getFormObject();

    assertEquals("fff", userFormData.getFirst());
    assertEquals("lll", userFormData.getLast());
    assertEquals("foo@bar", userFormData.getEmail());
  }

  private void invalid() {
    HttpFormReader<UserFormData> reader = new HttpFormReader<>(UserFormData::new);
    reader.addSetter("first", UserFormData::setFirst, new Validator<>(s -> s.length() > 1, "must be longer than 1 character"));
    reader.addSetter("last", UserFormData::setLast);
    FormReadResult<UserFormData> read = reader.read("first=f&last=l");
    assertTrue(read.hasValidationFailures());
    ValidationFailures failures = read.getValidationFailures();
    assertEquals(1, failures.getList().size());
    ValidationFailure validationFailure = failures.getList().get(0);
    assertEquals("f", validationFailure.getValue());
  }

  private static class UserFormData {

    private String first;
    private String last;
    private String email;

    String getFirst() {
      return first;
    }

    void setFirst(String first) {
      this.first = first;
    }

    String getLast() {
      return last;
    }

    void setLast(String last) {
      this.last = last;
    }

    String getEmail() {
      return email;
    }

    void setEmail(String email) {
      this.email = email;
    }

  }

  private void multi() {
    String post = "f=rh%2Fpython%2Ftransformation.py&f=rh%2Fpython%2Fdbutil.py&f=rh%2Fpython%2Finserter.py&" +
      "f=rh%2Fpython%2Ftests.py&f=rh%2Fpython%2Fmain.py&f=rh%2FREADME.md&f=rh%2Fcsv%2FScoring.csv&" +
      "f=rh%2Fcsv%2FCoaches.csv&f=rh%2Fcsv%2FAwardsPlayers.csv&f=rh%2Fcsv%2FMaster.csv&f=rh%2Foutput.txt&" +
      "f=rh%2Fsql%2Ftop-coach-players-inline.sql&f=rh%2Fsql%2Fcreate-awards-players.sql&" +
      "f=rh%2Fsql%2Fcreate-view-yearly-player-award-count.sql&f=rh%2Fsql%2Fcreate-view-top-coaches.sql&" +
      "f=rh%2Fsql%2Fcreate-view-top-award-players.sql&f=rh%2Fsql%2Ftop-coach-players.sql&" +
      "f=rh%2Fsql%2Fcreate-view-top-yearly-award-counts.sql&f=rh%2Fsql%2Fcreate-coaches.sql&" +
      "f=rh%2Fsql%2Fcreate-player-teams.sql&f=rh%2Fsql%2Fcreate-master.sql&f=rh%2Fsql%2Fcreate-points-summary.sql&" +
      "f=rh%2Fsql%2Fcreate-view-top-coach-wins.sql&f=rh%2Fsql%2Fcreate-proc-player-rankings.sql&" +
      "f=rh%2Fsql%2Fcoach-ranking.sql";
    HttpFormReader<FileFormData> reader = new HttpFormReader<>(FileFormData::new);
    reader.addSetter("f", FileFormData::addFileName);
    FormReadResult<FileFormData> read = reader.read(post);
    FileFormData formObject = read.getFormObject();
    List<String> fileNames = formObject.getFileNames();
    assertTrue(fileNames.size() > 1);
    System.out.println("fileNames = [" + fileNames + "]");
  }

  private static class FileFormData {
    private final List<String> fileNames = new ArrayList<>();
    void addFileName(String fileName) {
      fileNames.add(fileName);
    }
    public List<String> getFileNames() {
      return fileNames;
    }
  }

}
