package com.marx.controller;

import com.marx.entity.User;
import com.marx.security.Authorcate;
import com.marx.service.UserService;
import com.marx.util.StringUtil;
import com.marx.vo.PageInfo;
import com.wobangkj.api.Response;
import com.wobangkj.auth.Authenticate;
import com.wobangkj.auth.Author;
import com.wobangkj.bean.Pager;
import com.wobangkj.bean.Res;
import com.wobangkj.utils.IocUtils;
import com.wobangkj.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 执行登录操作
     *
     * @param loginUser 用由用户信息构成的用户对象
     * @return 登录是否成功的结果
     */
    @PostMapping("/login")
    @ResponseBody
    public Res login(User loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        if (username == null || password == null || "".equals(username) || "".equals(password)) {
            return Response.fail("用户名或密码都不能为空");
        }
        User user = userService.checkUser(username);
        if (user == null) {
            return Response.fail("用户名不存在或错误");
        } else if (!user.getPassword().equals(StringUtil.encrypt_MD5(password))) {
            return Response.fail("密码错误");
        } else {
            Authenticate.setAuthenticator(IocUtils.getBean(Authorcate.class));
            String token = Authenticate.authorize(Author.builder().data(user).build(),new Date(System.currentTimeMillis() + 24*60*60*60));
            return Response.ok(token);
        }
    }

    /**
     * 用户执行退出登录操作
     */
    @GetMapping("/logout")
    @ResponseBody
    public Res logout() {
        try {
            stringRedisTemplate.move("user", 1);
            return Response.ok("退出成功");
        } catch (Exception e) {
            return Response.fail("退出失败");
        }
    }

    /**
     * 添加新的用户
     */
    @PostMapping("/addUser")
    @ResponseBody
    public Res addUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (username == null || password == null || "".equals(username) || "".equals(password)) {
            return Response.err("用户名或密码都不能为空");
        }

        if (userService.checkUsername(username)) {
            Integer i = userService.addUser(username, password);
            return i > 0 ? Response.ok("添加成功") : Response.fail("添加失败");
        } else {
            return Response.fail("用户名已经存在");
        }
    }

    /**
     * 用户修改密码
     */
    @PutMapping("/modUser")
    @ResponseBody
    public Res modUser(@RequestParam String oldPassword, @RequestParam String newPassword) {
        String userStr = stringRedisTemplate.opsForValue().get("user");
        User currentUser = JsonUtils.fromJson(userStr, User.class);
        System.out.println("当前用户----》" + currentUser);
        //获取用户旧密码，用户输入的旧密码，用户输入的新密码
        String currentPwd = currentUser.getPassword();
        oldPassword = StringUtil.encrypt_MD5(oldPassword);
        newPassword = StringUtil.encrypt_MD5(newPassword);
        if (!currentPwd.equals(oldPassword)) {
            return Response.fail("修改失败，旧密码错误");
        }
        if (currentPwd.equals(newPassword)) {
            return Response.fail("修改失败，新密码不能和旧密码相同");
        }
        currentUser.setPassword(newPassword);
        Integer i = userService.modUser(currentUser);
        return i > 0 ? Response.ok() : Response.fail("修改失败");
    }

    /**
     * 获取所有用户
     */
    @GetMapping("/allUser")
    @ResponseBody
    public Res allUser(PageInfo pageInfo) {
        Pager<User> userPager = userService.allUser(pageInfo.toCon());
        return Response.ok(userPager);
    }

    /**
     * 根据用户名删除对应的用户
     */
    @DeleteMapping("/deleteUser/{username}")
    @ResponseBody
    public Res deleteUser(@PathVariable String username) {
        Integer i = userService.deleteUser(username);
        return i > 0 ? Response.ok() : Response.fail("删除失败");
    }

    /**
     * 用户添加角色
     */
    @PostMapping("/addUserRole")
    @ResponseBody
    public Res addRole(@RequestParam String userId, @RequestParam String roleIds) {
        /* 获取由角色Id组成的list集合 */
        List<String> roleIdList = StringUtil.stringToList(roleIds, ",");
        int i = userService.addRole(userId, roleIdList);
        return i > 0 ? Response.ok() : Response.fail("添加失败");
    }

    /**
     * 为用户删除角色
     */
    @DeleteMapping("/deleteUserRole")
    @ResponseBody
    public Res deleteRole(@RequestParam  String userId, @RequestParam String roleIds) {
        /* 获取由角色Id组成的list集合 */
        List<String> roleIdList = StringUtil.stringToList(roleIds, ",");
        int i = userService.deleteRole(userId, roleIdList);
        return i > 0 ? Response.ok() : Response.fail("删除失败");
    }

}
