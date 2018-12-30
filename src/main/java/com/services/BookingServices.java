package com.services;

import com.entities.BookingInfo;
import com.entities.BookingResult;
import com.info.ResultMap;
import com.info.UserInfo;

public interface BookingServices {

    /**
     * 获取当日号源信息
     */
    ResultMap getSchedulingToday();
    /**
     * 取消预约
     */
    ResultMap cancelBooking(String bookingId);
    /**
     * 确认预约（现场人工）
     */
    ResultMap<BookingResult> confirmBooking(UserInfo userInfo);
    /**
     * 获取预约信息（按身份证）
     */
    ResultMap<BookingInfo> getBookingInfo(String customerNo);

}
