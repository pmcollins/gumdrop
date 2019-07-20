package gumdrop.test.web;

class UserFormData {

  private String first;
  private String last;
  private String email;
  private boolean likesPeaches;

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

  boolean likesPeaches() {
    return likesPeaches;
  }

  void setLikesPeaches(boolean likesPeaches) {
    this.likesPeaches = likesPeaches;
  }

}
