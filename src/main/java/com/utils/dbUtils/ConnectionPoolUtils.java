package com.utils.dbUtils;

import com.utils.IniUtil;

import java.util.Map;

public class ConnectionPoolUtils {

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    static {
        Map<String,String> dbMap = IniUtil.readJDBCProperties();
        if (dbMap!=null){
            driver = dbMap.get("driver");
            System.out.println(driver);
            url = dbMap.get("url");
            System.out.println(url);
            user = dbMap.get("userName");
            System.out.println(user);
            password = dbMap.get("password");
            System.out.println(password);
        }
    }

    private ConnectionPoolUtils(){

    }

    private static ConnectionPool poolInstance = null;

    public static ConnectionPool getPoolInstance(){
        if(poolInstance == null) {
            poolInstance = new ConnectionPool(driver, url, user, password);
            try {
                poolInstance.createPool();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return poolInstance;
    }

}
