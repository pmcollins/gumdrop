package gumdrop.web.control;

import gumdrop.web.html.ViewModel;

public interface Presenter<T extends ViewModel> {

  T populateViewModel();

}
