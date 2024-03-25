package com.llg.usercenter.utils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

public class test1 {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 50; i++) {
            int finalI1 = i;
            new Thread(() -> {
                RandomAccessFile randomAccessFile = null;
                FileChannel channel = null;
//        for (int i = 0; i < 50; i++) {

//            new Thread(() -> {
                String newestFile = null;
                try {
                    newestFile = getNewestFile("E:/Temp/data");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                try {
                    randomAccessFile = new RandomAccessFile(newestFile, "rw");
                    channel = randomAccessFile.getChannel();
                    System.out.println(newestFile);
                    String content = finalI1+"test1:Hello, World! to be or not to be, that's the question!\r\n";

                    FileLock lock = null;
                    while (null == lock) {
                        try {
                            lock = channel.lock();
                        } catch (Exception e) {
                            System.out.println("Write Process : get lock failed");
                        }
                    }
                    System.out.println("Write Process : get lock");
                    randomAccessFile.writeChars(content);
                    System.out.println("内容成功写入文件！");
                    Thread.sleep(100);
                    lock.release();
                    System.out.println("Write Process : release lock");
                } catch (Exception e) {
                    System.out.println("写入文件时出现错误：" + e.getMessage());
                } finally {
                    if (null != randomAccessFile) {
                        try {
                            randomAccessFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                    if (null != channel) {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
//            }).start();

//        }

            }).start();
        }
    }

    public static String getNewestFile(String folderPath) throws UnknownHostException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        InetAddress localHost = InetAddress.getLocalHost();
        if(null == files || files.length == 0){
            return folderPath + "/TRAN_" + localHost.getHostName() + "_" + 1 + ".txt";
        }
        //按照创建时间进行排序
        Arrays.sort(files, Comparator.comparing(
                file -> {
                    try {
                        return Files.readAttributes(Paths.get(file.getAbsolutePath()), BasicFileAttributes.class).creationTime().toMillis();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        ));

        File newest = files[files.length - 1];
        System.out.println("文件大小："+newest.length());
        String filePath = newest.getPath();
        if(newest.length()<10*1024){//文件小于50M直接写入 50*1024*1024
            return newest.getPath();
        }else {//文件大小超过50M，创建新文件
            //获取文件后缀数字并加1
            int substring = Integer.parseInt(filePath.substring(filePath.indexOf("_",filePath.indexOf("_")+1) + 1, filePath.indexOf(".txt", 1))) + 1;
            //判断文件数量是否大于10个，大于则删除最早的文件
            if(files.length >=10){
                File oldFile = files[0];
                oldFile.delete();
            }
            return folderPath + "/TRAN_" + localHost.getHostName() + "_" + substring + ".txt";
        }



    }
}
