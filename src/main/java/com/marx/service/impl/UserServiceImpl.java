package com.marx.service.impl;

import com.marx.dao.UserMapper;
import com.marx.entity.User;
import com.marx.service.UserService;
import com.marx.util.StringUtil;
import com.marx.util.uuid.IdUtils;
import com.wobangkj.api.IService;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import com.wobangkj.impl.TkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends TkServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有的用户信息
     * @return 查询到的结果
     * */
    @Override
    public Pager<User> allUser(Condition condition) {
        UserMapper dao = this.getDao();
        dao.selectAll();
        return this.getDao().queryAllPage(null,condition);
    }

    /**
     * 根据用户名和密码查询用户信息，并以此判断有符合该信息的用户(用户登录验证操作)
     * @param username 用户名
     * @return 查询结果
     * */
    @Override
    public User checkUser(String username) {
        User user = new User(username, null);
        return this.getDao().queryOne(user);
    }

    /**
     * 为用户添加角色信息
     * @param userId 用户Id
     * @param roleIds 要添加的角色Id
     * @return 影响的行数
     * */
    @Override
    public int addRole(String userId, List<String> roleIds) {
          return userMapper.addRole(userId,roleIds);
    }

    /**
     * 为用户删除角色
     * @param userId 用户Id
     * @param roleIds 要删除的角色Id
     * @return  影响的行数
     * */
    @Override
    public int deleteRole(String userId, List<String> roleIds) {
           return userMapper.deleteRole(userId,roleIds);
    }

    /**
     * 验证用户名是否唯一
     * @param username 用户名
     * @return 验证结果  true（唯一）/false（不唯一）
     * */
    @Override
    public boolean checkUsername(String username) {
        User user = checkUser(username);
        return user == null ? true : false;
    }

    /**
     * 修改指定用户的密码
     * @param user 用户对象
     * @return 影响的行数
     * */
    @Override
    public Integer modUser(User user) {
        return this.getDao().update(user);
    }

    /**
     * 添加用户
     * @param username 用户名
     * @param password 密码
     * @return 影响的行数
     * */
    @Override
    public Integer addUser(String username,String password) {
        //使用MD5加密对密码进行加密后保存
        String encrypt_pwd = StringUtil.encrypt_MD5(password);
        User newUser = new User(username, encrypt_pwd);
        newUser.setId(IdUtils.randomUUID());
        return this.getDao().insert(newUser);
    }

    /**
     * 根据用户名删除对应的用户
     * @param username 用户名
     * @return 影响的行数
     * */
    @Override
    public Integer deleteUser(String username) {
        User user = new User(username, null);
        return this.getDao().delete(user);
    }
}
