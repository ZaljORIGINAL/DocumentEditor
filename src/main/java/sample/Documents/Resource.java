package sample.Documents;

import java.util.Date;

public abstract class Resource {
    protected String documentNumber;
    protected Date date;

    public Resource(){}

    public Resource(String documentNumber, Date date){
        this.documentNumber = documentNumber;
        this.date = date;
    }

    public abstract String getDocumentType();

    public String getDocumentNumber(){
        return documentNumber;
    }

    public Date getDate(){
        return date;
    }
}
