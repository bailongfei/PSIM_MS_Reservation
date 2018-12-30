package com.services.impl;

import com.entities.BookingInfo;
import com.entities.BookingResult;
import com.info.ResultMap;
import com.info.UserInfo;
import com.services.BookingServices;
import com.utils.LogUtil;
import com.webservices.HSWebServices.SoapServices;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

public class BookingServicesImpl implements BookingServices {

    @Override
    public ResultMap getSchedulingToday() {
        return null;
    }

    @Override
    public ResultMap cancelBooking(String bookingId) {
        ResultMap resMap = new ResultMap();
        resMap.setResultCode("0");
        resMap.setResultMessage("获取信息异常，BookingServices.java confirmBooking() ");
        try {
            String resp = SoapServices.cancelBooking(bookingId);
            Document document = DocumentHelper.parseText(resp);
            resMap.setResultCode(document.getRootElement().elements().get(0).elements().get(0).attributeValue("pval"));

            resMap.setResultMessage(document.getRootElement().elements().get(0).elements().get(1).attributeValue("pval"));

        } catch (DocumentException e) {
            LogUtil.markLog(2,"BookingServices.java cancelBooking() "+ e.getMessage());
        }
        return resMap;
    }

    @Override
    public ResultMap<BookingResult> confirmBooking(UserInfo userInfo) {
        ResultMap<BookingResult> resMap = new ResultMap<>();
        resMap.setResultCode("0");
        resMap.setResultMessage("获取信息异常，BookingServices.java confirmBooking() ");
        try {

            String resp = SoapServices.confirmBooking(userInfo.getCustomerID(),userInfo.getCustomerNo(),userInfo.getCustomerNoType(),userInfo.getCustomerName(),userInfo.getCustomerTel(),userInfo.getSchedulingID());
            Document document = DocumentHelper.parseText(resp);
            List<Element> list = document.getRootElement().elements().get(0).elements();
            resMap.setResultCode(list.get(0).attributeValue("pval"));

            resMap.setResultMessage(list.get(1).attributeValue("pval"));

            BookingResult bookingResult = new BookingResult();
            bookingResult.setBookingInfo(list.get(2).attributeValue("pval"));
            bookingResult.setBookingId(list.get(3).attributeValue("pval"));
            bookingResult.setQueueNo(list.get(4).attributeValue("pval"));
            bookingResult.setFloor(list.get(5).attributeValue("pval"));
            bookingResult.setDistrict(list.get(6).attributeValue("pval"));

        } catch (DocumentException e) {
            LogUtil.markLog(2,"BookingServices.java getBookingInfo() "+ e.getMessage());
        }
        return resMap;
    }

    @Override
    public ResultMap<BookingInfo> getBookingInfo(String customerNo) {
        ResultMap<BookingInfo> resMap = new ResultMap<>();
        resMap.setResultCode("0");
        resMap.setResultMessage("获取信息异常，BookingServices.java getBookingInfo() ");
        try {
            String resp = SoapServices.getBookingInfo(customerNo);
            Document document = DocumentHelper.parseText(resp);
            resMap.setResultCode(document.getRootElement().elements().get(0).elements().get(0).attributeValue("pval"));

            resMap.setResultMessage(document.getRootElement().elements().get(0).elements().get(1).attributeValue("pval"));

            if (resMap.getResultCode().equals("1") && document.getRootElement().elements().size() > 1) {
                BookingInfo bookingInfo = new BookingInfo();
                List<Element> list = document.getRootElement().elements().get(1).elements();
                bookingInfo.setSrvGroupID(list.get(0).attributeValue("pval"));
                bookingInfo.setSrvGroupName(list.get(1).attributeValue("pval"));
                bookingInfo.setStaffCode(list.get(2).attributeValue("pval"));
                bookingInfo.setStaffName(list.get(3).attributeValue("pval"));
                bookingInfo.setSrvCodeID(list.get(4).attributeValue("pval"));
                bookingInfo.setSrvCodeName(list.get(5).attributeValue("pval"));
                bookingInfo.setSchedulingDate(list.get(6).attributeValue("pval"));
                bookingInfo.setSchedulingType(list.get(7).attributeValue("pval"));
                bookingInfo.setFloor(list.get(8).attributeValue("pval"));
                bookingInfo.setDistrict(list.get(9).attributeValue("pval"));
                bookingInfo.setBookingTime(list.get(10).attributeValue("pval"));
                bookingInfo.setBookingStatus(list.get(11).attributeValue("pval"));
                bookingInfo.setBookingID(list.get(12).attributeValue("pval"));
                bookingInfo.setYYLY(list.get(13).attributeValue("pval"));
                bookingInfo.setQueueNo(list.get(14).attributeValue("pval"));
                resMap.setParam(bookingInfo);
            }

        } catch (DocumentException e) {
            LogUtil.markLog(2,"BookingServices.java getBookingInfo() "+ e.getMessage());
        }
        return resMap;
    }

}

