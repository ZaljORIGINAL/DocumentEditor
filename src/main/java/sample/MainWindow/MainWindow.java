package sample.MainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.AppContext;
import sample.Commands.ActionHistory;
import sample.Commands.Actions.DocumentCreated;
import sample.Commands.Actions.DocumentImported;
import sample.Commands.FileAction;
import sample.DialogFragments.BuildersDialog.PackingListDialogFragment;
import sample.DialogFragments.BuildersDialog.PaymentInvoiceDialogFragment;
import sample.DialogFragments.BuildersDialog.PaymentOrderDialogFragment;
import sample.DialogFragments.DocumentBuildDialogFragment;
import sample.DialogFragments.PathSelected;
import sample.DialogFragments.ShowerDialogFragment;
import sample.Documents.Document;
import sample.Documents.DocumentsManager;
import sample.Documents.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    public ListView filesList;
    public Button packingListButton;
    public Button showDocumentButton;
    public Button importDocumentButton;
    public Button exitButton;
    private Stage primaryStage;
    private ApplicationContext ctx;

    public MainWindow(Stage primaryStage){
        ctx = new AnnotationConfigApplicationContext(AppContext.class);
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        updateFilesList();
    }

    public void clickCreatePackingList(ActionEvent actionEvent) {
        DocumentBuildDialogFragment fragment =
                new PackingListDialogFragment(primaryStage);
        fragment.initFragmentView();
        createNewDocument(fragment);
    }

    public void clickCreatePaymentList(ActionEvent actionEvent){
        DocumentBuildDialogFragment fragment =
                new PaymentOrderDialogFragment(primaryStage);
        fragment.initFragmentView();
        createNewDocument(fragment);
    }

    public void clickCreatePaymentInvoice(ActionEvent actionEvent){
        DocumentBuildDialogFragment fragment =
                new PaymentInvoiceDialogFragment(primaryStage);
        fragment.initFragmentView();
        createNewDocument(fragment);
    }

    public void clickShowSelectedDocument(ActionEvent actionEvent) {
        FileAction action = (FileAction) filesList
                .getSelectionModel().getSelectedItem();
        DocumentsManager manager =
                ctx.getBean(DocumentsManager.class);
        try {
            File fileToOpen = new File(action.getPathToFile());
            Document document = manager.getDocument(fileToOpen);
            document.readFile();
            //TODO Создать диалоговое окно, которое продеманстриует ресур документа переданный ему.

            Dialog<Pair<String, DocumentCreated>> dialog = new Dialog<>();
            dialog.setTitle("Информация о документе");
            dialog.setHeaderText(document.getDocumentType());

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

            ShowerDialogFragment fragment = new ShowerDialogFragment(document);
            fragment.initFragmentView();
            dialog.getDialogPane().setContent(fragment.getMainPanel());
            dialog.showAndWait();
        }catch (FileNotFoundException exception){
            exception.printStackTrace();
        }catch (RuntimeException exception){
            exception.printStackTrace();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    //FIXME Не происходит проверка на действительность номера документа
    public void clickImportDocument(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File fileToImport = fileChooser.showOpenDialog(primaryStage);

        if (fileToImport != null){
            try{
                DocumentsManager manager = ctx.getBean(DocumentsManager.class);
                Document documentToImport = manager.getDocument(fileToImport);
                documentToImport.readFile();
                Resource resource = documentToImport.getResource();
                ActionHistory actionHistory = ctx.getBean(ActionHistory.class);

                StringBuilder descriptionToAction = new StringBuilder();
                //Наименование
                descriptionToAction.append(resource.getDocumentType());
                //Дата
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(resource.getDate());
                descriptionToAction.append("от " +
                        calendar.get(Calendar.DAY_OF_MONTH) + "." +
                        calendar.get(Calendar.MONTH) + "." +
                        calendar.get(Calendar.YEAR) + " ");
                //Номер документа
                descriptionToAction.append("номер " + resource.getDocumentNumber());
                //Создание момента действия
                DocumentImported action = new DocumentImported(
                        descriptionToAction.toString(),
                        fileToImport.getPath());

                actionHistory.addAction(action);
                filesList.getItems().add(action);
            }catch (FileNotFoundException exception){
                exception.printStackTrace();
            }catch (RuntimeException exception){
                //TODO Можно предоставить пользователю самостоятельно выбрать тип файла
                exception.printStackTrace();
            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }

    public void clickCloseProgram(ActionEvent actionEvent) {
        try{
            ActionHistory actionHistory = ctx.getBean(ActionHistory.class);
            actionHistory.saveData();
        }catch (Exception exception){
            exception.printStackTrace();
        }

        primaryStage.close();
    }

    private void updateFilesList(){
        ActionHistory history = ctx.getBean(ActionHistory.class);
        ArrayList<FileAction> list = history.getNewDocumentsAction();
        for (FileAction action: list) {
            filesList.getItems().add(action);
        }
    }

    private void createNewDocument(DocumentBuildDialogFragment fragment){
        Dialog<Pair<String, DocumentCreated>> dialog = new Dialog<>();
        dialog.setTitle("Конструктор документа");
        dialog.setHeaderText("Заполните все поля");

        //Настройка кнопок
        ButtonType createButtonType = new ButtonType("Создать", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);
        fragment.setCallBack(() -> createButton.setDisable(false));

        dialog.getDialogPane().setContent(fragment.getMainPanel());

        //FIXME Что то поплыл. Разобраться что за ключь. Кто ты такой этот Pair
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                DocumentCreated action = fragment.buildDocument();
                return new Pair<>(
                        "answer",
                        action);
            }
            return null;
        });

        Optional<Pair<String, DocumentCreated>> result = dialog.showAndWait();
        result.ifPresent(answer -> {
            DocumentCreated action = answer.getValue();
            if (action != null){
                ActionHistory history = ctx.getBean(ActionHistory.class);
                history.addAction(action);
                filesList.getItems().add(action);
            };
        });
    }
}
