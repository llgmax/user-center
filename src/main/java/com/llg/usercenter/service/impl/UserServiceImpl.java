package com.llg.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.llg.usercenter.common.ErrorCode;
import com.llg.usercenter.constant.UserConstant;
import com.llg.usercenter.exception.BusinessException;
import com.llg.usercenter.model.domain.User;
import com.llg.usercenter.service.UserService;
import com.llg.usercenter.mapper.UserMapper;
import javafx.scene.effect.SepiaTone;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.llg.usercenter.constant.UserConstant.USER_LOGIN_STATE;
import static com.llg.usercenter.constant.UserConstant.ADMIN_ROLE;

/**
* @author liulg
* @description 针对表【user(用户)】的数据库操作Service实现 用户服务实现类
* @createDate 2022-09-18 12:22:44
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值 混淆数据
     */
    private static final String SALT = "llg";



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        //1 校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户过短");
        }
        if (userPassword.length()<8 || checkPassword.length() < 8 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        if (planetCode.length() > 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
        }

        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！ @#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含特殊字符");
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
        }
        //星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号重复");
        }
        //密码和检验密码相同
        if (!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一致");
        }
        //2 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //3 插入数据
        User user= new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean result = this.save(user);
        if (!result){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一致");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //校验用户名和密码是否合法
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户过短");
        }
        if (userPassword.length()< 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }

        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！ @#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含特殊字符");
        }

        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //校验账户和密码
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null){
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.NULL_ERROR,"用户不存在");
        }
        //3 用户脱敏
        User safetyUser = getSafetyUser(user);
        //4 记录用户的登陆态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }

    /**
     * 用户脱敏
     * @param  originUser
     * @return safetyUser
     */
    @Override
    public User getSafetyUser(User originUser){
        if (originUser == null){
            return null;
        }
        User safetyUser  = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(0);
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        safetyUser.setProfile(originUser.getProfile());
        safetyUser.setTags(originUser.getTags());

        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @Override
    public int userLogOut(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
    /**
     * 修改用户资料
     * @param user 修改用户资料
     * @return
     */
    @Override
    public int updateUser (User user,User loginUser){
        if(isAdmin(loginUser)) {

        }

        return 1;
    }

    /**
     * 获取当前登录用户信息
     * @param request 获取当前登录用户信息
     * @return
     */
    @Override
    public User getLoginUser (HttpServletRequest request){
        if(request == null){
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if(userObj == null){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        return (User) userObj;
    }


    /**
     * 根据标签搜索用户
     * @param tagNameList 标签json列表
     * @return
     */
    @Override
    public List<User> searchUserByTags (List<String> tagNameList){
        //sql查询
//        if (CollectionUtils.isEmpty(tagNameList)){
//            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        //拼接 and 语句
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        for (String tagName : tagNameList){
//            queryWrapper = queryWrapper.like("tags",tagName);
//        }
//        List<User> list = this.list(queryWrapper);
//        //脱敏
//        return list.stream().map(this::getSafetyUser).collect(Collectors.toList());

        //内存查询
        //1.先查询所有用户
        QueryWrapper wrapper = new QueryWrapper();
        List<User> userList = userMapper.selectList(wrapper);
        Gson gson = new Gson();
        //2.再在内存中判断是否符合条件
        return userList.stream().filter(user -> {
            String tagStr = user.getTags();
            if (StringUtils.isBlank(tagStr)){
                return false;
            }
            Set<String> tempTagNameSet = gson.fromJson(tagStr, new TypeToken<Set<String>>(){}.getType());
            for(String tagName : tagNameList){
                if (!tempTagNameSet.contains(tagName)){
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());
    }

    /**
      * 是否管理员
      * @param request
      * @return
      */
    @Override
    public boolean isAdmin(HttpServletRequest request){
        Object objUser = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)objUser;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    @Override
    public boolean isAdmin(User loginUser){
        return loginUser != null && loginUser.getUserRole() == UserConstant.ADMIN_ROLE;
    }
}




