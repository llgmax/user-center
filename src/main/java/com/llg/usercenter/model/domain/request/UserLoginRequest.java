package com.llg.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author liulg
 */
@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = 3169796791540457486L;
    private String userAccount;
    private String userPassword;

}
