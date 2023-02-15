package org.myorg.httpServer;

import java.util.function.Consumer;

public class Page implements Pages {

    String content;
    Consumer<String> consumer;

    public Page(String content, Consumer<String> consumer) {
        this.content = content;
        this.consumer = consumer;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public Consumer<String> getConsumer() {
        return this.consumer;
    }
}
