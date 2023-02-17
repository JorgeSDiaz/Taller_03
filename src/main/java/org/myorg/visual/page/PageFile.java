package org.myorg.visual.page;

import org.myorg.logic.Route;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

public class PageFile extends Page {

    public <T> PageFile(Route content, Consumer<T> consumer, List<String> header) {
        super(content, consumer, header);
    }

    @Override
    public String getContent() throws IOException {
        return fileExist() ?
                Files.readString(Paths.get(this.content.getContent())) :
                "File not found" ;
    }

    public byte[] getContentBytes() throws IOException {
        return fileExist() ?
                Files.readAllBytes(Paths.get(this.content.getContent())) :
                Files.readAllBytes(Paths.get("src/main/resources/nf.jpg"));
    }

    private boolean fileExist() {
        return new File(this.content.getContent()).exists();
    }

}
