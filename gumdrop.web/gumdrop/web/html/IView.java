package gumdrop.web.html;

import java.util.function.BiConsumer;

public interface IView<T> extends BiConsumer<StringBuilder, T> { }
