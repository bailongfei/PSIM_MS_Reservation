package com.utils;

import com.info.ResultMessage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSUtil {



    public static ResultMessage sendSMS(String tel, String content) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setFlag(false);
        resultMessage.setInformation("短息发送失败，请查看日志");
        String URL_String =  IniUtil.readSMSURL();
        if (URL_String.equals("")){
            return resultMessage;
        }
        System.out.println(URL_String);
        try {
            URL_String = URL_String + "&destCode=" + tel;
            URL_String = URL_String + "&content=" + URLEncoder.encode(content, "UTF-8");
            URL_String = URL_String + "&sequenceId=123456";
            URL_String = URL_String + "&priority=100";
            URL_String = URL_String + "&isEncrypt=0";
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
            resultMessage.setFlag(true);
            resultMessage.setInformation("短息已发送");
        } catch (IOException e) {
            LogUtil.markLog(2, "电话号码:" + tel + "内容：" + content + "  发送失败 " + e.getMessage());
        }
        return resultMessage;
    }

}
