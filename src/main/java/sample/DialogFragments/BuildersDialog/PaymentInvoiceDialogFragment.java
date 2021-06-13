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
import sample.Documents.DocumentsType.PaymentInvoice;
import sample.Documents.ResourcesType.PackingListResource;
import sample.Documents.ResourcesType.PaymentInvoiceResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PaymentInvoiceDialogFragment extends DocumentBuildDialogFragment {
    private static Logger LOG;
    static {
        try {
            ApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
            var resource = ctx.getResource("classpath:logger.properties");
            File loggerFile = resource.getFile();
            FileInputStream file = new FileInputStream(loggerFile);
            LogManager.getLogManager().readConfiguration(file);
            LOG = Logger.getLogger(DocumentsManager.class.getName());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

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
    public void clickPositiveButton() {
        buildDocument();
    }

    @Override
    public DocumentCreated buildDocument() {
        LOG.info("Конструирование объекта...");
        DocumentCreated action = null;

        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        try{
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
            File documentFile = new File(pathToDir, documentName + ".txt");
            LOG.info("Путь к файлу: " + documentFile.getPath());
            PaymentInvoice document = new PaymentInvoice(documentFile);
            document.setResource(resource);
            document.writeFile();

            //Создание момента действия
            String description = manager.getDescription(document);
            action = new DocumentCreated(
                    description.toString(),
                    documentFile.getPath());

        }catch (IOException exception){
            //TODO Вывести диалоговое окно с сообщением ошибки.
            LOG.warning("Ошибка в создании документа. " + exception.getMessage());
            exception.printStackTrace();
        }

        return action;
    }
}
