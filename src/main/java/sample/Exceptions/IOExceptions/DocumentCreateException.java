package sample.Exceptions.IOExceptions;

import java.io.IOException;

public class DocumentCreateException extends IOException {
    public DocumentCreateException(String message){
        super(message);
    }
}
