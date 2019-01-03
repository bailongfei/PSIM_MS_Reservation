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

    public ConfirmBookingTask(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    protected ResultMap<BookingResult> call() {
        ResultMap<BookingResult> rs = new ResultMap<>();
        rs.setResultCode("0");
        rs.setResultMessage("操作失败");
        try {
            bookingServices = new BookingServicesImpl();
            rs = bookingServices.confirmBooking(userInfo);
        } catch (Exception e) {
            LogUtil.markLog(2, "预约失败" + e.getMessage());

        }
        return rs;
    }
}
