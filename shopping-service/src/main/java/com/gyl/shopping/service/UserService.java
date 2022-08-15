package com.gyl.shopping.service;

import com.gyl.shopping.dto.User;

public interface UserService {
    public Integer createUser(String userName, String password, String emailAddress, Integer rule);
    public User searchUser(String userName);
    public void updateSignature(String signature, Integer userId);
}
