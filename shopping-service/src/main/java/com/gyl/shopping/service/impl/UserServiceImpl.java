package com.gyl.shopping.service.impl;

import com.gyl.shopping.api.UserService;
import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.Md5Utils;
import com.gyl.shopping.dao.UserMapper;
import com.gyl.shopping.dto.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: gyl
 * @Description: 用户相关
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer createUser(String userName, String password, String emailAddress, Integer rule) {
        User user = searchUser(userName);
        if (user != null){
            throw new MallException(ExceptionEnum.USER_HAS_EXIST);
        }
        String md5Str = Md5Utils.getMd5Str(password);
        User newUser = new User();
        newUser.setCreateTime(new Date());
        newUser.setEmailAddress(emailAddress);
        newUser.setPassword(md5Str);
        newUser.setPersonalizedSignature("暂无说明");
        newUser.setRole(rule);
        newUser.setUsername(userName);
        newUser.setUpdateTime(new Date());
        return userMapper.insert(newUser);
    }

    @Override
    public User searchUser(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public void updateSignature(String signature, Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null){
            throw new MallException(ExceptionEnum.NOT_USER);
        }
        user.setPersonalizedSignature(signature);
        user.setUpdateTime(new Date());
        int i = userMapper.updateByPrimaryKey(user);
        if (i < 1){
            throw new MallException(ExceptionEnum.UPDATE_ERROR);
        }
    }
}
