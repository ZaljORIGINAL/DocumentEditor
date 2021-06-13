package sample.Documents;

import org.junit.Test;

import javax.print.Doc;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class DocumentsManagerTest {

    @Test
    public void getNewNumberToPackingList() {
        DocumentsManager manager = new DocumentsManager();
        String documentId = null;
        try {
            documentId = manager.getNewNumberToPackingList();
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        assertNotNull(documentId);
    }
}