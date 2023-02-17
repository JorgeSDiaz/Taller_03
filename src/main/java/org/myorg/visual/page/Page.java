package org.myorg.visual.page;

import org.myorg.logic.Route;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class Page implements Pages {

    protected Route content;
    protected Consumer consumer;
    protected List<String> header;

    public <T> Page(Route content, Consumer<T> consumer, List<String> header) {
        this.content = content;
        this.consumer = consumer;
        this.header = header;
    }

    @Override
    public String getContent() throws IOException {
        return this.content.getContent();
    }

    @Override
    public <T> Consumer<T> getConsumer() {
        return this.consumer;
    }

    @Override
    public List<String> getHeader() {
        return this.header;
    }
}
