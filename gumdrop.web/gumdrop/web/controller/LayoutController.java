package gumdrop.web.controller;

import gumdrop.common.Session;
import gumdrop.common.SessionService;
import gumdrop.web.html.Buildable;
import gumdrop.web.html.View;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;

/**
 * @param <S> Session
 * @param <M> ViewModel
 * @param <L> Layout ViewModel
 * @param <E> Session Entity
 */
public abstract class LayoutController<S extends Session<E>, M, L, E> extends SessionController<S, E> {

  private static final int CAPACITY = 1024 * 128;

  private final View<M> view;
  private final WrapperView<L> wrapperView;

  protected LayoutController(SessionService<S> sessionService, View<M> view, WrapperView<L> wrapperView) {
    super(sessionService);
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

  private static class BuildableView<M> implements Buildable {

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
