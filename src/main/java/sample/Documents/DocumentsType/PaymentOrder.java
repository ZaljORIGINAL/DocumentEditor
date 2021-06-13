package sample.Documents.DocumentsType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.AppContext;
import sample.Documents.Document;
import sample.Documents.DocumentsManager;
import sample.Documents.ResourcesType.PackingListResource;
import sample.Documents.ResourcesType.PaymentOrderResource;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PaymentOrder extends Document {
    private static Logger LOG;
    static {
        try {
            ApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
            var resource = ctx.getResource("classpath:logger.properties");
            File loggerFile = resource.getFile();
            FileInputStream file = new FileInputStream(loggerFile);
            LogManager.getLogManager().readConfiguration(file);
            LOG = Logger.getLogger(PaymentOrder.class.getName());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public PaymentOrder(File file){
        super(file);
    }

    @Override
    public void readFile() throws IOException {
        LOG.info("Чтение документа Накладаная по пути:" + file.getPath());
        FileReader stream = new FileReader(file);
        BufferedReader reader = new BufferedReader(stream);
        //Чтение номера документа
        String documentNumber = reader.readLine();
        LOG.info("Прочтен номер документа: " + documentNumber);
        //Чтение даты
        Date date = null;
        try{
            String dateStr = reader.readLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");
            date = formatter.parse(dateStr);
            LOG.info("Прочтена дата документа: " + date.toString());
        }catch (ParseException exception){
            LOG.warning("Ошибка в чтении даты из фалйа. " + exception.getMessage());
            exception.printStackTrace();
        }
        //Чтение имени
        String userName = reader.readLine();
        LOG.info("Прочтено имя клиента: " + userName);
        //Чтение цены
        String priceStr = reader.readLine();
        float price = Float.parseFloat(priceStr);
        LOG.info("Прочтена стоимость: " + price);
        //Чтение сотрудника
        String employeeName = reader.readLine();
        LOG.info("Прочитан сотрудник: " + employeeName);


        reader.close();
        LOG.info("Поток чтения ЗАКРЫТ!");

        //Компановка ресурсов файла
        resource = new PaymentOrderResource(
                documentNumber,
                date,
                userName,
                price,
                employeeName);
        LOG.info("Данные помещены к кэш!");
    }

    @Override
    public void writeFile() throws IOException {
        LOG.info("Запись данных документа Платежка по пути:" + file.getPath());
        PaymentOrderResource resourceToSave = (PaymentOrderResource) resource;
        if (file.exists()){
            LOG.info("Удаление устаревших данных...");
            file.delete();
            LOG.info("Устаревшие данные удалены!");
        }

        FileWriter stream = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(stream);
        //Запись номера документа
        writer.write(resourceToSave.getDocumentNumber() + "\n");
        LOG.info("Номер документа записан: " + resourceToSave.getDocumentNumber());
        //Запись даты
        String dateStr = new java.text.SimpleDateFormat("dd/MM/yyyy/HH:mm:ss")
                .format(resourceToSave.getDate());
        writer.write(dateStr + "\n");
        LOG.info("Дата записана: " + dateStr);
        //Запись имени
        writer.write(resourceToSave.getUserName() + "\n");
        LOG.info("Записан клиент: " + resourceToSave.getUserName());
        //Запись цены
        writer.write(resourceToSave.getPrice() + "\n");
        LOG.info("Записана стоимость: " + resourceToSave.getPrice());
        //Записать сотрудника
        writer.write(resourceToSave.getEmployeeName());
        LOG.info("Записан сотрудник : " + resourceToSave.getPrice());

        writer.close();
        LOG.info("Поток записи ЗАКРЫТ!");
    }
}
