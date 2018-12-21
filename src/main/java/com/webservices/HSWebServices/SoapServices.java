package com.webservices.HSWebServices;

import com.utils.LogUtil;
import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SoapServices {

    /**
     * 顾总的服务地址
     */
    private static String SERVICE_URL = "http://127.0.0.1:8080/CXF/executeXml";
    /**
     * 服务地址的命名空间。
     */
    private static String SERVICE_NAMESPACE = "http://services.shxh/SHXHService";

    //    拼接soap
    public static String soapBuild(String actionName, String paramName, String message) {
        String soapAction = "ns:" + actionName;
        String paramAction = "ns:" + paramName;
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        sb.append("<soap:Body>");
        sb.append("<").append(soapAction).append(" xmlns:ns=\"").append(SERVICE_NAMESPACE).append("\" >");
        sb.append("<").append(paramAction).append(">");
        sb.append(message);
        sb.append("</").append(paramAction).append(">");
        sb.append("</").append(soapAction).append(">");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        return sb.toString();
    }

    private static void callServices(String actionName, String paramName, String message) {
        HttpURLConnection conn = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        ByteArrayOutputStream bos = null;
        String soapString = soapBuild(actionName, paramName, message);
        String resp = "";
        try {
            URL wsUrl = new URL(SERVICE_URL);
            conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setRequestProperty("Content-Length", Integer.toString(soapString.length()));
            conn.setRequestProperty("SOAPAction", SERVICE_NAMESPACE + "/" + actionName);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(20000);

            outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            outputStreamWriter.write(soapString);
            outputStreamWriter.flush();

            inputStream = conn.getInputStream();
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int temp;

            while ((temp = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            System.out.println(bos.toString());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeResource(conn,bos,inputStream,outputStreamWriter);
        }

    }



    //    发送soap信息
    public static String callServices(String soapString) {
        String resp = "";
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/xml");
            RequestBody body = RequestBody.create(mediaType, soapString);
            Request request = new Request.Builder()
                    .url(SERVICE_URL)
                    .post(body)
                    .addHeader("Content-Type", "text/xml")
                    .addHeader("cache-control", "no-cache")
                    .build();
            Response response = client.newCall(request).execute();
            resp = new String(response.body().bytes());
        } catch (IOException e) {
            LogUtil.markLog(2, "Soap信息发送异常，SoapServices.java callServices() " + e.getMessage());
        }
        return resp;
    }

    // 关闭资源
    private static void closeResource(HttpURLConnection conn,AutoCloseable... source) {
        try {
            if (source != null) {
                for (AutoCloseable close : source) {
                    if (close != null) {
                        close.close();
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.markLog(2,"关闭资源异常,SoapServices.java closeResource() "+e.getMessage());
        } finally {
            if (conn!=null){
                conn.disconnect();
            }
        }
    }

}
