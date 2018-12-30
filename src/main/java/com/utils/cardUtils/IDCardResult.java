package com.utils.cardUtils;


import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * //身份证信息
 * typedef struct IdcInfo {
 * 	char name[30+1]; //
 * 	char sex[1+1];	//'1'男 '2'女
 * 	char nation[2+1];	//民族 '01'-'57'
 * 	char birth[8+1];	//YYYYMMDD
 * 	char addr[70+1];
 * 	char pid[18+1];
 * 	char issue[30+1];	//发卡机关
 * 	char valid_start[8+1]; //
 * 	char valid_end[8+1];   //
 * } TIdcInfo;
 */
public class IDCardResult extends Structure {

    public byte[] name = new byte[31];
    public byte[] sex = new byte[2];
    public byte[] nation = new byte[3];
    public byte[] birth = new byte[9];
    public byte[] addr = new byte[71];
    public byte[] pid = new byte[19];
    public byte[] issue = new byte[31];
    public byte[] valid_start = new byte[9];
    public byte[] valid_end = new byte[9];

    public static class ByReference extends IDCardResult implements Structure.ByReference {
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("name", "sex", "nation", "birth", "addr", "pid",
                "issue", "valid_start", "valid_end");
    }

}
