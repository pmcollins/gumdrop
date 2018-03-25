package gumdrop.web;

import gumdrop.common.SessionProvider;
import gumdrop.web.control.Presenter;
import gumdrop.web.html.Buildable;
import gumdrop.web.html.View;
import gumdrop.web.html.ViewModel;
import gumdrop.web.http.HttpResponse;

public abstract class LayoutController<T, M extends ViewModel, L extends ViewModel> extends PageController<T> {

  private final View<M> view;
  private final WrapperView<L> wrapperView;

  protected LayoutController(SessionProvider<T> sessionProvider, View<M> view, WrapperView<L> wrapperView) {
    super(sessionProvider);
    this.view = view;
    this.wrapperView = wrapperView;
  }

  @Override
  protected final void process(HttpResponse response) {
    super.process(response);
  }

  @Override
  protected final void withStringBuilder(StringBuilder sb) {
    L layoutModel = getLayoutPresenter().populateViewModel();
    wrapperView.wrap(sb, layoutModel, new BuildableView<>(view, getViewModel()));
  }

  abstract protected M getViewModel();

  // we can't put this in a call to super so we set it up this way
  abstract protected Presenter<L> getLayoutPresenter();

  private static class BuildableView<M extends ViewModel> implements Buildable {

    private final View<M> view;
    private final M viewModel;

    BuildableView(View<M> view, M viewModel) {
      this.view = view;
      this.viewModel = viewModel;
    }

    @Override
    public void build(StringBuilder sb) {
      view.accept(sb, viewModel);
    }

  }

}
