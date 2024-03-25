package com.llg.usercenter.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.google.gson.JsonArray;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.logging.Logger;

public class test2 {

  @Value("server.port")
  private  String port ;
    public static void main(String[] args) throws Exception {
      List<String> toAddList = new ArrayList<>();
      toAddList.add("北京林业大学");
      System.out.println(toAddList);
      System.out.println(JSON.toJSONString(toAddList));
      addPublicSentEnterprise(toAddList);
//      delPublicSentEnterprise(toAddList);

//      String str = "[{\"eid\":\"3db61b09-e177-4494-8d47-b6f5db838a72\",\"name\":\"国家开发银行\",\"type\":\"company\"},{\"eid\":\"763f97f7-0da2-4e45-aaf9-095bb04b726a\",\"name\":\"国家开发银行吉林省分行\",\"type\":\"company\"},{\"eid\":\"cc03e95e-fd27-11e9-9025-00163e104bc8\",\"name\":\"吉林省人民检察院\",\"type\":\"org\"}]]";
//      JSONArray jsonArray = JSONArray.parseArray(str);
//
//      System.out.println(str.substring(1,str.length()-1));
//      System.out.println(StringUtils.isEmpty("qq"));
//
//      Integer i  = 1;
//      Integer i1  = 1;
//      System.out.println(i==i1);
//      Integer j  = -129;
//      Integer j1  = -129;
//      System.out.println(j==j1);

//      Map<String , Object> map = new HashMap<>();
//      map.put("a",1);
//      map.put("b",2);
//      map.put("c",3);
//      map.put("d",4);
//      map.put("e",5);
//      String s2 = map.toString();
//      System.out.println(s2);
//
//      List<User> list = new ArrayList<>();
//      list.forEach(t-> System.out.println(t.getId()));
//      User user = new User();
//      user.setUsername("123");
//      System.out.println(user);
//      list.add(user);
//      System.out.println(list);
//
//      Random random = new Random();
//      SecureRandom random1 = new SecureRandom();
//      System.out.println("random:"+random.nextInt(10));
//      System.out.println("random1:"+random1.nextInt(10));
//      System.out.println("random1:"+random1.nextDouble());
//      int round = (int)Math.round(Math.random() * 2);
//      System.out.println(Math.random());
//      System.out.println(round);

//      System.out.println(encryptSHA("123456".getBytes()));
//      System.out.println(SHAEncrypt("123456"));
//
//      String s = "'1=1' 2 456";
//      String s1 = s.replaceAll("'", "");
//      System.out.println(s1);
//      List<String> strings = Collections.singletonList(s1);
//      System.out.println("s1:"+strings);


//      byte[] bytes  = "123456".getBytes();
//      System.out.println("Base64加密前："+Arrays.toString(bytes));
//      byte[] encodeBytes = Base64.getEncoder().encode(bytes);
//      System.out.println("Base64加密后："+Arrays.toString(encodeBytes));
//      byte[] decode = Base64.getDecoder().decode(encodeBytes);
//      System.out.println("Base64解密后："+Arrays.toString(decode));
//      String dec = new String(decode);
//      System.out.println(dec);
//      System.out.println(SHA256Encrypt("123456"));
//      String salt = UUID.randomUUID().toString();
//      String newPassword = DigestUtils.md5DigestAsHex((salt+password).getBytes(StandardCharsets.UTF_8));
//      System.out.println(newPassword);
//      StringBuilder stringBuilder = DigestUtils.appendMd5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8), new StringBuilder(salt));
//      System.out.println(stringBuilder);
//      String fileName = "test_file.jpg";
//      System.out.println(fileName);
//      if(Pattern.matches("^[^-\\:*?\"<>|/]+",fileName)){
//          System.out.println(true);
//      }

//      String port = PropertiesUtil.getProperties().getStringProperty("server.port");

//      fun();
    }

    public static void addPublicSentEnterprise (List<String> toAddList){
      publicSentEnterprise(toAddList,"add");
    }
    public static void delPublicSentEnterprise (List<String> toAddList){
      publicSentEnterprise(toAddList,"del");
    }

  private static void publicSentEnterprise(List<String> toAddList,String operate) {
    try {
      System.out.println("开始调用");
      HttpPost httpPost = new HttpPost("https://api.qixin.com/qipush/mgr/company/"+operate+"?set_id=40" +
              "&appkey=019a4c7f-ad6c-4ab0-a566-b044a71b5040&secret_key=e67d62d6-d404-4074-8315-0744b2238ae6");
      System.out.println(JSON.toJSONString(toAddList));
      StringEntity entity = new StringEntity(JSON.toJSONString(toAddList),"utf-8");
      entity.setContentEncoding("UTF-8");
      entity.setContentType("application/json");
      httpPost.setEntity(entity);
      httpPost.addHeader("Content-Type","application/json;charset=utf-8");

      CloseableHttpClient aDefault = HttpClients.createDefault();

      CloseableHttpResponse execute = aDefault.execute(httpPost);
      HttpEntity httpEntity = execute.getEntity();
      String s = EntityUtils.toString(httpEntity, "utf-8");
      System.out.println("接口返回json："+s);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static byte[] encryMD5(byte[] data) throws Exception {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(data);
      return md5.digest();
    }
    public static void fun() throws IOException {
//      InputStream resourceAsStream = this.getClass().getResourceAsStream("application.properties");
      InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream("application.properties");
      Properties properties = new Properties();
      properties.load(resourceAsStream);
      properties.list(System.out);
      String port = properties.getProperty("server.port");
      System.out.println(port);

/**
 * (a+1)*a^2=80
 */
    }
  public static byte[] encryptSHA(byte[] data) throws Exception{
    MessageDigest sha = MessageDigest.getInstance("SHA");
    sha.update(data);
    return sha.digest();
  }


  public static String SHAEncrypt(final String content) {
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA");
      byte[] sha_byte = sha.digest(content.getBytes());
      StringBuffer hexValue = new StringBuffer();
      for (byte b : sha_byte) {
        //将其中的每个字节转成十六进制字符串：byte类型的数据最高位是符号位，通过和0xff进行与操作，转换为int类型的正整数。
        String toHexString = Integer.toHexString(b & 0xff);
        hexValue.append(toHexString.length() == 1 ? "0" + toHexString : toHexString);
      }
      return hexValue.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }



//  //SHA-256加密
//  public static String SHA256Encrypt(String sourceStr) {
//    MessageDigest md = null;
//    try {
//      md = MessageDigest.getInstance("SHA-256");
//    } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//    }
//    if (null != md) {
//      md.update(sourceStr.getBytes());
//      String digestStr = getDigestStr(md.digest());
//      return digestStr;
//    }
//
//    return null;
//  }

//  public static void main(String[] args) throws Exception {
//    //System.out.println(encryMD5("123456"));
//    //System.out.println(encryMD5Salt("123456"));
//    //System.out.println(verify("123456",encryMD5Salt("123456")));
//    System.out.println(encryptSHA("123456".getBytes()));
//    System.out.println(SHAEncrypt("123456"));
//    System.out.println(SHA256Encrypt("123456"));
//  }

}
