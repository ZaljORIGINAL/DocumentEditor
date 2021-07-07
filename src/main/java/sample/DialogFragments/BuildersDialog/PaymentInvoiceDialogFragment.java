package sample.DialogFragments.BuildersDialog;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.AppContext;
import sample.Commands.Actions.DocumentCreated;
import sample.DialogFragments.DocumentBuildDialogFragment;
import sample.Documents.DocumentsManager;
import sample.Documents.DocumentsType.PaymentInvoice;
import sample.Documents.ResourcesType.PaymentInvoiceResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PaymentInvoiceDialogFragment extends DocumentBuildDialogFragment {
    private static final Logger LOG = Logger.getLogger(DocumentsManager.class.getName());

    public TextField userName;
    public TextField counterAgent;
    public TextField price;
    public TextField currency;
    public TextField currencyRate;
    public TextField commission;

    public PaymentInvoiceDialogFragment(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOG.info("Параметризация граффических элементов управления...");
        LOG.info("Граффических элементы управления настроены.");
    }

    @Override
    public String getPathToFXML() {
        String pathToFXML = "/fragments/fragment_dialog_build_PaymentInvoice.fxml";
        return pathToFXML;
    }

    @Override
    public DocumentCreated buildDocument() throws IOException {
        LOG.info("Конструирование объекта...");

        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        DocumentsManager manager = ctx.getBean(DocumentsManager.class);

        PaymentInvoiceResource resource
                = new PaymentInvoiceResource(
                    manager.getNewNumberToPaymentInvoice(),
                    Calendar.getInstance().getTime(),
                    userName.getText(),
                    counterAgent.getText(),
                    Float.parseFloat(price.getText()),
                    currency.getText(),
                    Float.parseFloat(currencyRate.getText()),
                    Float.parseFloat(commission.getText()));

        LOG.info("Параметры конструируемого документа:" +
                "\n\tНомер документа: " + resource.getDocumentNumber() +
                "\n\tДата: " + resource.getDate().toString() +
                "\n\tКлиент: " + resource.getUserName() +
                "\n\tКонтрагент: " + resource.getCounterAgent() +
                "\n\tЦена: " + resource.getPrice() +
                "\n\tВалюта: " + resource.getCurrency() +
                "\n\tКурс валюты: " + resource.getCurrencyRate() +
                "\n\tКомиссия: " + resource.getCommission());

            String documentName = resource.getDocumentNumber() + " Заявка на оплату";
            //TODO Что то не так как по мне
            Path documentFile = Paths.get(pathToDir, documentName + ".txt");
            LOG.info("Путь к файлу: " + documentFile.toUri());
            PaymentInvoice document = new PaymentInvoice(documentFile);
            document.setResource(resource);
            document.writeFile();

        //Создание момента действия
        String description = manager.getDescription(document);
        var action = new DocumentCreated(
                    description,
                    documentFile.toUri().getPath());

        return action;
    }
}
