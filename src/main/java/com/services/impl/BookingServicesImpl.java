package com.services.impl;

import com.entities.BookingInfo;
import com.entities.BookingResult;
import com.entities.SchedulingInfo;
import com.info.ResultMap;
import com.info.UserInfo;
import com.services.BookingServices;
import com.utils.LogUtil;
import com.webservices.HSWebServices.SoapServices;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class BookingServicesImpl implements BookingServices {

    @Override
    public ResultMap<List<SchedulingInfo>> getSchedulingToday(String srvGroupID,String districtID,String bookingTypeID) {
        ResultMap<List<SchedulingInfo>> resMap = new ResultMap<>();
        resMap.setResultCode("0");
        resMap.setResultMessage(" ");
        try{
            String resp = SoapServices.getScheduling(srvGroupID,districtID,bookingTypeID);
            Document document = DocumentHelper.parseText(resp);
            resMap.setResultCode(document.getRootElement().elements().get(0).elements().get(0).attributeValue("pval"));

            resMap.setResultMessage(document.getRootElement().elements().get(0).elements().get(1).attributeValue("pval"));

            if (resMap.getResultCode().equals("1")) {
                List<Element> domList = document.getRootElement().elements();
                List<SchedulingInfo> schedulingInfo;
                if (domList.size() > 1) {
                    schedulingInfo = new ArrayList<>();
                    for (int i = 1; i < domList.size() ; i++) {
                        SchedulingInfo scheduling = new SchedulingInfo();
                        scheduling.setSrvGroupID(domList.get(i).elements().get(0).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(1).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(2).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(3).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(4).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(5).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(6).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(7).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(8).attributeValue("pval"));
                        scheduling.setSrvGroupID(domList.get(i).elements().get(9).attributeValue("pval"));
                        schedulingInfo.add(scheduling);
                    }
                    resMap.setParam(schedulingInfo);
                }
            }

        }catch (Exception ex){
            LogUtil.markLog(2, "获取信息异常，BookingServices.java getSchedulingToday() " + ex.getMessage());
        }
        return resMap;
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
            LogUtil.markLog(2, "BookingServices.java cancelBooking() " + e.getMessage());
        }
        return resMap;
    }

    @Override
    public ResultMap<BookingResult> confirmBooking(UserInfo userInfo) {
        ResultMap<BookingResult> resMap = new ResultMap<>();
        resMap.setResultCode("0");
        resMap.setResultMessage("获取信息异常，BookingServices.java confirmBooking() ");
        try {

            String resp = SoapServices.confirmBooking(userInfo.getCustomerID(), userInfo.getCustomerNo(), userInfo.getCustomerNoType(), userInfo.getCustomerName(), userInfo.getCustomerTel(), userInfo.getSchedulingID());
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
            LogUtil.markLog(2, "BookingServices.java getBookingInfo() " + e.getMessage());
        }
        return resMap;
    }

    @Override
    public ResultMap<List<BookingInfo>> getBookingInfo(String customerNo) {
        ResultMap<List<BookingInfo>> resMap = new ResultMap<>();
        resMap.setResultCode("0");
        resMap.setResultMessage("获取信息异常，BookingServices.java getBookingInfo() ");
        try {
            String resp = SoapServices.getBookingInfo(customerNo);
            Document document = DocumentHelper.parseText(resp);
            resMap.setResultCode(document.getRootElement().elements().get(0).elements().get(0).attributeValue("pval"));

            resMap.setResultMessage(document.getRootElement().elements().get(0).elements().get(1).attributeValue("pval"));

            if (resMap.getResultCode().equals("1")) {
                List<Element> domList = document.getRootElement().elements();
                List<BookingInfo> bookingInfos;
                if (domList.size() > 1) {
                    bookingInfos = new ArrayList<>();
                    for (int i = 1; i < domList.size() ; i++) {
                        BookingInfo bookingInfo = new BookingInfo();
                        bookingInfo.setSrvGroupID(domList.get(i).elements().get(0).attributeValue("pval"));
                        bookingInfo.setSrvGroupName(domList.get(i).elements().get(1).attributeValue("pval"));
                        bookingInfo.setStaffCode(domList.get(i).elements().get(2).attributeValue("pval"));
                        bookingInfo.setStaffName(domList.get(i).elements().get(3).attributeValue("pval"));
                        bookingInfo.setSrvCodeID(domList.get(i).elements().get(4).attributeValue("pval"));
                        bookingInfo.setSrvCodeName(domList.get(i).elements().get(5).attributeValue("pval"));
                        bookingInfo.setSchedulingDate(domList.get(i).elements().get(6).attributeValue("pval"));
                        bookingInfo.setSchedulingType(domList.get(i).elements().get(7).attributeValue("pval"));
                        bookingInfo.setFloor(domList.get(i).elements().get(8).attributeValue("pval"));
                        bookingInfo.setDistrict(domList.get(i).elements().get(9).attributeValue("pval"));
                        bookingInfo.setBookingTime(domList.get(i).elements().get(10).attributeValue("pval"));
                        bookingInfo.setBookingStatus(domList.get(i).elements().get(11).attributeValue("pval"));
                        bookingInfo.setBookingID(domList.get(i).elements().get(12).attributeValue("pval"));
                        bookingInfo.setYYLY(domList.get(i).elements().get(13).attributeValue("pval"));
                        bookingInfo.setQueueNo(domList.get(i).elements().get(14).attributeValue("pval"));
                        bookingInfos.add(bookingInfo);
                    }
                    resMap.setParam(bookingInfos);
                }
            }

        } catch (DocumentException e) {
            LogUtil.markLog(2, "BookingServices.java getBookingInfo() " + e.getMessage());
        }
        return resMap;
    }

}

