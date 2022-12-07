package com.llg.usercenter.service;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.llg.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("llg");
        user.setUserAccount("123");
        user.setAvatarUrl("https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/1640082114571-1610793363633-bilibili.jpg");
        user.setGender(0);
        user.setUserPassword("123");
        user.setEmail("111");
        user.setPhone("111");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());


        boolean result = userService.save(user);
        System.out.println(result);
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister(){
        String userAccount = "liulg";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";

        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

//        userAccount = "liu";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);
//
//        userAccount = "liulg";
//        userPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1,result);

        userAccount = "liu lg";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount = "dogliulg";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);

        userAccount = "liulg";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertTrue(result > 0);


    }

    @Test
    void testRegister(){
        String userAccount = "liu lg";
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！ @#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        System.out.println(matcher.find());
    }

    @Test
    void testSearchUserByTags(){
        List<String> tagNameList = Arrays.asList("java","c++");
        List<User> users = userService.searchUserByTags(tagNameList);
        Assertions.assertNotNull(users);
    }

}