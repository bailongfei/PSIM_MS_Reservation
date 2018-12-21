package com.utils;

public class SoapUtil {

    public static String ASCIIEncode(String string) {
        StringBuilder encode = new StringBuilder();
        char[] stringArr = string.toCharArray();
        for (char c:stringArr){
            int ascii = (int) c +100;
            encode.append((char)ascii);
        }
        System.out.println(encode.toString());
        return encode.toString();
    }

    public static String ASCIIDecode(String string) {
        StringBuilder decode = new StringBuilder();
        char[] stringArr = string.toCharArray();
        for (char c:stringArr){
            int ascii = (int) c -100;
            decode.append((char)ascii);
        }
        return decode.toString();
    }

    public static String encodeGTLT(String string){
        string = string.replace("<","&lt;").replace(">","&gt;");
        return string;
    }

    public static String decodeGTLT(String string){
        string = string.replace("&lt;","<").replace("&gt;",">");
        return string;
    }

}
