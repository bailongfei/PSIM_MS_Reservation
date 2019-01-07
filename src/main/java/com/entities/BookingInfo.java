package com.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class BookingInfo {

    private StringProperty srvGroupID;
    private StringProperty srvGroupName;
    private StringProperty staffCode;
    private StringProperty staffName;
    private StringProperty srvCodeID;
    private StringProperty srvCodeName;
    private StringProperty schedulingDate;
    private StringProperty schedulingType;
    private StringProperty floor;
    private StringProperty district;
    private StringProperty bookingTime;
    private StringProperty bookingStatus;
    private StringProperty bookingStatusDesc;
    private StringProperty bookingID;
    private StringProperty YYLY;
    private StringProperty queueNo;

    public StringProperty bookingStatusDesc(){
        return bookingStatusDesc;
    }

    public String getSrvGroupID() {
        return srvGroupID.get();
    }

    public StringProperty srvGroupIDProperty() {
        return srvGroupID;
    }

    public void setSrvGroupID(String srvGroupID) {
        this.srvGroupID = new SimpleStringProperty(srvGroupID);
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

    public String getStaffCode() {
        return staffCode.get();
    }

    public StringProperty staffCodeProperty() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = new SimpleStringProperty(staffCode);
    }

    public String getStaffName() {
        return staffName.get();
    }

    public StringProperty staffNameProperty() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = new SimpleStringProperty(staffName);
    }

    public String getSrvCodeID() {
        return srvCodeID.get();
    }

    public StringProperty srvCodeIDProperty() {
        return srvCodeID;
    }

    public void setSrvCodeID(String srvCodeID) {
        this.srvCodeID = new SimpleStringProperty(srvCodeID);
    }

    public String getSrvCodeName() {
        return srvCodeName.get();
    }

    public StringProperty srvCodeNameProperty() {
        return srvCodeName;
    }

    public void setSrvCodeName(String srvCodeName) {
        this.srvCodeName = new SimpleStringProperty(srvCodeName);
    }

    public String getSchedulingDate() {
        return schedulingDate.get();
    }

    public StringProperty schedulingDateProperty() {
        return schedulingDate;
    }

    public void setSchedulingDate(String schedulingDate) {
        this.schedulingDate = new SimpleStringProperty(schedulingDate);
    }

    public String getSchedulingType() {
        return schedulingType.get();
    }

    public StringProperty schedulingTypeProperty() {
        return schedulingType;
    }

    public void setSchedulingType(String schedulingType) {
        this.schedulingType = new SimpleStringProperty(schedulingType);
    }

    public String getFloor() {
        return floor.get();
    }

    public StringProperty floorProperty() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = new SimpleStringProperty(floor);
    }

    public String getDistrict() {
        return district.get();
    }

    public StringProperty districtProperty() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = new SimpleStringProperty(district);
    }

    public String getBookingTime() {
        return bookingTime.get();
    }

    public StringProperty bookingTimeProperty() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = new SimpleStringProperty(bookingTime);
    }

    public String getBookingStatus() {
        return bookingStatus.get();
    }

    public StringProperty bookingStatusProperty() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {

        this.bookingStatus = new SimpleStringProperty(bookingStatus);
        if (getBookingStatus().equals("1")){
            bookingStatusDesc = new SimpleStringProperty("确认");
        } else {
            bookingStatusDesc = new SimpleStringProperty("取消");
        }
    }

    public String getBookingID() {
        return bookingID.get();
    }

    public StringProperty bookingIDProperty() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = new SimpleStringProperty(bookingID);
    }

    public String getYYLY() {
        return YYLY.get();
    }

    public StringProperty YYLYProperty() {
        return YYLY;
    }

    public void setYYLY(String YYLY) {
        this.YYLY = new SimpleStringProperty(YYLY);
    }

    public String getQueueNo() {
        return queueNo.get();
    }

    public StringProperty queueNoProperty() {
        return queueNo;
    }

    public void setQueueNo(String queueNo) {
        this.queueNo = new SimpleStringProperty(queueNo);
    }
}
