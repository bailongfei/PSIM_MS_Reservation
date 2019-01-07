package com.utils;

import com.info.ResultMap;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 用于华山北院短信平台发送
 */
public class SMSUtil {

    /**
     * 短信发送
     */
    public static ResultMap sendSMS(String tel, String content) {
        ResultMap resultMessage = new ResultMap();
        resultMessage.setResultCode("0");
        resultMessage.setResultMessage("短息发送失败，请查看日志");
        String URL_String = IniUtil.readSMSURL();
        if (URL_String.equals("")) {
            return resultMessage;
        }
        try {
            URL_String = URL_String + "?userName=admin&password=123456";
            URL_String = URL_String + "&destCode=" + tel;
            URL_String = URL_String + "&content=" + URLEncoder.encode(content, "UTF-8");
            URL_String = URL_String + "&sequenceId=123456";
            URL_String = URL_String + "&priority=100";
            URL_String = URL_String + "&isEncrypt=0";
            System.out.println(URL_String);
            URLConnection urlConnection = new URL(URL_String).openConnection();
            HttpURLConnection huc = (HttpURLConnection) urlConnection;
            huc.setRequestProperty("Accept-Charset", "utf-8");
            huc.setRequestProperty("Content-Type", "application/json");
            huc.setConnectTimeout(3000);
            huc.setReadTimeout(20000);
            huc.setUseCaches(false);
            huc.connect();
            if (huc.getResponseCode() != 200) {
                throw new IOException("" + huc.getResponseCode());
            }
            huc.disconnect();
            resultMessage.setResultCode("1");
            resultMessage.setResultMessage("短息已发送");
        } catch (IOException e) {
            LogUtil.markLog(2, "电话号码:" + tel + " 内容：" + content + "  发送失败 :" + e.getMessage());
        }
        return resultMessage;
    }

    /**
     * 预约短信内容拼写
     */
    public static String buildBookingContent(String customerName, String date, String srvGroupName, String time) {
        String content = customerName + ",您已预约" + date;
        content = content + srvGroupName;
        content = content + "请务必于" + time;
        content = content + "凭证件原件及就诊卡至门诊1楼挂号大厅进行挂号。";
        return content;
    }
    
    /**
     * 取消短信内容拼写
     */
    public static String buildCancelContent(String customerName, String srvGroupName, String time) {
        String content = customerName + ",您预约的";
        content = content + time + srvGroupName + "门诊已取消。";

        return content;
    }


}
