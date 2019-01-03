package com.task;

import com.entities.SchedulingInfo;
import com.info.ResultMap;
import com.services.impl.BookingServicesImpl;
import javafx.concurrent.Task;

import java.util.List;

public class GetSchedulingTodayTask extends Task<ResultMap<List<SchedulingInfo>>> {

    private String srvGroupID;
    private String districtID = "";
    private String bookingTypeID;

    public GetSchedulingTodayTask(String srvGroupID,String districtID,String bookingTypeID){
        this.srvGroupID = srvGroupID;
        this.districtID = districtID;
        this.bookingTypeID = bookingTypeID;
    }

    @Override
    protected ResultMap<List<SchedulingInfo>> call() {
        ResultMap<List<SchedulingInfo>> rs = new ResultMap<>();
        rs.setResultCode("0");
        rs.setResultMessage("获取当日号源信息异常");
        try{
            rs = new BookingServicesImpl().getSchedulingToday(srvGroupID,districtID,bookingTypeID);
        }catch (Exception ex){
            System.out.println("GetSchedulingTodayTask "+ex.getMessage());
        }

        return rs;
    }
}
