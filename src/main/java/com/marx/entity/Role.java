package com.marx.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;
/**
 * 角色表
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Role {
    /**
     * 角色Id
     * */
    @Id
    private String roleId;

    /**
     * 角色名称
     * */
    private String roleName;

    /**
     * 该角色所拥有的权限
     * */
    @Transient
    private List<Authority> authorities;
}
