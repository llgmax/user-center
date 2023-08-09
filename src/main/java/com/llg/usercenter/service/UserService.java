package com.llg.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.llg.usercenter.model.domain.User;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author liulg
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2022-09-18 12:22:44
*/
public interface UserService extends IService<User> {



    /**
     *用户注册
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogOut(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     * @param tagNameList 标签json列表
     * @return
     */
    List<User> searchUserByTags (List<String> tagNameList);

    /**
      * 修改用户资料
      * @param user
      * @return
      */
    int updateUser (User user, User loginUser);

    /**
      * 获取当前登录用户信息
      * @param request
      * @return
      */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否管理员
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);
    boolean isAdmin(User loginUser);

}
