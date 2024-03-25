package com.llg.usercenter.utils;

import org.springframework.util.StreamUtils;

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
        String json = "{\"CUST_TYPE\":\"0\",\"CUST_PAPERTYPE\":\"1010\",\"CUST_NAME\":\"徐立军\",\"CUST_PAPERNO\":\"15212319770408211X\",\"LINK_MAN_PHONE\":\"13789608186\",\"LINK_MAN\":\"徐立军\",\"MAIL_ADDRESS\":\"江苏省苏州相城区欧风丽苑29幢1705室\",\"BUS_ADDRESS\":\"江苏省苏州相城区欧风丽苑29幢1705室\",\"REG_ADDRESS\":\"内蒙古莫力达瓦达斡尔族自治旗奎勒河镇富强村\",\"REG_DATE\":\"1977-04-08\"}\n";
        File file = new File("E:/Temp/yuanshuju222/dddd/ssss");
        boolean mkdir = file.mkdirs();
        System.out.println(mkdir);
                String filePath = "E:/Temp/file2.txt";
                String filePath1 = "E:/Temp/yuanshuju.txt";


        StreamUtils.copy(new FileInputStream(filePath1),new FileOutputStream(filePath));
//        String filePath1 = "E:/Temp/yuanshuju.txt";
//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath1),"UTF-8"));
//
//        String filePath = "E:/Temp/file.txt";
//        String content = "Hello, World!\r\n";
//        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8"))) {
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                line = line.substring(0,line.indexOf(","));
//                writer.write(line+"\r\n");
//                writer.flush();
//            }
//
////            writer.write(content);
//            System.out.println("内容成功写入文件！");
//        } catch (IOException e) {
//            System.out.println("写入文件时出现错误：" + e.getMessage());
//        }
//        for (int i = 0; i < 50; i++) {
//            String newestFile = null;
//            try {
//                newestFile = getNewestFile("E:/Temp/data");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            int finalI = i;
//            String finalNewestFile = newestFile;
//            new Thread(() -> {
//
//                System.out.println(finalNewestFile);
//                String content = finalI + ":Hello, World! to be or not to be, that's the question!\r\n";
//                try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalNewestFile,true))) {
//                    Thread.sleep(1000);
//                    writer.write(content);
//                    System.out.println("内容成功写入文件！"+finalI);
//                } catch (Exception e) {
//                    System.out.println("写入文件时出现错误：" + e.getMessage());
//                }
//            }).start();

//        }


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
