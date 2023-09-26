package com.llg.usercenter.utils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;

public class test {
    public static void main(String[] args) throws IOException {
//        String str = "987654321987654321";
//        String regex = "\\d{17}[Xx]";//用正则表达式定义手机号规则
//        boolean flag = str.matches(regex);
//        System.out.println(flag);

//        long l = System.currentTimeMillis(); //获取时间戳效率最高
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        String format = dateFormat.format(l);
//        System.out.println(l); //1663989713565
//        System.out.println(format);//2022-09-24
//        String s = null;
//        new BigDecimal(s);

//        String filePath = "E:/Temp/file.txt";
//        String content = "Hello, World!\r\n";
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
//            writer.write(content);
//            System.out.println("内容成功写入文件！");
//        } catch (IOException e) {
//            System.out.println("写入文件时出现错误：" + e.getMessage());
//        }
        for (int i = 0; i < 50; i++) {
            String newestFile = null;
            try {
                newestFile = getNewestFile("E:/Temp/data");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int finalI = i;
            String finalNewestFile = newestFile;
            new Thread(() -> {
                System.out.println(finalNewestFile);
                String content = finalI + "Hello, World! to be or not to be, that's the question!\r\n";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalNewestFile,true))) {
                    writer.write(content);
                    System.out.println("内容成功写入文件！"+finalI);
                } catch (IOException e) {
                    System.out.println("写入文件时出现错误：" + e.getMessage());
                }
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
        if(newest.length()<1024*1024){//文件小于50M直接写入 50*1024*1024
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
