package com.info;

public class ResultMessage<T> {

    private boolean flag;

    private T t;

    private String information;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

//    public int getSfzCode() {
//        return sfzCode;
//    }
//
//    public void setSfzCode(int sfzCode) {
//        this.sfzCode = sfzCode;
//    }
}
