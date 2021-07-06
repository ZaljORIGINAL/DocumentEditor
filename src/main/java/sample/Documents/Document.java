package sample.Documents;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public abstract class Document {
    protected final File file;
    protected Resource resource;

    public Document(File file){
        this.file = file;
    }

    public String getDocumentType(){
        return resource.getDocumentType();
    }

    public File getFile() {
        return file;
    }

    public abstract void readFile() throws IOException, ParseException;

    public abstract void writeFile() throws IOException;

    public Resource getResource(){
        return resource;
    }

    public void setResource(Resource resource){
        this.resource = resource;
    }
}
