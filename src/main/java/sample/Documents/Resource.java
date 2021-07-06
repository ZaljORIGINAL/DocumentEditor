package sample.Documents;

import java.util.Date;

public abstract class Resource {
    protected final String documentNumber;
    protected final Date date;

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
