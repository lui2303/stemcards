package com.luiswiederhold.backend.flashcards;

import java.util.Date;

public abstract class Flashcard {
    // maybe abstract class

    /*
    Schema:

    {
        "is_url_to_ressource": false,
        "value" : "Latex output oder link zu der Datei, die handschrifltich geschriebenes speichert. Ggf. muss dieses image für den browser rescaled werden" TODO: endpoint /rescale implementieren oder direkt im frontend vornehmen
        "creation_date": 23.03.2005,
        "last_updated_on":23.03.2008,
        "last_revised_on": 23.03.2009,
    }
     */
     private boolean isUrlToResource;
     private String value;
     private Date creationDate;
     private Date lastUpdatedOn;
     private Date lastRevisedOn;

    public Flashcard(boolean isUrlToResource, String value, Date creationDate, Date lastUpdatedOn, Date lastRevisedOn) {
        this.isUrlToResource = isUrlToResource;
        this.value = value;
        this.creationDate = creationDate;
        this.lastUpdatedOn = lastUpdatedOn;
        this.lastRevisedOn = lastRevisedOn;
    }

    public boolean isUrlToResource() {
        return isUrlToResource;
    }

    public void setUrlToResource(boolean urlToResource) {
        isUrlToResource = urlToResource;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Date getLastRevisedOn() {
        return lastRevisedOn;
    }

    public void setLastRevisedOn(Date lastRevisedOn) {
        this.lastRevisedOn = lastRevisedOn;
    }
}
