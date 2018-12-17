package com.utils.cardUtil;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.utils.IniUtil;

class LBIDCardReader {

    public interface YiBao extends Library {
        YiBao INSTANCE = (YiBao) Native.loadLibrary("YiBao", YiBao.class);

        int ICC_Reader_Open(String ComPort);// 打开

        int ICC_Reader_Close(int openHandle);// 关闭

    }

    private static YiBao yiBao = YiBao.INSTANCE;

    static int openReader(){
        System.out.println("打开端口");
        int handle = yiBao.ICC_Reader_Open(IniUtil.readYiBaoCOM());
        System.out.println("打开结束");
        System.out.println(handle);
        return handle;
    }

    static void closeReader(int openHandle){
        System.out.println("关闭端口");
        int rs = yiBao.ICC_Reader_Close(openHandle);
        System.out.println("关闭结束");
        System.out.println(rs);
    }


}
