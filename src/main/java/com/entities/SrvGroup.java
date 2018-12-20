package com.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SrvGroup {

    private StringProperty srvGroupId;
    private StringProperty srvGroupName;
    private StringProperty srvGroupLetter;
    private StringProperty srvGroupCode;

    public String getSrvGroupId() {
        return srvGroupId.get();
    }

    public StringProperty srvGroupIdProperty() {
        return srvGroupId;
    }

    public void setSrvGroupId(String srvGroupId) {
        this.srvGroupId = new SimpleStringProperty(srvGroupId);
    }

    public String getSrvGroupName() {
        return srvGroupName.get();
    }

    public StringProperty srvGroupNameProperty() {
        return srvGroupName;
    }

    public void setSrvGroupName(String srvGroupName) {
        this.srvGroupName = new SimpleStringProperty(srvGroupName);
    }

    public String getSrvGroupLetter() {
        return srvGroupLetter.get();
    }

    public StringProperty srvGroupLetterProperty() {
        return srvGroupLetter;
    }

    public void setSrvGroupLetter(String srvGroupLetter) {
        this.srvGroupLetter = new SimpleStringProperty(srvGroupLetter);
    }

    public String getSrvGroupCode() {
        return srvGroupCode.get();
    }

    public StringProperty srvGroupCodeProperty() {
        return srvGroupCode;
    }

    public void setSrvGroupCode(String srvGroupCode) {
        this.srvGroupCode= new SimpleStringProperty(srvGroupCode);
    }
}
