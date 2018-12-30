package com.info;

public class UserInfo<T> {

    /**
     * customerID 为病人卡号 身份证读取 不读取医保卡号
     * customerNo 为病人身份证 三代二代医保卡读卡读取身份证号
     * CustomerNoType 为病人证件号码1 身份证 2 护照 3 其他
     * customerName 病人姓名
     * customerTel 预约手机号
     * schedulingId 排班别号
     */

    private String customerID = "";
    private String customerNo = "";
    private String customerNoType = "";
    private String customerName = "";
    private String customerTel = "";
    private String schedulingID = "";

    private T param;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerNoType() {
        return customerNoType;
    }

    public void setCustomerNoType(String customerNoType) {
        this.customerNoType = customerNoType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getSchedulingID() {
        return schedulingID;
    }

    public void setSchedulingID(String schedulingID) {
        this.schedulingID = schedulingID;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
