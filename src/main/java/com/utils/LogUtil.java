package com.utils;

import com.info.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 日志记录
 */
public class LogUtil {

    public static void markLog(Integer status,String message){
        Log log = new Log(status,message);
        String dirName;
        String fileName;
        if (status.equals(2)){
            dirName = "ErrorLog";
        } else {
            dirName = "Log";
        }
        fileName = dirName +"/"+ new SimpleDateFormat("yyyy-MM-dd").format(log.getLogDate())+"Log.txt";
        writeLog(dirName,fileName,log);
    }

    private static void logWrite(File file,Log log){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            writer.write("*****************************");
            writer.write(log.toString());
            writer.write("\r\n*****************************");
            writer.write("\r\n");
            writer.close();
        }catch(Exception ignore){

        }
    }

    private static void writeLog(String dirName ,String fileName,Log log){
        File file = new File(fileName);
        if (file.exists()){
            logWrite(file,log);
        } else {
            try {
                File dir = new File(dirName);
                if (dir.exists()&&dir.isDirectory()){
                    file.createNewFile();
                } else {
                    dir.mkdir();
                    file.createNewFile();
                }
                logWrite(file,log);
            } catch (IOException ignore) {

            }
        }
    }

}
