package gumdrop.web.controller;

import gumdrop.web.html.ViewModel;

public interface Presenter<T extends ViewModel> {

  T populateViewModel();

}
