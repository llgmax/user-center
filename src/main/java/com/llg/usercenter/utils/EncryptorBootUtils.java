package com.llg.usercenter.utils;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncryptorBootUtils {
    @Autowired
    private StringEncryptor stringEncryptor;

    public void testEncryptor(String msg) {
        System.out.println("原始的信息为"+ msg);
        // 下面的方法是加密
        String encryptStr = stringEncryptor.encrypt(msg);
        System.out.println("加密后的信息为"+encryptStr);
        // 下面的方法是解密
        String decrypt = stringEncryptor.decrypt("解密后的信息为"+encryptStr);
        System.out.println(decrypt);
    }

}


