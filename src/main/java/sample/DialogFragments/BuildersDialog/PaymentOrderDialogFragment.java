package sample.DialogFragments.BuildersDialog;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DialogFragments.DocumentBuildDialogFragment;
import sample.Documents.Document;
import sample.Documents.DocumentsManager;
import sample.Documents.DocumentsType.PaymentOrder;
import sample.Documents.Resource;
import sample.Documents.ResourcesType.PaymentOrderResource;
import sample.Exceptions.IOExceptions.DocumentCreateException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class PaymentOrderDialogFragment extends DocumentBuildDialogFragment {
    private static final Logger LOG = Logger.getLogger(PaymentOrderDialogFragment.class.getName());

    public TextField userName;
    public TextField price;
    public TextField employeeName;

    public PaymentOrderDialogFragment(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOG.info("Параметризация граффических элементов управления...");
        LOG.info("Граффических элементы управления настроены.");
    }

    @Override
    public String getPathToFXML() {
        String pathToFXML = "/fragments/fragment_dialog_build_PaymentOrder.fxml";
        return pathToFXML;
    }

    @Override
    protected Resource generationResource(DocumentsManager manager) throws DocumentCreateException {
        try {
            LOG.info("Запрос на уникальный номер документа...");
            String documentNumber = manager.getNewNumberToPackingList();
            LOG.info("Уникальный номер документа получен: " + documentNumber);

            PaymentOrderResource resource
                    = new PaymentOrderResource(
                    documentNumber,
                    Calendar.getInstance().getTime(),
                    userName.getText(),
                    Float.parseFloat(price.getText()),
                    employeeName.getText());
            LOG.info("Параметры конструируемого документа:" +
                    "\n\tНомер документа: " + resource.getDocumentNumber() +
                    "\n\tДата: " + resource.getDate().toString() +
                    "\n\tКлиент: " + resource.getUserName() +
                    "\n\tЦена: " + resource.getPrice() +
                    "\n\tСотрудник: " + resource.getEmployeeName());

            return resource;
        } catch (IOException exception) {
            LOG.info("Ошибка в получении номера. " + exception.getMessage());
            exception.printStackTrace();
            throw new DocumentCreateException(exception.getMessage());
        }
    }

    @Override
    protected Document saveResource(Resource resource) throws DocumentCreateException {
        try{
            String documentName = resource.getDocumentNumber() + " Накладная";
            Path documentPath = new File(pathToDir, documentName + ".txt").toPath();
            LOG.info("Путь к файлу: " + documentPath.toUri().getPath());
            Document document = new PaymentOrder(documentPath);
            document.setResource(resource);
            document.writeFile();
            return document;
        }catch (IOException exception){
            LOG.info("Ошибка в записи конента документа. " + exception.getMessage());
            exception.printStackTrace();
            throw new DocumentCreateException(exception.getMessage());
        }
    }
}
