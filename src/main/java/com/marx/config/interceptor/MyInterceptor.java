package com.marx.config.interceptor;

import com.marx.entity.Role;
import com.marx.entity.User;
import com.marx.security.Authorcate;
import com.marx.service.RoleAuthService;
import com.wobangkj.api.Response;
import com.wobangkj.auth.Authenticate;
import com.wobangkj.auth.Author;
import com.wobangkj.utils.IocUtils;
import com.wobangkj.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private RoleAuthService roleAuthService;

    private boolean isFirst = true;

    //return true 执行下一个拦截器，放行，return false 则不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isFirst) {
            Authenticate.setAuthenticator(IocUtils.getBean(Authorcate.class));
            isFirst = false;
        }
        String token = request.getParameter("token");
        Author author = Authenticate.authenticate(token);

        if (author == null) {
            setResponse(response);
            return false;
        }
        User user = author.getData(User.class);
        String authStr = request.getMethod() + ":" + request.getRequestURI();
        List<Role> roles = user.getRoles();
        if (roles != null)
            for (Role role : roles) {
                if (role.getRoleName().equals("admin")) {
                    return true;
                }
                Role role1 = roleAuthService.checkRoleAuth(role.getRoleId(), authStr);
                if (role1 != null) {
                    return true;
                }
            }
        setResponse(response);
        return false;
    }

    public void setResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(Response.fail("暂无权限").toString());
    }

}
