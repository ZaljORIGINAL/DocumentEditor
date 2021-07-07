package sample.Commands;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.Commands.Actions.DocumentCreated;
import sample.Commands.Actions.DocumentImported;
import sample.Documents.DocumentsManager;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ActionHistory {
    private static final long SerialVersionUID = 10l;
    private static final Logger LOG = Logger.getLogger(DocumentsManager.class.getName());

    private final List<FileAction> actions;

    public ActionHistory(){
        LOG.info("Конструирование объекта истории действий над файлами");
        List<FileAction> actions;
        try {
            actions = readData();
        }catch (FileNotFoundException | NoSuchFileException exception){
            LOG.info(exception.getMessage());
            LOG.info("Задается пустая история действий!");
            actions = new ArrayList<>();
        }catch (ClassNotFoundException | IOException exception){
            LOG.info(exception.getMessage());
            LOG.info("Задается пустая история действий!");
            actions = new ArrayList<>();
        }
        this.actions = actions;
    }

    public ArrayList<FileAction> getNewDocumentsAction(){
        ArrayList<FileAction> list = new ArrayList<>();

        for (FileAction action:actions) {
            if (action instanceof DocumentCreated || action instanceof DocumentImported)
                list.add(action);
        }

        return list;
    }

    public ArrayList<DocumentCreated> getCreatedDocumentActions(){
        ArrayList<DocumentCreated> list = new ArrayList<>();

        for (FileAction action:actions) {
            if (action instanceof DocumentCreated)
                list.add((DocumentCreated) action);
        }

        return list;
    }

    public ArrayList<DocumentImported> getImportedDocumentActions(){
        ArrayList<DocumentImported> list = new ArrayList<>();

        for (FileAction action:actions) {
            if (action instanceof DocumentImported)
                list.add((DocumentImported) action);
        }

        return list;
    }

    public void addAction(FileAction action){
        actions.add(action);
    }

    public void removeAction(FileAction action){
        actions.remove(action);
    }

    public List<FileAction> readData() throws IOException, ClassNotFoundException {
        LOG.info("Востановление данных...");
        File file = new File("appCach", "actionHistoryData.bin");
        try (ObjectInputStream objectOutputStream = new ObjectInputStream(
                new FileInputStream(file))) {
            return (ArrayList<FileAction>) objectOutputStream.readObject();
        }
    }

    public void saveData() throws IOException{
        LOG.info("Сохранение историй действий над документами...");
        File file = new File("appCach", "actionHistoryData.bin");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(file))){
            LOG.info("Попытка сохранения данных...");
            objectOutputStream.writeObject(actions);
            objectOutputStream.close();
            LOG.info("Закрытие потока на чтение.");
        }
    }
}
