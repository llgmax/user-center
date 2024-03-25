package com.llg.usercenter.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class Jasypt {

    /**
     * 2.1.2 jasypt加密解密方法
     * ���ܷ���
     *
     * @param password jasypt����Ҫ�ļ�����������
     * @param value    ��Ҫ���ܵ�����
     */
    public static String encyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor.encrypt(value);
    }

    public static String decyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor.decrypt(value);
    }
    public static void main(String[] args) {
        System.out.println("���ܴ���: " + encyptPwd("vHrTRfnN7CQeYzBVVD6fJf6agZKarALH", "lmsapp_App2020"));
        System.out.println(decyptPwd("vHrTRfnN7CQeYzBVVD6fJf6agZKarALH", "2eBo5GmSit02Lds6OxQjdetCc8dR7aVs"));
    }
}