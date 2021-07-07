package sample.DialogFragments.BuildersDialog;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.AppContext;
import sample.Commands.Actions.DocumentCreated;
import sample.DialogFragments.DocumentBuildDialogFragment;
import sample.Documents.DocumentsManager;
import sample.Documents.DocumentsType.PaymentOrder;
import sample.Documents.ResourcesType.PaymentOrderResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
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
    public DocumentCreated buildDocument() throws IOException {
        LOG.info("Конструирование объекта...");

        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        DocumentsManager manager = ctx.getBean(DocumentsManager.class);

        PaymentOrderResource resource
                = new PaymentOrderResource(
                        manager.getNewNumberToPaymentOrder(),
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

        String documentName = resource.getDocumentNumber() + " платежка";
        Path documentFile = new File(pathToDir, documentName + ".txt").toPath();
        LOG.info("Путь к файлу: " + documentFile.toUri().getPath());
        PaymentOrder document = new PaymentOrder(documentFile);
        document.setResource(resource);
        document.writeFile();

        //Создание момента действия
        String description = manager.getDescription(document);
        var action = new DocumentCreated(
                    description,
                    documentFile.toUri().getPath());

        return action;
    }

    @Override
    public String getPathToFXML() {
        String pathToFXML = "/fragments/fragment_dialog_build_PaymentOrder.fxml";
        return pathToFXML;
    }
}
