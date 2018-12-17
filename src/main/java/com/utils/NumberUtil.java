package com.utils;

import com.info.ResultMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号
 */
public class NumberUtil {

    private static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    private static final String REGEX_ID_18 ="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    private static final String REGEX_ID_15 ="^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";

    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    public static ResultMessage isPhone(String phone) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setFlag(false);
        if (phone.length() != 11) {
//            不满11位
            resultMessage.setInformation("手机号应为11位数");
            return resultMessage;
        } else {
            Pattern p = Pattern.compile(REGEX_MOBILE);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
//                不符合正则表达式
                resultMessage.setInformation("请填入正确的手机号");
            }
            resultMessage.setFlag(isMatch);
            return resultMessage;
        }
    }

    public static ResultMessage isID(String id){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setInformation("身份证号应该为15位或18位");
        resultMessage.setFlag(false);
        if (id.length()==15) {
//            15位
            Pattern p = Pattern.compile(REGEX_ID_15);
            Matcher m = p.matcher(id);
            boolean isMatch = m.matches();
            if (!isMatch) {
//               不符合15位正则表达式
                resultMessage.setInformation("不符合15位身份证");
            } else{
                resultMessage.setInformation("");
            }
            resultMessage.setFlag(isMatch);
        }
        if (id.length()==18){
//            18位
            Pattern p = Pattern.compile(REGEX_ID_18);
            Matcher m = p.matcher(id);
            boolean isMatch = m.matches();
            if (isMatch) {
//               不符合18位正则表达式
                resultMessage.setInformation("");
                resultMessage.setFlag(true);
            } else{
                resultMessage.setFlag(false);
                resultMessage.setInformation("不符合18位身份证");
            }
        }
        return resultMessage;
    }

}
