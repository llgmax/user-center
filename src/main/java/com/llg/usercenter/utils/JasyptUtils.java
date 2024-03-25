package com.llg.usercenter.utils;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.junit.Test;

import javax.annotation.Resource;

public class JasyptUtils {
    /**
     * 引入jasypt-spring-boot-starter就会自动注入
     */
//    @Resource
//    private StringEncryptor stringEncryptor;

//    @Test
//    public void StringEncryptor() {
//        String encrypt = stringEncryptor.encrypt("newpwd");
//        System.out.println(encrypt);
//
//        String decrypt = stringEncryptor.decrypt(encrypt);
//        System.out.println(decrypt);
//    }

    public static void main(String[] args) {

        String info = encrypt("root");
        String info_1 = decrypt(info);

        System.out.println(info);
        System.out.println(info_1);
    }

    /**
     * 加密
     *
     * @param plaintext 明文
     * @return
     */
    public static String encrypt(String plaintext) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        // 指定算法
        config.setAlgorithm("PBEWithMD5AndDES");
        // 指定秘钥，和yml配置文件中保持一致
        config.setPassword("123456");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        encryptor.setConfig(config);
        // 生成加密数据
        return encryptor.encrypt(plaintext);
    }

    /**
     * 解密
     *
     * @param data 加密后数据
     * @return
     */
    public static String decrypt(String data) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
//        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("123456");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        encryptor.setConfig(config);
        // 解密数据
        return encryptor.decrypt(data);
    }
}