package com.webservices.HSWebServices;

import com.utils.LogUtil;
import okhttp3.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SoapServices {

    /**
     * 顾总的服务地址
     */
    private static String SERVICE_URL = "http://128.100.200.122:8888/WS_HSBY_BOOKING/services/tranBookingInfo";
    /**
     * 服务地址的命名空间
     */
    private static String SERVICE_NAMESPACE = "http://booking";
    /**
     * 服务actionName
     */
    private static String actionName = "execXML";
    /**
     * 服务paramName
     */
    private static String paramName = "in0";

    /**
     * 拼接soap信息
     */
    private static String soapBuild(String message) {
        String soapAction = "ns:" + actionName;
        String paramAction = "ns:" + paramName;
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        sb.append("<soap:Body>");
        sb.append("<").append(soapAction).append(" xmlns:ns=\"").append(SERVICE_NAMESPACE).append("\" >");
        sb.append("<").append(paramAction).append(">");
        sb.append("<![CDATA[").append(message).append("]]>");
        sb.append("</").append(paramAction).append(">");
        sb.append("</").append(soapAction).append(">");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        return sb.toString();
    }

    /**
     * 发送soap 信息
     */
    private static String callServices(String actionName, String message) {
        HttpURLConnection conn = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        ByteArrayOutputStream bos = null;
        String resp = "";
        try {
            URL wsUrl = new URL(SERVICE_URL);
            conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setRequestProperty("Content-Length", Integer.toString(message.length()));
//            conn.setRequestProperty("SOAPAction", SERVICE_NAMESPACE + "/" + actionName);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(20000);

            outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            outputStreamWriter.write(message);
            outputStreamWriter.flush();

            inputStream = conn.getInputStream();

            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int temp;

            while ((temp = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            String out = bos.toString();
            Document document = null;
            document = DocumentHelper.parseText(out);
            resp = document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
        } catch (IOException e) {
            LogUtil.markLog(2, "发送soap信息异常，SoapServices.java callServices() " + e.getMessage() + "\n" + "soap 消息体为：" + message);
        } catch (DocumentException e) {
            LogUtil.markLog(2, "接口返回转换xml异常，SoapServices.java callServices() " + e.getMessage() + "\n"
                    + "soap 消息体为：" + message + "\n result 消息体为"
                    + bos.toString());
        } finally {
            closeResource(conn, bos, inputStream, outputStreamWriter);
        }
        return resp;
    }


    /**
     *  第三方jar 发送soap 信息
     * @param soapString
     * @return
     */
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

    /**
     * 发送完毕，关闭相对于的资源
     * @param conn
     * @param source
     */
    private static void closeResource(HttpURLConnection conn, AutoCloseable... source) {
        try {
            if (source != null) {
                for (AutoCloseable close : source) {
                    if (close != null) {
                        close.close();
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.markLog(2, "关闭资源异常,SoapServices.java closeResource() " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

    /**
     * 顾总接口文档标注
     */
    public static String getBookingInfo(String customerNo) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("sname", "HSBY.BOOKING.GetBookingInfo");
        map.put("IdentifyID", "DE10B1F1CD0064E98DDCE9BA489725ED");
        map.put("CustomerNo", customerNo);
        map.put("BeginDate", sdf.format(new Date()));
        String req = SoapResultUtil.buildRequestXml(map);
        System.out.println(req);
        String resp = callServices("getBookingInfo", soapBuild(req));
        System.out.println(resp);
        return resp;
    }

    /**
     * 顾总接口文档标注
     */
    public static String cancelBooking(String bookingID) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("sname", "HSBY.BOOKING.GetBookingInfo");
        map.put("IdentifyID", "DE10B1F1CD0064E98DDCE9BA489725ED");
        map.put("BookingID", bookingID);
        String req = SoapResultUtil.buildRequestXml(map);
        System.out.println(req);
        String resp = callServices("cancelBooking", soapBuild(req));
        System.out.println(resp);
        return resp;
    }

    /**
     * 顾总接口文档标注
     */
    public static String confirmBooking(String customerId, String customerNo, String customerNoType, String customerName, String customerTel, String schedulingID) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("sname", "HSBY.BOOKING.XCRG.ConfirmBooking");
        map.put("IdentifyID", "DE10B1F1CD0064E98DDCE9BA489725ED");
        map.put("CustomerID", customerId);
        map.put("CustomerNo", customerNo);
        map.put("CustomerNoType", customerNoType);
        map.put("CustomerName", customerName);
        map.put("CustomerTel", customerTel);
        map.put("SchedulingID", schedulingID);
        map.put("CreateUserID", "");
        map.put("CreateUserUser", "");
        String req = SoapResultUtil.buildRequestXml(map);
        System.out.println(req);
        String resp = callServices("confirmBooking", soapBuild(req));
        System.out.println(resp);
        return resp;
    }

    /**
     * 顾总接口文档标注
     */
    public static String getScheduling(String srvGroupId, String districtId, String bookingTypeId) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("sname", "HSBY.BOOKING.GetScheduling");
        map.put("IdentifyID", "DE10B1F1CD0064E98DDCE9BA489725ED");
        map.put("SrvGroupID", srvGroupId);
        map.put("DistrictID", districtId);
        map.put("BookingTypeID", bookingTypeId);
        String req = SoapResultUtil.buildRequestXml(map);
        System.out.println(req);
        String resp = callServices("confirmBooking", soapBuild(req));
        System.out.println(resp);
        return resp;
    }

    public static void main(String[] args) {
        System.out.println(getBookingInfo("340121199402063781"));
    }

}
