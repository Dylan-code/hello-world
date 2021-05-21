package com.marx.service.impl;

import com.marx.dao.RoleAuthMapper;
import com.marx.dao.UserMapper;
import com.marx.entity.Role;
import com.marx.service.RoleAuthService;
import com.wobangkj.impl.TkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleAuthServiceImpl extends TkServiceImpl<RoleAuthMapper, Role> implements RoleAuthService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有的角色信息（包含该角色所拥有的权限信息）
     */
    @Override
    public List<Role> allRole() {
        return this.getDao().selectAll();
    }

    /**
     * 根据角色Id查询对应的角色信息（包含该角色所拥有的权限信息）
     */
    @Override
    public Role getRole(String roleId) {
        return this.getDao().queryById(roleId);
    }

    /**
     * 查询一个角色是否拥有某个权限
     *
     * @param roleId   被查询角色的Id
     * @param authName 要查询的那个权限
     * @return 如果符合条件则返回该角色对象，否则为null
     */
    @Override
    public Role checkRoleAuth(String roleId, String authName) {
        return this.getDao().checkRoleAuth(roleId, authName);
    }

    /**
     * 添加角色信息
     *
     * @param role 要添加的角色对象
     * @return 影响的行数
     */
    @Override
    public int addRole(Role role) {
        return this.getDao().insert(role);
    }

    /**
     * 为角色分配权限
     *
     * @param roleId  角色Id
     * @param authIds 权限Id集合
     * @return 影响的行数
     */
    @Override
    public int addRoleAuth(String roleId, List<String> authIds) {
        return this.getDao().addRoleAuth(roleId, authIds);
    }

    /**
     * 为指定角色删除权限
     *
     * @param roleIds 角色Id
     * @param authIds 权限Id集合
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int deleteRoleAuth(List<String> roleIds, List<String> authIds) {
        return this.getDao().deleteRoleAuth(roleIds, authIds);
    }

    /**
     * 删除角色信息
     *
     * @param roleIds 要删除的角色Id
     */
    @Override
    @Transactional
    public int deleteRole(List<String> roleIds) {
        //删除该角色和其他user之间的绑定关系
        userMapper.deleteRole(null, roleIds);
        //删除该角色和其他auth之间的绑定关系
        this.getDao().deleteRoleAuth(roleIds, null);
        //从role表中删除该角色信息
        return this.getDao().deleteById(roleIds);
    }

    /**
     * 添加权限信息
     *
     * @param authName 要添加的权限名称
     * @return 影响的行数
     */
    @Override
    public int addAuth(String authName) {
        return this.getDao().addAuth(authName);
    }

    /**
     * 删除权限信息
     *
     * @param authIds 要删除的权限Id集合
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int deleteAuth(List<String> authIds) {
        this.getDao().deleteRoleAuth(null, authIds);
        return this.getDao().deleteAuth(authIds);
    }
}
