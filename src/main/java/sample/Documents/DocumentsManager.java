package sample.Documents;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.Documents.DocumentsType.PackingList;
import sample.Documents.DocumentsType.PaymentInvoice;
import sample.Documents.DocumentsType.PaymentOrder;
import sample.Documents.ResourcesType.PackingListResource;
import sample.Main;

import java.io.*;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DocumentsManager {
    private final static Logger LOG = Logger.getLogger(DocumentsManager.class.getName());
    /*FIXME Не удается работать с Properties
        Данные задаваемы не сохраняются после завершения работы программы*/
    //private Properties properties;

    public DocumentsManager(){
        LOG.info("Конструируетс объект менеджера документов...");
        LOG.info("Объект менеджера документов создан!");
    }

    public Document getDocument(File file) throws FileNotFoundException{
        if (file.exists()){
            String fileName = file.getName();
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
            throw new FileNotFoundException("Фаил  не обнаружен. Переданный путь: " + file.getPath());

    }

    public String getDescription(Document document){
        StringBuilder descriptionToAction = new StringBuilder();

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

        return descriptionToAction.toString();
    }

    //FIXME Применить XML
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

    //FIXME Применить XML
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

    //FIXME Применить XML
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
