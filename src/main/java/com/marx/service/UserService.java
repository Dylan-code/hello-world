package com.marx.service;

import com.marx.entity.User;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    /**
     * 获取所有的用户信息
     * @return 查询到的结果
     * */
    public Pager<User> allUser(Condition condition);

    /**
     * 根据用户名和密码查询用户信息，并以此判断有符合该信息的用户(用户登录验证操作)
     * @param username 用户名
     * @return 查询结果
     * */
    public User checkUser(String username);

    /**
     * 为用户添加角色信息
     * @param userId 用户Id
     * @param roleIds 要添加的角色Id集合
     * @return 影响的行数
     * */
    public int addRole(String userId,List<String> roleIds);

    /**
     * 为用户删除角色
     * @param userId 用户Id
     * @param roleIds 要删除的角色Id集合
     * @return  影响的行数
     * */
    public int deleteRole(String userId,List<String> roleIds);

    /**
     * 验证用户名是否唯一
     * @param username 用户名
     * @return 验证结果  true（唯一）/false（不唯一）
     * */
    public boolean checkUsername(String username);

    /**
     * 修改指定用户的密码
     * @param username 用户名
     * @param password 密码
     * @return 影响的行数
     * */
    public Integer modUser(User user);

    /**
     * 添加用户
     * @param username 用户名
     * @param password 密码
     * @return 影响的行数
     * */
    public Integer addUser(String username,String password);

    /**
     * 根据用户名删除对应的用户
     * @param username 用户名
     * @return 影响的行数
     * */
    public Integer deleteUser(String username);

}
