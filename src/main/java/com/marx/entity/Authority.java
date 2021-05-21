package com.marx.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 权限表
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Authority {

    /**
     * 权限Id
     * */
    @Id
    private String authId;

    /**
     * 权限名称
     * */
    private String authName;
}
