package sample.Documents.ResourcesType;

import sample.Documents.Resource;

import java.util.Date;

public class PaymentInvoiceResource extends Resource {
    private final String userName;
    private final String counterAgent;
    private final float price;
    private final String currency;
    private final float currencyRate;
    private final float commission;

    public PaymentInvoiceResource(
            String documentNumber,
            Date date,
            String userName,
            String counterAgent,
            float price,
            String currency,
            float currencyRate,
            float commission){
        super(documentNumber, date);
        this.userName = userName;
        this.counterAgent = counterAgent;
        this.price = price;
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.commission = commission;
    }

    @Override
    public String getDocumentType() {
        return "Заявка на оплату ";
    }

    @Override
    public boolean equals(Object obj) {
        PaymentInvoiceResource resource = (PaymentInvoiceResource) obj;

        if (!documentNumber.equals(resource.getDocumentNumber()))
            return false;
        if (date.getTime() != resource.getDate().getTime())
            return false;
        if (!userName.equals(resource.getUserName()))
            return false;
        if (!counterAgent.equals(resource.getCounterAgent()))
            return false;
        if (price != resource.getPrice())
            return false;
        if (!currency.equals(resource.currency))
            return false;
        if (currencyRate != resource.getCurrencyRate())
            return false;
        if (commission != resource.getCommission())
            return false;

        return true;
    }

    public String getUserName() {
        return userName;
    }

    public String getCounterAgent() {
        return counterAgent;
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

    public float getCommission() {
        return commission;
    }
}
