package com.utils;

import com.info.ResultMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号
 */
public class NumberUtil {

    private static final String REGEX_MOBILE = "^1\\d{10}$";

    private static final String REGEX_ID_18 ="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    private static final String REGEX_ID_15 ="^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";

    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 手机号码验证
     */
    public static ResultMap isPhone(String phone) {
        ResultMap resultMap = new ResultMap();
        resultMap.setResultCode("0");
        if (phone.length() != 11) {
//            不满11位
            resultMap.setResultMessage("手机号应为11位数");
            return resultMap;
        } else {
            Pattern p = Pattern.compile(REGEX_MOBILE);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
//                不符合正则表达式
                resultMap.setResultMessage("请填入正确的手机号");
            } else {
                resultMap.setResultCode("1");
            }
            return resultMap;
        }
    }

    /**
     * 身份证号码验证
     */
    public static ResultMap isID(String id){
        ResultMap resultMap = new ResultMap();
        resultMap.setResultMessage("身份证号应该为15位或18位");
        resultMap.setResultCode("0");
        if (id.length()==15) {
//            15位
            Pattern p = Pattern.compile(REGEX_ID_15);
            Matcher m = p.matcher(id);
            boolean isMatch = m.matches();
            if (!isMatch) {
//               不符合15位正则表达式
                resultMap.setResultMessage("不符合15位身份证");
            } else{
                resultMap.setResultMessage("");
                resultMap.setResultCode("1");
            }

        }
        if (id.length()==18){
//            18位
            Pattern p = Pattern.compile(REGEX_ID_18);
            Matcher m = p.matcher(id);
            boolean isMatch = m.matches();
            if (isMatch) {
//               不符合18位正则表达式
                resultMap.setResultMessage("");
                resultMap.setResultCode("1");
            } else{
                resultMap.setResultMessage("不符合18位身份证");
            }
        }
        return resultMap;
    }

}
