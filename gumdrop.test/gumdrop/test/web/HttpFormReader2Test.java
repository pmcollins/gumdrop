package gumdrop.test.web;

import gumdrop.json.v2.*;
import gumdrop.test.util.Test;
import gumdrop.web.controller.ReadResult;
import gumdrop.web.http.HttpFormReader2;

import java.util.List;

import static gumdrop.test.util.Asserts.assertEquals;
import static gumdrop.test.util.Asserts.assertNotNull;

public class HttpFormReader2Test extends Test {

  public static void main(String[] args) throws Exception {
    new HttpFormReader2Test().run();
  }

  @Override
  public void run() throws Exception {
    simple();
    multi();
  }

  private void simple() {
    HttpFormReader2<UserFormData> r = new HttpFormReader2<>(UserFormDataNode::new);
    String q = "first=fff&last=lll&email=foo%40bar&likesPeaches=true";
    ReadResult<UserFormData> res = r.read(q);
    UserFormData userFormData = res.getFormObject();
    assertNotNull(userFormData);
    assertEquals("fff", userFormData.getFirst());
    assertEquals("lll", userFormData.getLast());
    assertEquals("foo@bar", userFormData.getEmail());
    assertEquals(true, userFormData.likesPeaches());
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
    HttpFormReader2<FileFormData> r = new HttpFormReader2<>(FileFormDataNode::new);
    ReadResult<FileFormData> res = r.read(post);
    FileFormData formData = res.getFormObject();
    assertEquals(25, formData.getFileNames().size());
  }

  private static class FileFormDataNode extends PojoNode<FileFormData> {

    FileFormDataNode() {
      super(FileFormData::new, List.of(
        new StringFieldBinding<>("f", FileFormData::addFileName)
      ));
    }

  }

  private static class UserFormDataNode extends PojoNode<UserFormData> {

    UserFormDataNode() {
      super(UserFormData::new, List.of(
        new StringFieldBinding<>("first", UserFormData::setFirst),
        new StringFieldBinding<>("last", UserFormData::setLast),
        new StringFieldBinding<>("email", UserFormData::setEmail),
        // no boolean binding here since we're not accepting a bareword
        new StringFieldBinding<>("likesPeaches", (a, b) -> a.setLikesPeaches(Boolean.valueOf(b)))
      ));
    }

  }

}
