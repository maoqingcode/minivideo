package com.mao.service;

import com.mao.pojo.Users;

public interface UserService {
    /*
    判断用户名是否存在
     */
    public boolean isUserNameIsExist(String userName);

    /*
    保存用户名
     */
    boolean saveUser(Users user);
    /*
    判断用户是否存在
     */
    Users queryIsUserExistForLogin(String username,String password);

    void updateUserInfo(Users user);

    Users queryUserInfo(String userId);

    boolean queryIfFollow(String userId, String fanid);
    /*
    d
     */
}
