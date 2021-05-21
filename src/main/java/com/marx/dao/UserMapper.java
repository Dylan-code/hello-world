package com.marx.dao;

import com.marx.entity.User;
import com.wobangkj.api.ITkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper extends ITkMapper<User> {

    /**
     * 为用户添加角色信息
     * @param userId 用户Id
     * @param roleId 要添加的角色Id
     * @return 影响的行数
     * */
    int addRole(String userId,List<String> roleIds);

    /**
     * 为用户删除角色
     * @param userId 用户Id
     * @param roleIds 要删除的角色Id
     * @return  影响的行数
     * */
    int deleteRole(String userId,List<String> roleIds);

    /**
     * 根据user对象里的信息查询信息（包含该用户拥有的角色信息）
     * */
    User queryOne(User user);
}
