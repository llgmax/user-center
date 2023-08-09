package com.llg.usercenter.utils;

import java.sql.SQLOutput;

public class test {
    public static void main(String[] args){
        String str = "987654321987654321";
        String regex = "\\d{17}[Xx]";//用正则表达式定义手机号规则
        boolean flag = str.matches(regex);
        System.out.println(flag);
    }
}
