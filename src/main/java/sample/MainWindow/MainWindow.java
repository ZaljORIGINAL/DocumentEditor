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
import sample.DialogFragments.ShowerDialogFragment;
import sample.Documents.Document;
import sample.Documents.DocumentsManager;
import sample.Documents.Resource;
import sample.Exceptions.IOExceptions.DocumentCreateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainWindow implements Initializable {
    private static final Logger LOG = Logger.getLogger(DocumentsManager.class.getName());

    public ListView filesList;
    public Button packingListButton;
    public Button showDocumentButton;
    public Button importDocumentButton;
    public Button exitButton;
    private Stage primaryStage;
    private ApplicationContext ctx;

    public MainWindow(Stage primaryStage){
        LOG.info("Запуск конструктора...");
        ctx = new AnnotationConfigApplicationContext(AppContext.class);
        this.primaryStage = primaryStage;
        LOG.info("ККОнструктор завершено.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOG.info("Инициализация граффического интерфеса...");
        filesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        updateFilesList();
        LOG.info("Инициализация граффического интерфеса завершено.");
    }

    public void clickCreatePackingList(ActionEvent actionEvent) {
        DocumentBuildDialogFragment fragment =
                new PackingListDialogFragment(primaryStage);
        try {
            var dialog = buildDocumentConstructorView(fragment);
            Optional<Pair<String, DocumentCreated>> result = dialog.showAndWait();
            result.ifPresent(answer -> {
                DocumentCreated action = answer.getValue();
                if (action != null){
                    ActionHistory history = ctx.getBean(ActionHistory.class);
                    history.addAction(action);
                    filesList.getItems().add(action);
                };
            });
        } catch (IOException exception) {
            LOG.info(exception.getMessage());
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка в исполнении");
            alert.setContentText("Неудалось вызвать окно конструктор документа!");
            alert.showAndWait();
        }
    }

    public void clickCreatePaymentList(ActionEvent actionEvent){
        DocumentBuildDialogFragment fragment =
                new PaymentOrderDialogFragment(primaryStage);
        try {
            var dialog = buildDocumentConstructorView(fragment);
            Optional<Pair<String, DocumentCreated>> result = dialog.showAndWait();
            result.ifPresent(answer -> {
                DocumentCreated action = answer.getValue();
                if (action != null){
                    ActionHistory history = ctx.getBean(ActionHistory.class);
                    history.addAction(action);
                    filesList.getItems().add(action);
                };
            });
        } catch (IOException exception) {
            LOG.info(exception.getMessage());
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка в исполнении");
            alert.setContentText("Неудалось вызвать окно конструктор документа!");
            alert.showAndWait();
        }
    }

    public void clickCreatePaymentInvoice(ActionEvent actionEvent){
        DocumentBuildDialogFragment fragment =
                new PaymentInvoiceDialogFragment(primaryStage);
        try {
            var dialog = buildDocumentConstructorView(fragment);
            Optional<Pair<String, DocumentCreated>> result = dialog.showAndWait();
            result.ifPresent(answer -> {
                DocumentCreated action = answer.getValue();
                if (action != null){
                    ActionHistory history = ctx.getBean(ActionHistory.class);
                    history.addAction(action);
                    filesList.getItems().add(action);
                };
            });
        } catch (IOException exception) {
            LOG.info(exception.getMessage());
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка в исполнении");
            alert.setContentText("Неудалось вызвать окно конструктор документа!");
            alert.showAndWait();
        }
    }

    public void clickShowSelectedDocument(ActionEvent actionEvent) {
        LOG.info("Отобразить информацию по выбранному документу...");
        FileAction action = (FileAction) filesList
                .getSelectionModel().getSelectedItem();
        LOG.info("Описание выбранного документа: " + action.getDescription() + "\n" +
                "Путь к документу: " + action.getPathToFile());
        Path fileToOpen = Paths.get(action.getPathToFile());

        DocumentsManager manager =
                ctx.getBean(DocumentsManager.class);

        try {
            Document document = manager.getDocument(fileToOpen);
            document.readFile();
            var dialog = buildShowerDialog(document);
            dialog.showAndWait();
        }catch (FileNotFoundException exception){
            LOG.info(exception.getMessage());
            exception.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Фаил не найден");
            alert.setHeaderText("Неудалось считать параметры контент из фала");
            alert.setContentText("Фаил не найден. Предпологаемое расположение: " +
                    fileToOpen.toUri());

            alert.showAndWait();
        }catch (IOException exception){
            LOG.info("Ошибка в конструировании диалогового окна. " +
                    exception.getMessage());
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка в исполнении");
            alert.setContentText("Неудалось вызвать окно демонстрации контента документа!");
            alert.showAndWait();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    //FIXME Не происходит проверка на действительность номера документа
    public void clickImportDocument(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Path fileToImport = fileChooser
                        .showOpenDialog(primaryStage)
                        .toPath();

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
                        fileToImport.toFile().getPath());//TODO Перепроверить

                actionHistory.addAction(action);
                filesList.getItems().add(action);
            }catch (FileNotFoundException exception){
                exception.printStackTrace();
            }catch (RuntimeException exception){
                //TODO Можно предоставить пользователю самостоятельно выбрать тип файла
                exception.printStackTrace();
            }catch (IOException exception){
                exception.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickCloseProgram(ActionEvent actionEvent) {
        LOG.info("Завершение программы...");
        try{
            LOG.info("Сохранение истории действий...");
            ActionHistory actionHistory = ctx.getBean(ActionHistory.class);
            actionHistory.saveData();
            LOG.info("История действий сохранена.");
        }catch (IOException exception){
            LOG.info("Ошибка в сохранении истории действий! " + exception.getMessage());
            exception.printStackTrace();
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Ошибка");
            dialog.setContentText("Ошибка при сохаранении истории действий!");
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

    private Dialog buildShowerDialog(Document document) throws IOException{
        LOG.info("Формирование окна для демонстрации контента документа...");
        Dialog<Pair<String, DocumentCreated>> dialog = new Dialog<>();
        LOG.info("Установка имени окна.");
        dialog.setTitle("Информация о документе");
        LOG.info("Установка заголовка окна.");
        dialog.setHeaderText(document.getDocumentType());

        LOG.info("Настройка элементов управлени окна...");
        ShowerDialogFragment fragment = new ShowerDialogFragment(document);
        fragment.initFragmentView();
        var fragmentPanel = fragment.getMainPanel();
        LOG.info("Получен фрагмент.");
        dialog.getDialogPane().setContent(fragmentPanel);
        LOG.info("Панель конструктора установлена в диалоговое окно.");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        LOG.info("Графические элементы управления установлены.");

        LOG.info("Диалоговое окно успешно сконструирован.");
        return dialog;
    }

    private Dialog buildDocumentConstructorView(DocumentBuildDialogFragment fragment) throws IOException{
        LOG.info("Конструирование графического диалогового кона для конструктора...");
        Dialog<Pair<String, DocumentCreated>> dialog = new Dialog<>();
        LOG.info("Установка имени окна.");
        dialog.setTitle("Конструктор документа");
        LOG.info("Установка заголовка окна.");
        dialog.setHeaderText("Заполните все поля");

        //Настройка кнопок
        LOG.info("Настройка элементов управлени окна...");
        fragment.initFragmentView();
        var constructorFragment = fragment.getMainPanel();
        LOG.info("Получен фрагмент конструктора.");
        dialog.getDialogPane().setContent(constructorFragment);
        LOG.info("Панель конструктора установлена в диалоговое окно.");
        ButtonType createButtonType = new ButtonType("Создать", ButtonBar.ButtonData.OK_DONE);
        LOG.info("Создана кнопка СОЗДАТЬ.");
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        LOG.info("Графические элементы управления установлены.");

        LOG.info("Параметризация кнопок диалогового окна...");
        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);
        fragment.setCallBack(() -> createButton.setDisable(false));
        LOG.info("Парамеры успешно установлены.");

        dialog.setResultConverter(dialogButton -> {
                if (dialogButton == createButtonType) {
                    try {
                        DocumentCreated action = fragment.buildDocument();
                        return new Pair<>(
                               "answer",
                                action);
                        /*FIXME Метод buildDocument() ВЫКИДЫВАЕТ IOException
                            Но при запуске выходит ошибка:
                            exception java.io.IOException is never thrown in body of corresponding try statement.
                            Возможно из за того, что в сигнатуре мтода
                            ошибки типа IOException ПРОБРАСЫВАЕТСЯ ВЫШЕ.
                         */
                    }catch (DocumentCreateException exception){
                        LOG.info("Неудалось сконструировать документ. " +
                                exception.getMessage());
                        exception.printStackTrace();
                        Alert messageDialog = new Alert(Alert.AlertType.ERROR);
                        messageDialog.setTitle("Ошибка");
                        messageDialog.setHeaderText("Ошибка в конструировании документа");
                        messageDialog.setContentText(exception.getMessage());
                        messageDialog.showAndWait();
                    }
                }

            return null;
        });

        LOG.info("Диалоговое окно успешно сконструирован.");
        return dialog;
    }
}
