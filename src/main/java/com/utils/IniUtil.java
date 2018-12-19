package com.utils;


import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class IniUtil {

//    按钮状态
    public static String readButtonInfo(String buttonName) {
        try {
            File file = new File("AppConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                throw new IOException("配置文件找不到");
            } else {
                Ini.Section section = new Ini(file).get("ButtonStatus");
                return section.get(buttonName);
            }
        } catch (IOException e) {
            LogUtil.markLog(2,"配置文件异常，IniUtil.java readButtonInfo() "+e.getMessage());
        }
        return "0";
    }

//    身份证COM
    public static String readYiBaoCOM(){
        try {
            File file = new File("AppConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                throw new IOException("配置文件找不到");
            } else {
                Ini.Section section = new Ini(file).get("YiBao");
                return section.get("COM");
            }
        } catch (IOException e) {
            LogUtil.markLog(2,"配置文件异常，IniUtil.java readYiBaoCOM() "+e.getMessage());
        }
        return "COM3";
    }
//    短息平台地址
    static String readSMSURL(){
        try {
            File file = new File("AppConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                throw new IOException("配置文件找不到");
            } else {
                Ini.Section section = new Ini(file).get("SMSURL");
                return section.get("url");
            }
        } catch (IOException e) {
            LogUtil.markLog(2,"配置文件异常，IniUtil.java readSMSURL() "+e.getMessage());
        }
        return "";
    }

}
