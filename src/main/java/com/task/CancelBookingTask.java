package com.task;

import com.info.ResultMap;
import com.services.impl.BookingServicesImpl;
import javafx.concurrent.Task;

public class CancelBookingTask extends Task<ResultMap>{

    private String bookingID;

    public CancelBookingTask(String bookingID){
        this.bookingID = bookingID;
    }

    @Override
    protected ResultMap call() {
        ResultMap rs = new ResultMap();
        rs.setResultCode("0");
        rs.setResultMessage("操作失败");
        try{
            rs =  new BookingServicesImpl().cancelBooking(bookingID);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return rs;
    }
}
