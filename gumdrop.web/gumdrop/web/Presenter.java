package gumdrop.web;

public interface Presenter<T extends ViewModel> {

  T populateViewModel();

}
