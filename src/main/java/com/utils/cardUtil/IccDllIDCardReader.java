package com.utils.cardUtil;

import com.sun.jna.Library;
import com.sun.jna.Native;


class IccDllIDCardReader {

    public interface IccDll extends Library {
        IccDll INSTANCE = (IccDll) Native.loadLibrary("ICCDLL", IccDll.class);

        int ReadIdcInfo(int handle, IDCardResult.ByReference info);
    }

    private static IccDll iccDll = IccDll.INSTANCE;

    static IDCardResult.ByReference readIDCard(int handle){
        IDCardResult.ByReference info = new IDCardResult.ByReference();
        int rs = iccDll.ReadIdcInfo(handle,info);
        System.out.println(rs);
        if (rs<0){
            return null;
        }
        return info;
    }

}
