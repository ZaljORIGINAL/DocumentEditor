package sample.DialogFragments;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Documents.Document;
import sample.Documents.DocumentsManager;
import sample.Documents.Resource;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ShowerDialogFragment implements DialogFragment{
    private static final Logger LOG = Logger.getLogger(DocumentsManager.class.getName());

    public AnchorPane mainPane;
    public TextArea infoField;
    private Document document;

    public ShowerDialogFragment(Document document){
        LOG.info("Запуск конструктора...");
        this.document = document;
        LOG.info("ККОнструктор завершено.");
    }

    //TODO Существует ли блок который сработет после всех
    // catch блоков. Вопрос возник из того, что в данном
    // случае требуется оповестить пользователя от
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOG.info("Инициализация граффического интерфеса...");
        try {
            document.readFile();
            Resource resource = document.getResource();
            String info = resource.toString();
            infoField.setText(info);
            LOG.info("Инициализация граффического интерфеса завершено.");
        } catch (ParseException  exception) {
            LOG.info("Ошибка при парсинге даты. " +
                    exception.getMessage());
            exception.printStackTrace();
        } catch (IOException exception){
            LOG.info("Ошибка при чтении контента документа. " +
                    exception.getMessage());
            exception.printStackTrace();
        }
        LOG.info("Инициализация граффического интерфеса завершено.");
    }

    @Override
    public FXMLLoader initFragmentView() throws IOException {
        LOG.info("Инициализация фрагмента, граффические элементы управления...");
        String pathToFXML = getPathToFXML();
        FXMLLoader loader = null;
        loader = new FXMLLoader(
                getClass().getResource(pathToFXML));
        loader.setControllerFactory(
                c -> this);
        loader.load();
        LOG.info("Инициализация фрагмента, граффические элементы управления, успешно завершено...");

        return loader;
    }

    @Override
    public Pane getMainPanel() {
        return mainPane;
    }

    @Override
    public String getPathToFXML() {
        String pathToFXML = "/fragments/fragment_dialog_document_shower.fxml";
        return pathToFXML;
    }
}
