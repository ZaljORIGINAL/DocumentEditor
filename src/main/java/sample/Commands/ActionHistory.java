package sample.Commands;

import sample.Commands.Actions.DocumentCreated;
import sample.Commands.Actions.DocumentImported;
import sample.Documents.DocumentsManager;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ActionHistory {
    private static final long SerialVersionUID = 10l;
    private static Logger LOG;
    static {
        try {
            FileInputStream file = new FileInputStream(
                    "D:\\Программирование\\Java\\Практическая работа\\NewForm\\src\\main\\resources\\logger.properties");
            LogManager.getLogManager().readConfiguration(file);
            LOG = Logger.getLogger(DocumentsManager.class.getName());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private ArrayList<FileAction> actions;

    public ActionHistory(){
        LOG.info("Конструирование объекта истории действий над файлами");
        try{
            readData();
        }catch (IOException exception){
            actions = new ArrayList<>();
        }
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

    public void readData() throws IOException {
        LOG.info("Востановление прежныжних данных...");
        File file = new File("appCach", "actionHistoryData.bin");
        ObjectInputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectInputStream(
                new FileInputStream(file));
            LOG.info("Попытка чтени прежныжних данных...");
            actions = (ArrayList<FileAction>) objectOutputStream.readObject();
            LOG.info("Данные востановлены.");
        }catch (Exception exception){
            actions = new ArrayList<>();
            LOG.warning("Ошибка в востонавлении данных!." + exception.getMessage());
        }
        if (objectOutputStream != null) {
            objectOutputStream.close();
            LOG.info("Закрытие потока на чтение.");
        }
    }

    public void saveData() throws IOException{
        LOG.info("Сохранение историй действий над документами...");
        File file = new File("appCach", "actionHistoryData.bin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(file));
        LOG.info("Попытка сохранения данных...");
        objectOutputStream.writeObject(actions);
        objectOutputStream.close();
        LOG.info("Закрытие потока на чтение.");
    }
}
