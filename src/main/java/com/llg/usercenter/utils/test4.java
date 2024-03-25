package com.llg.usercenter.utils;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.jasypt.encryption.*;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class test4 {

    @Resource
    private StringEncryptor stringEncryptor;

    @Test
    public void encode(){
        System.out.println(stringEncryptor.encrypt("admin"));
        System.out.println(stringEncryptor.decrypt(stringEncryptor.encrypt("admin")));

    }
    @Test
    public void encode2(){
        System.out.println(StringUtils.isEmpty("qq"));

    }


}
