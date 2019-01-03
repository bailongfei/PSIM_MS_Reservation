package com.services;

import com.entities.BookingInfo;
import com.entities.BookingResult;
import com.entities.SchedulingInfo;
import com.info.ResultMap;
import com.info.UserInfo;

import java.util.List;

public interface BookingServices {

    /**
     * 获取当日号源信息
     */
    ResultMap<List<SchedulingInfo>> getSchedulingToday(String srvGroupID,String districtID,String bookingTypeID);
    /**
     * 取消预约
     *
     */
    ResultMap cancelBooking(String bookingId);
    /**
     * 确认预约（现场人工）
     */
    ResultMap<BookingResult> confirmBooking(UserInfo userInfo);
    /**
     * 获取预约信息（按身份证）
     */
    ResultMap<List<BookingInfo>> getBookingInfo(String customerNo);

}
