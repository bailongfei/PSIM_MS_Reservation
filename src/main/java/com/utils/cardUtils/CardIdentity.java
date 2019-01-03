package com.utils.cardUtils;

import com.info.ResultMap;
import com.info.UserInfo;
import com.utils.LogUtil;
import com.webservices.TranBookingInfo;
import com.webservices.TranBookingInfoPortType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class CardIdentity {

    /**
     * 社保卡读卡
     *
     * @return
     */
    public static ResultMap<UserInfo> getUserInfoBySSCard() {
        ResultMap<UserInfo> resultMessage = new ResultMap<>();
        UserInfo userInfo;
        resultMessage.setParam(null);
        System.out.println("s000");
        userInfo = DLLUtil.getId();
        if (userInfo != null) {
            resultMessage.setResultCode("1");
            userInfo.setCustomerNoType("1");
            resultMessage.setParam(userInfo);
        } else {
            resultMessage.setResultCode("0");
            resultMessage.setResultMessage("无法读取芯片卡信息，请尝试重新读取");
        }
        return resultMessage;

    }

    public static ResultMap<UserInfo> getUserInfoByCardString(String cardString) {
        ResultMap<UserInfo> resultMessage = new ResultMap<>();
        resultMessage.setResultCode("0");
        UserInfo userInfo;
        resultMessage.setParam(null);
        // 判断输入框的字符串长度
        if (cardString.length() == 15) {
            System.out.println("自费走his");
            String result = getInformationFromHis(cardString);
            // 拆分result的值
            String pname[] = {"Result", "ResultInfo", "CustomerName", "CustomerNo", "CustomerTel", "CustomerMobile"};
            String[][] resultArray = getData(result, pname, 0);
            System.out.println(resultArray.length);
            if (resultArray.length > 1) {
                if (resultArray[0][0].equals("1")) {
                    userInfo = new UserInfo();
                    userInfo.setCustomerID(cardString);
                    userInfo.setCustomerNo(resultArray[1][3]);
                    userInfo.setCustomerName(resultArray[1][2]);
                    userInfo.setCustomerNoType("1");
                    resultMessage.setResultMessage(resultArray[0][1]);
                    resultMessage.setParam(userInfo);
                    resultMessage.setResultCode("1");
                } else {
                    resultMessage.setResultMessage(resultArray[0][1]);
                }
            } else {
                resultMessage.setResultMessage("无法读取磁条卡信息，请尝试重新读取");
            }
        } else {
            System.out.println("白玉兰卡走his");
            String result = getInformationFromHis(cardString);
            // 拆分result的值
            String pname[] = {"Result", "ResultInfo", "CustomerName", "CustomerNo", "CustomerTel", "CustomerMobile"};
            String[][] resultArray = getData(result, pname, 0);
            if (resultArray[0][0].equals("1")) {
                // 判断是否有信息
                if (resultArray.length > 1) {
                    if (resultArray[1][2] != null && !resultArray[1][2].equals("") && resultArray[1][2].length() > 1) {
                        // his有信息
                        userInfo = new UserInfo();
                        userInfo.setCustomerID(cardString);
                        userInfo.setCustomerName(resultArray[1][2]);
                        userInfo.setCustomerNo(resultArray[1][3]);
                        userInfo.setCustomerNoType("1");
                        resultMessage.setResultMessage(resultArray[0][1]);
                        resultMessage.setParam(userInfo);
                        resultMessage.setResultCode("1");
                    } else {
                        resultMessage.setResultMessage(resultArray[0][1]);

                    }
                } else {
                    resultMessage.setResultMessage("无法读取磁条卡信息，请尝试重新读取");
                }
            }
//              else {
//                resultMessage.setFlag(false);
//                resultMessage.setInformation(resultArray[0][1]);
//            }
        }
        return resultMessage;
    }

    /**
     * 良标身份证端口
     *
     * @return
     */
    public static ResultMap<UserInfo> getUserInfoByCustomerCard() {
        System.out.println("走身份证");
        ResultMap<UserInfo> resultMessage = new ResultMap<>();
        try {
            UserInfo userInfo = new UserInfo();
            int handle = LBIDCardReader.openReader();
            IDCardResult.ByReference info = IccDllIDCardReader.readIDCard(handle);
            LBIDCardReader.closeReader(handle);
            if (info != null) {
                String id = "";
                String SFZ = new String(info.pid).trim();
                String name = new String(info.name).trim();
                userInfo.setCustomerNo(SFZ);
                userInfo.setCustomerID(id);
                userInfo.setCustomerName(name);
                userInfo.setCustomerNoType("1");
            }
            resultMessage.setParam(userInfo);
        } catch (Exception e) {
            LogUtil.markLog(2, "身份证读卡异常" + e.getMessage());
        }
        return resultMessage;
    }

    /**
     * 执行接口返回数据
     */
    private static String getInformationFromHis(String customerId) {
        TranBookingInfoPortType tranBookingInfoPortType = new TranBookingInfo().getTranBookingInfoHttpPort();
        String str = "<?xml version='1.0' encoding='gbk'?>"
                + "<SERVICES sname='HSBY.BOOKING.GetCustomerInfo'>"
                + "<PARAMS><PARAM pname='IdentifyID' pval='6AA36B715F8CEC7D8B79FFB6D16C4DD4' />"
                + "<PARAM pname='CustomerID' pval='" + customerId + "' />"
                + "</PARAMS></SERVICES>";
        System.out.println(str);
        String backInfo = tranBookingInfoPortType.execXML(str);
        System.out.println(backInfo);
        return backInfo;
    }

    /**
     * 解析XML
     * @return
     */
    private static String[][] getData(String execXML, String[] Pname, int location) {
        // 获得xml文件内容
        StringReader sr = new StringReader(execXML);
        InputSource is = new InputSource(sr);
        // 返回2元数组
        String[][] pval = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            Element rootElement = doc.getDocumentElement();
            NodeList list = rootElement.getChildNodes();
            // 修改pname的值即可针对不同的xml
            pval = new String[list.getLength()][Pname.length];
            for (int i = location; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                NodeList DistrictList = element.getElementsByTagName("PARAM");
                for (int j = 0; j < DistrictList.getLength(); j++) {
                    Element element1 = (Element) DistrictList.item(j);
                    String pname = element1.getAttribute("pname");
                    for (int k = 0; k < Pname.length; k++) {
                        if (pname.equals(Pname[k])) {
                            pval[i][k] = element1.getAttribute("pval");
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LogUtil.markLog(2, "获取病人基本信息，解析返回参数异常 CardIdentity.java getData() " + e.getMessage());
        }
        return pval;
    }


}
