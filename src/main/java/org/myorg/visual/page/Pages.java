package org.myorg.visual.page;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public interface Pages {
    public String getContent() throws IOException;
    public <T> Consumer<T> getConsumer();
    public List<String> getHeader();
}
