package sample.Commands;

import java.io.Serializable;

public abstract class FileAction implements Serializable {
    protected String description;
    protected String pathToFile;

    public FileAction(String description, String pathToFile) {
        this.description = description;
        this.pathToFile = pathToFile;
    }

    public String getDescription() {
        return description;
    }

    public String getPathToFile(){
        return this.pathToFile;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
