package com.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 排班信息
 *
 * @srvGroupID 科室编号
 * @staffCode 医生工号
 * @srvCodeID 挂号类型编号
 * @srvCodeName 挂号类型名称
 * @schedulingDate 排班日期
 * @schedulingType 1上午、2下午
 * @floor 楼层
 * @district 院区
 * @schedulingID 排班ID
 * @bookingNum 1可预约、0不可预约
 */
public class SchedulingInfo {

    private StringProperty srvGroupID;
    private StringProperty staffCode;
    private StringProperty srvCodeID;
    private StringProperty srvCodeName;
    private StringProperty schedulingDate;
    private StringProperty schedulingType;
    private StringProperty schedulingTypeDesc;
    private StringProperty floor;
    private StringProperty district;
    private StringProperty schedulingID;
    private StringProperty bookingNum;
    private StringProperty bookingNumDesc;

    public StringProperty schedulingTypeDescProperty() {
        schedulingTypeDesc = new SimpleStringProperty("");
        if (getSchedulingType().equals("1")){
            schedulingTypeDesc.setValue("上午");
        } else {
            schedulingTypeDesc.setValue("下午");
        }
        return schedulingTypeDesc;
    }

    public StringProperty bookingNumDescProperty() {
        bookingNumDesc = new SimpleStringProperty("");
        if (getBookingNum().equals("1")){
            bookingNumDesc.setValue("可预约");
        } else {
            bookingNumDesc.setValue("不可预约");
        }
        return bookingNumDesc;
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

    public String getStaffCode() {
        return staffCode.get();
    }

    public StringProperty staffCodeProperty() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = new SimpleStringProperty(staffCode);
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

    public String getSchedulingID() {
        return schedulingID.get();
    }

    public StringProperty schedulingIDProperty() {
        return schedulingID;
    }

    public void setSchedulingID(String schedulingID) {
        this.schedulingID = new SimpleStringProperty(schedulingID);
    }

    public String getBookingNum() {
        return bookingNum.get();
    }

    public StringProperty bookingNumProperty() {
        return bookingNum;
    }

    public void setBookingNum(String bookingNum) {
        this.bookingNum = new SimpleStringProperty(bookingNum);
    }
}
