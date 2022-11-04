package com.gyl.shopping.api;

import com.gyl.shopping.dto.User;

/**
 * @Author: gyl
 * @Description: 用户
 */
public interface UserService {
    public Integer createUser(String userName, String password, String emailAddress, Integer rule);
    public User searchUser(String userName);
    public void updateSignature(String signature, Integer userId);
}
