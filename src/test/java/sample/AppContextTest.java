package sample;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.Documents.DocumentsManager;

import static org.junit.Assert.*;

public class AppContextTest {

    @Test
    public void GetDocumentManagerTest(){
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        DocumentsManager manager = ctx.getBean(DocumentsManager.class);
        assertNotNull(manager);
    }

    @Test
    public void GetDocumentManagerToSingletonTest(){
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        DocumentsManager manager1 = ctx.getBean(DocumentsManager.class);
        DocumentsManager manager2 = ctx.getBean(DocumentsManager.class);

        assertEquals(manager1.hashCode(), manager2.hashCode());
    }
}