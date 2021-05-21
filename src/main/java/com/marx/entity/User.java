package com.marx.entity;

import com.wobangkj.api.SessionSerializable;
import com.wobangkj.utils.JsonUtils;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * 用户实体类
 * */
@Setter
@Getter
@NoArgsConstructor
@Entity
public class User implements SessionSerializable {

    private static final long serialVersionUID = -7320965599817552517L;
    @Id
    private String id;

    private String username;

    private String password;

    /**
     * 用户所拥有的角色列表
     * */
    @Transient
    private List<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
