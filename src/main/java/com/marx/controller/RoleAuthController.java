package com.marx.controller;

import com.marx.dao.RoleAuthMapper;
import com.marx.entity.Role;
import com.marx.service.RoleAuthService;
import com.marx.util.StringUtil;
import com.wobangkj.api.IDao;
import com.wobangkj.api.Response;
import com.wobangkj.bean.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 和角色与权限相关的接口
 */
@Controller
public class RoleAuthController {

    @Autowired
    private RoleAuthService roleAuthService;

    /**
     * 根据条件（roleId）查询角色信息，如果都不填则是查询全部角色信息
     */
    @GetMapping("/role")
    @ResponseBody
    public Res allRole(Role role) {
        List<Role> roles = roleAuthService.getDao().queryAll(role);
        IDao<Role> dao = roleAuthService.getDao();
        return Response.ok(roles);
    }

    /**
     * 为角色分配权限
     */
    @GetMapping("/addRoleAuth")
    @ResponseBody
    public Res addRoleAuth(@RequestParam String roleId, @RequestParam String authIds) {
        List<String> authIdList = StringUtil.stringToList(authIds, ",");
        int i = roleAuthService.addRoleAuth(roleId, authIdList);
        return i > 0 ? Response.ok("分配成功") : Response.fail("没有全部分配成功");
    }

    /**
     * 为指定角色删除权限信息
     */
    @DeleteMapping("/deleteRoleAuth")
    @ResponseBody
    public Res deleteRoleAuth(@RequestParam String roleIds, @RequestParam String authIds) {
        List<String> authIdList = StringUtil.stringToList(authIds, ",");
        List<String> roleIdList = StringUtil.stringToList(roleIds, ",");
        int i = roleAuthService.deleteRoleAuth(roleIdList, authIdList);
        return i > 0 ? Response.ok("删除成功") : Response.fail("删除失败");
    }

    /**
     * 通过角色Id删除对应的角色信息
     */
    @DeleteMapping("/deleteRole")
    @ResponseBody
    public Res deleteRole(@RequestParam String roleIds) {
        List<String> roleIdList = StringUtil.stringToList(roleIds, ",");
        int i = roleAuthService.deleteRole(roleIdList);
        return i > 0 ? Response.ok("删除成功") : Response.fail("删除失败");
    }

    /**
     * 添加权限信息
     */
    @PostMapping("/addAuth")
    @ResponseBody
    public Res addAuth(@RequestParam String authName) {
        int i = roleAuthService.addAuth(authName);
        return i > 0 ? Response.ok("添加成功") : Response.fail("添加失败");
    }

    /**
     * 根据权限Id删除对应的权限信息
     */
    @DeleteMapping("/deleteAuth")
    @ResponseBody
    public Res deleteAuth(@RequestParam String authIds) {
        List<String> authIdList = StringUtil.stringToList(authIds, ",");
        int i = roleAuthService.deleteAuth(authIdList);
        return i > 0 ? Response.ok("删除权限成功") : Response.fail("删除权限失败");
    }
}
