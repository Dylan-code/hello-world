package com.marx.dao;

import com.marx.entity.Role;
import com.wobangkj.api.ITkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleAuthMapper extends ITkMapper<Role> {

    /**
     * 获取所有的角色（包含该角色所拥有的权限信息）
     * @return 查询到的所有角色集合
     * */
    List<Role> queryAll(Role role);

    /**
     * 根据角色Id查询对应的角色信息（包含该角色所拥有的权限信息）
     * */
    Role queryById(String id);

    /**
     * 查询一个角色是否拥有某个权限
     * @param roleId 被查询角色的Id
     * @param authName 要查询的那个权限
     * @return 如果符合条件则返回该角色对象，否则为null
     * */
    public Role checkRoleAuth(String roleId,String authName);

    /**
     * 为角色分配权限
     * @param roleId 角色Id
     * @param authId 权限Id
     * @return 影响的行数
     * */
    public int addRoleAuth(String roleId,List<String> authIds);

    /**
     * 为指定角色删除权限
     * @param roleId 角色Id
     * @param authIds 权限Id集合
     * @return 影响的行数
     * */
    public int deleteRoleAuth(List<String> roleId,@Param("authIds") List<String> authIds);

    /**
     * 删除角色信息
     * @param roleIds 要删除的角色Id集合
     * */
    int deleteById(List<String> roleIds);

    /**
     * 添加权限信息
     * @param authName 要添加的权限名称
     * @return 影响的行数
     * */
    public int addAuth(String authName);

    /**
     * 删除权限信息
     * @param authIds 要删除的权限Id集合
     * @return 影响的行数
     * */
    public int deleteAuth(List<String> authIds);

}
