package com.marx.service;

import com.marx.entity.Role;
import com.wobangkj.api.IService;

import java.util.List;

public interface RoleAuthService extends IService<Role> {

    /**
     * 获取所有的角色信息（包含该角色所拥有的角色信息）
     * */
    public List<Role> allRole();



    /**
     * 根据角色Id查询对应的角色信息（包含该角色所拥有的角色信息）
     * */
    public Role getRole(String roleId);

    /**
     * 查询一个角色是否拥有某个权限
     * @param roleId 被查询角色的Id
     * @param authName 要查询的那个权限
     * @return 如果符合条件则返回该角色对象，否则为null
     * */
    public Role checkRoleAuth(String roleId,String authName);

    public int addRole(Role role);

    /**
     * 为角色分配权限
     * @param roleId 角色Id
     * @param authIds 权限Id集合
     * @return 影响的行数
     * */
    public int addRoleAuth(String roleId,List<String> authIds);

    /**
     * 为指定角色删除权限
     * @param roleId 角色Id
     * @param authIds 权限Id集合
     * @return 影响的行数
     * */
    public int deleteRoleAuth(List<String> roleIds, List<String> authIds);

    /**
     * 删除角色信息
     * @param roleId 要删除的角色Id
     * */
    public int deleteRole(List<String> roleId);

    /**
     * 添加权限信息
     * @param authName 要添加的权限名称
     * @return 影响的行数
     * */
    public int addAuth(String authName);

    /**
     * 删除权限信息
     * @param authIda 要删除的权限Id集合
     * @return 影响的行数
     * */
    public int deleteAuth(List<String> authIds);
}
