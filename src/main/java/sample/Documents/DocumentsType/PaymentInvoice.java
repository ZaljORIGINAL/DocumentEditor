package sample.Documents.DocumentsType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.AppContext;
import sample.Documents.Document;
import sample.Documents.ResourcesType.PackingListResource;
import sample.Documents.ResourcesType.PaymentInvoiceResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PaymentInvoice extends Document {
    private static final Logger LOG = Logger.getLogger(PaymentInvoice.class.getName());

    public PaymentInvoice(Path file){
        super(file);
    }

    @Override
    public void readFile() throws IOException, ParseException {
        LOG.info("Чтение документа Заявка на оплату по пути:" + file.getPath());
        FileReader stream = new FileReader(file);
        BufferedReader reader = new BufferedReader(stream);
        //Чтение номера документа
        String documentNumber = reader.readLine();
        LOG.info("Прочтен номер документа: " + documentNumber);
        //Чтение даты
        Date date = null;
        String dateStr = reader.readLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");
        date = formatter.parse(dateStr);
        LOG.info("Прочтена дата документа: " + date.toString());
        //Чтение имени
        String userName = reader.readLine();
        LOG.info("Прочтено имя клиента: " + userName);
        //Чтение контрагента
        String counterAgent = reader.readLine();
        LOG.info("Прочтено имя клиента: " + counterAgent);
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
        //Чтение комиссии
        String commissionStr = reader.readLine();
        float commission = Float.parseFloat(currencyRateStr);
        LOG.info("Прочтена комиссия: " + currencyRate);

        reader.close();
        LOG.info("Поток чтения ЗАКРЫТ!");

        //Компановка ресурсов файла
        resource = new PaymentInvoiceResource(
                documentNumber,
                date,
                userName,
                counterAgent,
                price,
                currency,
                currencyRate,
                commission);
        LOG.info("Данные помещены в кэш!");
    }

    @Override
    public void writeFile() throws IOException {
        LOG.info("Запись данных документа Заявка на оплату по пути:" + file.toUri());
        PaymentInvoiceResource resourceToSave = (PaymentInvoiceResource) resource;
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
        //Запись контрагента
        writer.write(resourceToSave.getCounterAgent() + "\n");
        LOG.info("Записан контрагент: " + resourceToSave.getCounterAgent());
        //Запись цены
        writer.write(resourceToSave.getPrice() + "\n");
        LOG.info("Записана стоимость: " + resourceToSave.getPrice());
        //Запись валюты
        writer.write(resourceToSave.getCurrency() + "\n");
        LOG.info("Записана валюта: " + resourceToSave.getCurrency());
        //Запись курса валюты
        writer.write(resourceToSave.getCurrencyRate() + "\n");
        LOG.info("Записан курс валюты: " + resourceToSave.getCurrencyRate());
        //Запись комиссии
        writer.write(resourceToSave.getCommission() + "\n");
        LOG.info("Записан комиссии: " + resourceToSave.getCommission());

        writer.close();
        LOG.info("Поток записи ЗАКРЫТ!");
    }
}
