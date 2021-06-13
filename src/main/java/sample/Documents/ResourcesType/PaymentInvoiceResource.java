package sample.Documents.ResourcesType;

import sample.Documents.Resource;

import java.util.Date;

public class PaymentInvoiceResource extends Resource {
    private String userName;
    private String counterAgent;
    private float price;
    private String currency;
    private float currencyRate;
    private float commission;

    public PaymentInvoiceResource(){}

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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCounterAgent() {
        return counterAgent;
    }

    public void setCounterAgent(String counterAgent) {
        this.counterAgent = counterAgent;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(float currencyRate) {
        this.currencyRate = currencyRate;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }
}
