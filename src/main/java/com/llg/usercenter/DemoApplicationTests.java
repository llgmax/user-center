package com.llg.usercenter;

import com.llg.usercenter.utils.EncryptorBootUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserCenterApplication.class)
public class DemoApplicationTests {

    @Value("${spring.datasource.password}")
    private String value;

    @Test
    public void utilsTest(){
       System.out.println("value："+value);
    }
    @Autowired
    private EncryptorBootUtils bootUtils;

    @Test
    public void utilsTest2(){
        //System.out.println("value："+value);
        bootUtils.testEncryptor("redis@App2020");
    }
}