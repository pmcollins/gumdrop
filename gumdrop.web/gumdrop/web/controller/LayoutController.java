package gumdrop.web.controller;

import gumdrop.common.Entity;
import gumdrop.common.Session;
import gumdrop.common.SessionService;
import gumdrop.web.html.Buildable;
import gumdrop.web.html.IView;
import gumdrop.web.http.HeaderUtil;
import gumdrop.web.http.HttpResponse;

public abstract class LayoutController<SessionT extends Session<EntityT>, ViewModelT, LayoutViewModelT, EntityT extends Entity> extends SessionController<SessionT, EntityT> {

  private static final int CAPACITY = 1024 * 128;

  private final IView<ViewModelT> view;
  private final WrapperView<LayoutViewModelT> wrapperView;

  protected LayoutController(SessionService<SessionT> sessionService, IView<ViewModelT> view, WrapperView<LayoutViewModelT> wrapperView) {
    super(sessionService);
    this.view = view;
    this.wrapperView = wrapperView;
  }

  @Override
  protected void process(HttpResponse response) {
    StringBuilder sb = new StringBuilder(CAPACITY);
    LayoutViewModelT layoutModel = getLayoutPresenter().getViewModel();
    ViewModelT viewModel = run();
    BuildableView buildableView = new BuildableView(view, viewModel);
    wrapperView.wrap(sb, layoutModel, buildableView, getClass().getSimpleName());
    HeaderUtil.setHtmlResponseHeaders(response.getHeader(), sb.length());
    response.setBytes(sb.toString().getBytes());
  }

  abstract protected ViewModelT run();

  abstract protected IPresenter<LayoutViewModelT> getLayoutPresenter();

  private class BuildableView implements Buildable {

    private final IView<ViewModelT> view;
    private final ViewModelT viewModel;

    BuildableView(IView<ViewModelT> view, ViewModelT viewModel) {
      this.view = view;
      this.viewModel = viewModel;
    }

    @Override
    public void build(StringBuilder sb) {
      view.accept(sb, viewModel);
    }

  }

}
