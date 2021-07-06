package sample.Documents.ResourcesType;

import sample.Documents.DocumentsType.PackingList;
import sample.Documents.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PackingListResource extends Resource {
    private final String userName;
    private final float price;
    private final String currency;
    private final float currencyRate;
    private final String product;
    private final float count;

    public PackingListResource(
            String documentNumber,
            Date date,
            String userName,
            float price,
            String currency,
            float currencyRate,
            String product,
            float count){
        super(documentNumber, date);
        this.userName = userName;
        this.price = price;
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.product = product;
        this.count = count;
    }

    @Override
    public String getDocumentType() {
        return "Накладная ";
    }

    @Override
    public boolean equals(Object obj) {
        PackingListResource resource = (PackingListResource) obj;

        if (!documentNumber.equals(resource.getDocumentNumber()))
            return false;
        if (date.getTime() != resource.getDate().getTime())
            return false;
        if (!userName.equals(resource.getUserName()))
            return false;
        if (price != resource.getPrice())
            return false;
        if (!currency.equals(resource.currency))
            return false;
        if (currencyRate != resource.getCurrencyRate())
            return false;
        if (!product.equals(resource.product))
            return false;
        if (count != resource.getCount())
            return false;

        return true;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public Date getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public float getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public float getCurrencyRate() {
        return currencyRate;
    }

    public String getProduct() {
        return product;
    }

    public float getCount() {
        return count;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        String dateStr = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss")
                .format(getDate());
        string
                .append("Номер документа: " + getDocumentNumber() + "\n")
                .append("Дата: " + dateStr + "\n")
                .append("Пользователь: " + getUserName() + "\n")
                .append("Cумма: " + getPrice() + "\n")
                .append("Валюта: " + getCurrency() + "\n")
                .append("Курс валюты: " + getCurrencyRate() + "\n")
                .append("Товар: " + getProduct() + "\n")
                .append("Количество: " + getCount() + "\n");

        return string.toString();
    }
}
