package sample.Documents;

import sample.Documents.DocumentsType.PackingList;
import sample.Documents.DocumentsType.PaymentInvoice;
import sample.Documents.DocumentsType.PaymentOrder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DocumentsManager {
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
    /*FIXME Не удается работать с Properties
        Данные задаваемы не сохраняются после завершения работы программы*/
    //private Properties properties;

    public DocumentsManager(){
        LOG.info("Конструируетс объект менеджера документов...");
        LOG.info("Объект менеджера документов создан!");
    }

    public Document getDocument(Path file) throws FileNotFoundException{
        if (Files.exists(file)){
            //TODO Посмотреть что возвращает
            String fileName = file.getFileName().toString();
            char groupId = fileName.charAt(0);
            switch (groupId){
                case 'A':
                    return new PackingList(file);

                case 'B':
                    return new PaymentOrder(file);

                case 'C':
                    return new PaymentInvoice(file);

                default:
                    throw new RuntimeException("Идентификатор типа документа не зарегестрирован! Переданный индентификатор: " + groupId);
            }
        }else
            throw new FileNotFoundException("Фаил  не обнаружен. Переданный путь: " + file.toUri());

    }

    public String getDescription(Document document){
        StringBuilder descriptionToAction = new StringBuilder();

        try {
            Resource resource = document.getResource();
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
        }catch (RuntimeException exception){
            exception.printStackTrace();
        }

        return descriptionToAction.toString();
    }

    public String getNewNumberToPackingList() throws IOException {
        //String key = "memory.document.PackingList.lastId";

        LOG.info("Генерация идентификатора для документа Накладная...");
        File file = new File("appCach", "PackingListLastId.txt");
        if (!file.exists()){
            FileWriter streamToWrite = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(streamToWrite);
            writer.write(String.valueOf(0));
            writer.close();
        }
        //Получаем последний номер
        FileReader streamToRead = new FileReader(file);
        BufferedReader reader = new BufferedReader(streamToRead);
        String valueStr = reader.readLine();
        reader.close();
        LOG.info("Получено последний номер ID: " + valueStr);
        int number = Integer.parseInt(valueStr);

        //Генерируем новый номер
        ++number;
        LOG.info("Генерация нового номера ID: " + number);

        //Без этого прежний и новый номер слипнуться
        file.delete();
        LOG.info("Прежние данные удалены");

        //Сохраняем новый номер
        FileWriter streamToWrite = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(streamToWrite);
        writer.write(String.valueOf(number));
        writer.close();

        //Генерируем идентификатор
        String newId = "A" + number;
        LOG.info("Сгенерированный идентификатор: " + newId);
        return newId;
    }

    public String getNewNumberToPaymentOrder() throws IOException{
        LOG.info("Генерация идентификатора для документа Платежка...");
        File file = new File("appCach", "PaymentOrderLastId.txt");
        if (!file.exists()){
            FileWriter streamToWrite = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(streamToWrite);
            writer.write(String.valueOf(0));
            writer.close();
        }
        //Получаем последний номер
        FileReader streamToRead = new FileReader(file);
        BufferedReader reader = new BufferedReader(streamToRead);
        String valueStr = reader.readLine();
        reader.close();
        LOG.info("Получено последний номер ID: " + valueStr);
        int number = Integer.parseInt(valueStr);
        //Генерируем новый номер
        ++number;
        LOG.info("Генерация нового номера ID: " + number);
        //Без этого прежний и новый номер слипнуться
        file.delete();
        LOG.info("Прежние данные удалены");
        //Сохраняем новый номер
        FileWriter streamToWrite = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(streamToWrite);
        writer.write(String.valueOf(number));
        writer.close();
        //Генерируем идентификатор
        String newId = "B" + number;
        LOG.info("Сгенерированный идентификатор: " + newId);
        return newId;
    }

    public String getNewNumberToPaymentInvoice() throws IOException{
        LOG.info("Генерация идентификатора для документа Заявка на оплату...");
        File file = new File("appCach", "PaymentInvoiceLastId.txt");
        if (!file.exists()){
            FileWriter streamToWrite = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(streamToWrite);
            writer.write(String.valueOf(0));
            writer.close();
        }
        //Получаем последний номер
        FileReader streamToRead = new FileReader(file);
        BufferedReader reader = new BufferedReader(streamToRead);
        String valueStr = reader.readLine();
        reader.close();
        LOG.info("Получено последний номер ID: " + valueStr);
        int number = Integer.parseInt(valueStr);
        //Генерируем новый номер
        ++number;
        LOG.info("Генерация нового номера ID: " + number);
        //Без этого прежний и новый номер слипнуться
        file.delete();
        LOG.info("Прежние данные удалены");
        //Сохраняем новый номер
        FileWriter streamToWrite = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(streamToWrite);
        writer.write(String.valueOf(number));
        writer.close();
        //Генерируем идентификатор
        String newId = "C" + number;
        LOG.info("Сгенерированный идентификатор: " + newId);
        return newId;
    }
}
