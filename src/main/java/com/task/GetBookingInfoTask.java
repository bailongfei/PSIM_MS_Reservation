package com.task;

import com.entities.BookingInfo;
import com.info.ResultMap;
import com.services.BookingServices;
import com.services.impl.BookingServicesImpl;
import javafx.concurrent.Task;

import java.util.List;

public class GetBookingInfoTask extends Task<ResultMap<List<BookingInfo>>> {

    private String customerNo;
    private BookingServices bookingServices;

    public GetBookingInfoTask(String customerNo) {
        this.customerNo = customerNo;
    }

    @Override
    protected ResultMap<List<BookingInfo>> call() {
        ResultMap<List<BookingInfo>> rs = new ResultMap<>();
        rs.setResultCode("0");
        rs.setResultMessage("获取预约信息，操作失败");
        try {
            this.bookingServices = new BookingServicesImpl();
            rs = bookingServices.getBookingInfo(customerNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
