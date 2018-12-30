package com.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class FileLockManager {

    private static FileLock fileLock = null;

    private static File file = null;

    private static RandomAccessFile randomAccessFile = null;

    public FileLockManager(String fileName) {
        file = new File(fileName);
    }

    /**
     * 文件加锁并创建文件
     */
    public boolean Lock() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            randomAccessFile = new RandomAccessFile(file, "rw");
            fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock.isValid()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 解锁并删除文件
     */
    public void unLock() throws IOException {

        if (fileLock != null) {
            fileLock.release();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
        file.delete();
    }

}


