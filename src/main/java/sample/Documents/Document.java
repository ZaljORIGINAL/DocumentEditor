package sample.Documents;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class Document {
    protected Path file;
    protected Resource resource;

    public Document(){};

    public Document(Path file){
        this.file = file;
    }

    public String getDocumentType(){
        return resource.getDocumentType();
    }

    public Path getFile() {
        return file;
    }

    public abstract void readFile() throws IOException;

    public abstract void writeFile() throws IOException;

    public Resource getResource(){
        return resource;
    }

    public void setResource(Resource resource){
        this.resource = resource;
    }
}
