package com.utils;


import org.ini4j.Ini;
import org.ini4j.Wini;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            LogUtil.markLog(2, "配置文件异常，IniUtil.java readButtonInfo() " + e.getMessage());
        }
        return "0";
    }

    //    身份证COM
    public static String readYiBaoCOM() {
        try {
            File file = new File("AppConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                throw new IOException("配置文件找不到");
            } else {
                Ini.Section section = new Ini(file).get("YiBao");
                return section.get("COM");
            }
        } catch (IOException e) {
            LogUtil.markLog(2, "配置文件异常，IniUtil.java readYiBaoCOM() " + e.getMessage());
        }
        return "COM3";
    }

    //    短息平台地址
    static String readSMSURL() {
        try {
            File file = new File("AppConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                throw new IOException("配置文件找不到");
            } else {
                Ini.Section section = new Ini(file).get("SMSURL");
                return section.get("messageUrl");
            }
        } catch (IOException e) {
            LogUtil.markLog(2, "配置文件异常，IniUtil.java readSMSURL() " + e.getMessage());
        }
        return "";
    }

    //    科室配置
    public static String readSrvGroup() {
        try {
            File file = new File("AppConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                throw new IOException("配置文件找不到");
            } else {
                Ini.Section section = new Ini(file).get("SrvGroup");
                return section.get("ids");
            }
        } catch (IOException e) {
            LogUtil.markLog(2, "配置文件异常，IniUtil.java readSrvGroup() " + e.getMessage());
        }
        return "";
    }

    //    数据库读取
    public static Map<String, String> readJDBCProperties() {
        Map<String, String> map = new HashMap<>();
        try {
            File file = new File("AppConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                throw new IOException("配置文件找不到");
            } else {
                Ini.Section section = new Ini(file).get("DataBase");
                map.put("url", section.get("url"));
                map.put("driver", section.get("driver"));
                map.put("userName", section.get("userName"));
                map.put("password", section.get("password"));
                return map;
            }
        } catch (IOException e) {
            LogUtil.markLog(2, "配置文件异常，IniUtil.java readSrvGroup() " + e.getMessage());
        }
        return null;
    }

    public static void modifyIni(String ids) {
        try {
            File file = new File("AppConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                throw new IOException("配置文件找不到");
            } else {
                Wini ini = null;
                ini = new Wini(file);
                ini.put("SrvGroup","ids",ids);
                ini.store();
            }
        } catch (IOException e) {
            LogUtil.markLog(2, "修改Ini 文件异常,IniUtil.java modifyIni() " + e.getMessage());
        }
    }


    public static String getProfileString(
            String file,
            String section,
            String variable,
            String defaultValue)
            throws IOException {
        String strLine, value = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        boolean isInSection = false;
        try {
            while ((strLine = bufferedReader.readLine()) != null) {
                strLine = strLine.trim();
                //strLine = strLine.split("[;]")[0];
                Pattern p;
                Matcher m;
                p = Pattern.compile("]");
                m = p.matcher((strLine));
                if (m.matches()) {
                    p = Pattern.compile("]");
                    m = p.matcher(strLine);
                    if (m.matches()) {
                        isInSection = true;
                    } else {
                        isInSection = false;
                    }
                }
                if (isInSection == true) {
                    strLine = strLine.trim();
                    String[] strArray = strLine.split("=");
                    if (strArray.length == 1) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = "";
                            return value;
                        }
                    } else if (strArray.length == 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = strArray[1].trim();
                            return value;
                        }
                    } else if (strArray.length > 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = strLine.substring(strLine.indexOf("=") + 1).trim();
                            return value;
                        }
                    }
                }
            }
        } finally {
            bufferedReader.close();
        }
        return defaultValue;
    }

    public static boolean setProfileString(
            String file,
            String section,
            String variable,
            String value)
            throws IOException {
        String fileContent, allLine, strLine, newLine, remarkStr;
        String getValue;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        boolean isInSection = false;
        fileContent = "";
        try {

            while ((allLine = bufferedReader.readLine()) != null) {
                allLine = allLine.trim();
                System.out.println("allLine == " + allLine);
                strLine = allLine;
                Pattern p;
                Matcher m;
                p = Pattern.compile("]");
                m = p.matcher((strLine));
                //System.out.println("+++++++ ");
                if (m.matches()) {
                    System.out.println("+++++++ ");
                    p = Pattern.compile("]");
                    m = p.matcher(strLine);
                    if (m.matches()) {
                        System.out.println("true ");
                        isInSection = true;
                    } else {
                        isInSection = false;
                        System.out.println("+++++++ ");
                    }
                }

                if (isInSection == true) {

                    strLine = strLine.trim();
                    String[] strArray = strLine.split("=");
                    getValue = strArray[0].trim();

                    if (getValue.equalsIgnoreCase(variable)) {
                        // newLine = getValue + " = " + value + " " + remarkStr;

                        newLine = getValue + " = " + value + " ";
                        fileContent += newLine + "\r\n";
                        while ((allLine = bufferedReader.readLine()) != null) {
                            fileContent += allLine + "\r\n";
                        }
                        bufferedReader.close();
                        BufferedWriter bufferedWriter =
                                new BufferedWriter(new FileWriter(file, false));
                        bufferedWriter.write(fileContent);
                        bufferedWriter.flush();
                        bufferedWriter.close();

                        return true;
                    }
                }
                fileContent += allLine + "\r\n";
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            bufferedReader.close();
        }
        return false;
    }

}
