package com.briup.dao;

import com.briup.bean.User;
import com.briup.bean.UserLog;

public interface UserDao {
public void insertUser(User user,UserLog userlog);
public User checkPassword(String username);
public User checkUser(String username);
}
