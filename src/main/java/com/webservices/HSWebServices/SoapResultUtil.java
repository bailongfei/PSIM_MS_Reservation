package com.webservices.HSWebServices;

import java.util.List;
import java.util.Map;

public class SoapResultUtil {


    /**
     * 创建请求XML
     * @param params
     * @return
     */
    static String buildRequestXml(Map<String, String> params) {
        if (params == null || params.get("sname") == null || params.get("sname").length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        // gbk头
        sb.append("<?xml version=\"1.0\" encoding=\"gbk\"?>");
        // 服务名
        sb.append("<SERVICES sname=\"").append(params.get("sname")).append("\">");
        sb.append("<PARAMS>");

        // 所有请求参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!entry.getKey().equals("sname")) {
                sb.append("<PARAM pname=\"").append(entry.getKey()).append("\" pval=\"")
                        .append(entry.getValue() == null ? "" : convert(entry.getValue())).append("\" />");
            }
        }

        sb.append("</PARAMS>");
        sb.append("</SERVICES>");

        return sb.toString();
    }

    /**
     * 创建响应XML
     * @param params
     * @return
     */
    public static String buildResponseXml(List<Map<String, String>> params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        // 判断第一个元素是否包含结果信息
        Map<String, String> resultMap = params.get(0);
        if (resultMap == null || resultMap.get("sname") == null || resultMap.get("sname").trim().length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        // gbk头
        sb.append("<?xml version=\"1.0\" encoding=\"gbk\"?>");
        // 服务名
        sb.append("<").append(resultMap.get("sname")).append(">");

        // 响应信息
        Map<String, String> paramMap = null;
        for (int i = 0; i < params.size(); i++) {
            paramMap = params.get(i);
            // 拼接节点标识信息
            sb.append("<PARAMS ptype=\"").append(paramMap.get("ptype")).append("\" >");

            // 拼接节点字段信息
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                if ((!entry.getKey().equals("ptype")) && (!entry.getKey().equals("sname"))) {
                    sb.append("<PARAM pname=\"").append(entry.getKey()).append("\" pval=\"")
                            .append(entry.getValue() == null ? "" : convert(entry.getValue())).append("\" />");
                }
            }

            sb.append("</PARAMS>");
        }

        sb.append("</").append(resultMap.get("sname")).append(">");

        return sb.toString();
    }

    /**
     * 替换XML拼接字符
     * @param str
     * @return
     */
    private static String convert(String str) {
        return str == null ? null
                : str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;")
                .replace("\"", "&quot;").replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
    }

    /**
     * 替换XML转义字符
     * @param str
     * @return
     */
    public static String convertBack(String str) {
        return str == null ? null :
                str
                        .replace("&amp;", "&")
                        .replace( "&lt;","<")
                        .replace("&gt;",">")
                        .replace( "&apos;","'")
                        .replace("&quot;","\"");
    }


}
