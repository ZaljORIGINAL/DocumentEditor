package sample.Documents;

import java.io.File;
import java.io.IOException;

public abstract class Document {
    protected File file;
    protected Resource resource;

    public Document(){};

    public Document(File file){
        this.file = file;
    }

    public String getDocumentType(){
        return resource.getDocumentType();
    }

    public File getFile() {
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
