package gumdrop.web.controller;

import gumdrop.common.SessionSupplier;
import gumdrop.web.html.Buildable;
import gumdrop.web.html.View;
import gumdrop.web.html.ViewModel;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;

public abstract class LayoutController<E, M extends ViewModel, L extends ViewModel> extends SessionController<E> {

  private static final int CAPACITY = 1024 * 128;

  private final View<M> view;
  private final WrapperView<L> wrapperView;

  protected LayoutController(SessionSupplier<E> sessionSupplier, View<M> view, WrapperView<L> wrapperView) {
    super(sessionSupplier);
    this.view = view;
    this.wrapperView = wrapperView;
  }

  @Override
  protected void process(HttpResponse response) {
    StringBuilder sb = new StringBuilder(CAPACITY);
    L layoutModel = getLayoutPresenter().populateViewModel();
    wrapperView.wrap(sb, layoutModel, new BuildableView<>(view, getViewModel()));
    HeaderUtil.setHtmlResponseType(response.getHeader(), sb.length());
    response.setBytes(sb.toString().getBytes());
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
