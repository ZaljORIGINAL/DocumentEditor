package sample.DialogFragments.BuildersDialog;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.AppContext;
import sample.Commands.Actions.DocumentCreated;
import sample.DialogFragments.DocumentBuildDialogFragment;
import sample.Documents.DocumentsManager;
import sample.Documents.DocumentsType.PackingList;
import sample.Documents.ResourcesType.PackingListResource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class PackingListDialogFragment extends DocumentBuildDialogFragment {
    private static final Logger LOG = Logger.getLogger(DocumentsManager.class.getName());

    public TextField userName;
    public TextField price;
    public TextField currency;
    public TextField currencyRate;
    public TextField product;
    public TextField count;

    public PackingListDialogFragment(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOG.info("Параметризация граффических элементов управления...");
        LOG.info("Граффических элементы управления настроены.");
    }

    @Override
    public String getPathToFXML() {
        String pathToFXML = "/fragments/fragment_dialog_build_PackingList.fxml";
        return pathToFXML;
    }

    //TODO Очеь неусточиво. Требуется добавить провекру на пустые поля
    @Override
    public DocumentCreated buildDocument() throws IOException {
        LOG.info("Конструирование объекта...");

        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        DocumentsManager manager = ctx.getBean(DocumentsManager.class);

        PackingListResource resource
                = new PackingListResource(
                        manager.getNewNumberToPackingList(),
                        Calendar.getInstance().getTime(),
                        userName.getText(),
                        Float.parseFloat(price.getText()),
                        currency.getText(),
                        Float.parseFloat(currencyRate.getText()),
                        product.getText(),
                        Float.parseFloat(count.getText()));
        LOG.info("Параметры конструируемого документа:" +
                "\n\tНомер документа: " + resource.getDocumentNumber() +
                "\n\tДата: " + resource.getDate().toString() +
                "\n\tКлиент: " + resource.getUserName() +
                "\n\tЦена: " + resource.getPrice() +
                "\n\tВалюта: " + resource.getCurrency() +
                "\n\tКурс валюты: " + resource.getCurrencyRate() +
                "\n\tПродукт: " + resource.getProduct() +
                "\n\tКоличество: " + resource.getCount());

        String documentName = resource.getDocumentNumber() + " Накладная";
        File documentFile = new File(pathToDir, documentName + ".txt");
        var documentPath = documentFile.toPath();
        LOG.info("Путь к файлу: " + documentFile.getPath());
        PackingList document = new PackingList(documentPath);
        document.setResource(resource);
        document.writeFile();

        //Создание момента действия
        String description = manager.getDescription(document);
        var action = new DocumentCreated(
                description,
                documentFile.getPath());

        return action;
    }
}
