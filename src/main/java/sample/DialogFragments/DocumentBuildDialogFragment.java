package sample.DialogFragments;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.AppContext;
import sample.Commands.Actions.DocumentCreated;
import sample.Documents.Document;
import sample.Documents.DocumentsManager;
import sample.Documents.Resource;
import sample.Exceptions.IOExceptions.DocumentCreateException;

import java.io.File;
import java.io.IOException;

public abstract class DocumentBuildDialogFragment implements DialogFragment {
    public Pane mainPane;
    public Button pathSelectButton;
    protected String pathToDir;
    protected Stage primaryStage;
    protected PathSelected callBack;

    public DocumentBuildDialogFragment(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    @Override
    public FXMLLoader initFragmentView() throws IOException {
        String pathToFXML = getPathToFXML();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(pathToFXML));
        loader.setControllerFactory(
                c -> this);
        loader.load();

        return loader;
    }

    @Override
    public Pane getMainPanel(){
        return mainPane;
    }

    public void setCallBack(PathSelected callBack){
        this.callBack = callBack;
    }

    public void clickSelectPath(ActionEvent actionEvent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(primaryStage);
        if (selectedDir != null){
            String path = selectedDir.getPath();
            setPathToDir(path);
        }
    }

    /*FIXME Просмотрев стек вызовов, в случае ошибки создания
       документа, пользователю в диалоговом окне видаст ошибку:
       "Неудалось вызвать окно конструктор документа!". Что
       неверно, ведь окно конструктора успешно запущено.
       Все это происходит из за того что ошибка IOException выпадает
       при записи/чтении файла и то же время при
       формировании граффического окна конструктора документа. (Метод
        buildDocumentConstructorView() -> initFragment())*/
    public DocumentCreated buildDocument() throws DocumentCreateException{
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        DocumentsManager manager = ctx.getBean(DocumentsManager.class);

        Resource resource = generationResource(manager);
        Document document = saveResource(resource);


        //Создание момента действия
        String description = manager.getDescription(document);
        String pathToDocument = document.getFile().toUri().getPath();
        var action = new DocumentCreated(
                description,
                pathToDocument);

        return action;
    }

    protected void setPathToDir(String path){
        pathToDir = path;
        callBack.notif();
    }

    protected abstract Resource generationResource(DocumentsManager manager)
            throws DocumentCreateException;

    protected abstract Document saveResource(Resource resource)
            throws DocumentCreateException;
}