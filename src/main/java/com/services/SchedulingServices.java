package com.services;

import com.entities.SrvGroup;
import com.utils.IniUtil;
import com.utils.LogUtil;
import com.utils.dbUtils.ConnectionPool;
import com.utils.dbUtils.ConnectionPoolUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchedulingServices {

    private String[] readSrvGroupIds() {
        String iniString = IniUtil.readSrvGroup();
        String ids[] = iniString.split(",");
        return ids;
    }

    //    根据id查找对应的srvGroup
    private List<SrvGroup> getSrvGroupByIds(String ids) {
        List<SrvGroup> list = new ArrayList<>();
        ConnectionPool connPool = ConnectionPoolUtils.getPoolInstance();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final String sql = "SELECT * FROM BASIC_SRVGROUP WHERE 1=1 and SRVGROUP_ID in("+ids+")";
        try {
            conn = connPool.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                SrvGroup srvGroup = new SrvGroup();
                srvGroup.setSrvGroupCode(rs.getString("SRVGROUP_CODE"));
                srvGroup.setSrvGroupId(rs.getString("SRVGROUP_ID"));
                srvGroup.setSrvGroupLetter(rs.getString("SRVGROUP_LETTER"));
                srvGroup.setSrvGroupName(rs.getString("SRVGROUP_NAME"));
                list.add(srvGroup);
            }
            if (list.size()<1){
                return null;
            }
        } catch (SQLException e) {
            LogUtil.markLog(2, "数据库异常，SchedulingServices getSrvGroupByIds() " + e.getMessage());
        } finally {
            connPool.returnConnection(conn);
            connPool.closeSTMTRS(ps,rs);
        }
        return list;
    }

}
