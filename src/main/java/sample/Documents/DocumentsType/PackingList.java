package sample.Documents.DocumentsType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.AppContext;
import sample.Documents.Document;
import sample.Documents.ResourcesType.PackingListResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PackingList extends Document {
    private static Logger LOG;
    static {
        try {
            ApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
            var resource = ctx.getResource("classpath:logger.properties");
            Path loggerFile = Paths.get(resource.getURI());
            //TODO Что вернеть метод toString?
            FileInputStream file = new FileInputStream(loggerFile.toFile());
            LogManager.getLogManager().readConfiguration(file);
            LOG = Logger.getLogger(PackingList.class.getName());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public PackingList(Path file){
        super(file);
    }

    @Override
    public void readFile() throws IOException {
        //TODO Что вернеть метод toUri?
        LOG.info("Чтение документа Накладаная по пути:" + file.toUri());
        FileReader stream = new FileReader(file.toFile());
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
        //Чтение валюты
        String currency = reader.readLine();
        LOG.info("Прочтена валюта: " + currency);
        //Чтение курса валюты
        String currencyRateStr = reader.readLine();
        float currencyRate = Float.parseFloat(currencyRateStr);
        LOG.info("Прочтен курс валюта: " + currencyRate);
        //Чтение продукта
        String product = reader.readLine();
        LOG.info("Прочтен продукт: " + product);
        //Чтение количества
        String countStr = reader.readLine();
        float count = Float.parseFloat(countStr);
        LOG.info("Прочтено количетсво продука: " + count);

        reader.close();
        LOG.info("Поток чтения ЗАКРЫТ!");

        //Компановка ресурсов файла
        resource = new PackingListResource(
                documentNumber,
                date,
                userName,
                price,
                currency,
                currencyRate,
                product,
                count);
        LOG.info("Данные помещены в кэш!");
    }

    @Override
    public void writeFile() throws IOException {
        LOG.info("Запись данных документа Накладаная по пути:" + file.toUri());
        PackingListResource resourceToSave = (PackingListResource) resource;
        if (Files.exists(file)){
            LOG.info("Удаление устаревших данных...");
            Files.delete(file);
            LOG.info("Устаревшие данные удалены!");
        }

        FileWriter stream = new FileWriter(file.toFile());
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
        //Запись валюты
        writer.write(resourceToSave.getCurrency() + "\n");
        LOG.info("Записана валюта: " + resourceToSave.getCurrency());
        //Запись курса валюты
        writer.write(resourceToSave.getCurrencyRate() + "\n");
        LOG.info("Записан курс валюты: " + resourceToSave.getCurrencyRate());
        //Запись продукта
        writer.write(resourceToSave.getProduct() + "\n");
        LOG.info("Записан продукт: " + resourceToSave.getProduct());
        //Чтение количества
        writer.write(resourceToSave.getCount() + "\n");
        LOG.info("Записано количетсво продука: " + resourceToSave.getCount());

        writer.close();
        LOG.info("Поток записи ЗАКРЫТ!");
    }
}
