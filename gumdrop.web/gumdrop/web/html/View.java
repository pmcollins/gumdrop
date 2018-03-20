package gumdrop.web.html;

import java.util.function.BiConsumer;

public interface View<T> extends BiConsumer<StringBuilder, T> { }
