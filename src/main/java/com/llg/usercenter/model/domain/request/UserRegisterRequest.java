package com.llg.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author liulg
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -4193521407423070729L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;

}
