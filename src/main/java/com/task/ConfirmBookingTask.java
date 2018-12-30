package com.task;

import com.entities.BookingResult;
import com.info.ResultMap;
import com.info.UserInfo;
import com.services.BookingServices;
import com.services.impl.BookingServicesImpl;
import com.utils.LogUtil;
import javafx.concurrent.Task;

public class ConfirmBookingTask extends Task<ResultMap<BookingResult>> {

    private UserInfo userInfo;
    private BookingServices bookingServices;
    public ConfirmBookingTask(UserInfo userInfo){
        this.userInfo = userInfo;
    }

    @Override
    protected ResultMap<BookingResult> call(){
        try{
            bookingServices = new BookingServicesImpl();
            return bookingServices.confirmBooking(userInfo);
        }catch (Exception e){
            LogUtil.markLog(2,"预约失败"+e.getMessage());
            return null;
        }
    }
}
