package sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import sample.Commands.ActionHistory;
import sample.Documents.DocumentsManager;

@Configuration
public class AppContext {

    @Bean
    @Scope("singleton")
    public DocumentsManager documentsManager(){
        return new DocumentsManager();
    }

    @Bean
    @Scope("singleton")
    public ActionHistory actionHistory(){
        return new ActionHistory();
    }
}
