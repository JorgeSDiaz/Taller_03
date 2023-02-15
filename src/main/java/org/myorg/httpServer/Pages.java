package org.myorg.httpServer;

import java.util.function.Consumer;

public interface Pages {
    public String getContent();
    public <T> Consumer<T> getConsumer();
}
