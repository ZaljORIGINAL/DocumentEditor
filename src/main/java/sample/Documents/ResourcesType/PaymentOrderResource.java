package sample.Documents.ResourcesType;

import sample.Documents.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentOrderResource extends Resource {
    private final String userName;
    private final float price;
    private final String employeeName;

    public PaymentOrderResource(
            String documentNumber,
            Date date,
            String userName,
            float price,
            String employeeName){
        super(documentNumber, date);
        this.userName = userName;
        this.price = price;
        this.employeeName = employeeName;
    }

    @Override
    public String getDocumentType() {
        return "Платежка ";
    }

    @Override
    public boolean equals(Object obj) {
        PaymentOrderResource resource = (PaymentOrderResource) obj;

        if (!documentNumber.equals(resource.getDocumentNumber()))
            return false;
        if (date.getTime() != resource.getDate().getTime())
            return false;
        if (!userName.equals(resource.getUserName()))
            return false;
        if (price != resource.getPrice())
            return false;
        if (!employeeName.equals(resource.getEmployeeName()))
            return false;

        return true;
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
                .append("Сотрудник: " + getEmployeeName() + "\n");

        return string.toString();
    }

    public String getUserName() {
        return userName;
    }

    public float getPrice() {
        return price;
    }

    public String getEmployeeName() {
        return employeeName;
    }
}
