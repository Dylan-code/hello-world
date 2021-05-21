package com.marx.entity;

import com.wobangkj.annotation.LeftJoin;
import lombok.*;

import javax.persistence.*;

/**
 * 师资力量实体类
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Teacher {
    /**
     * 教师Id
     * */
    @Id
    private String id;

    /**
     * 子分类Id
     * */
    private String childId;

    /**
     * 子分类名称
     * */
    @Transient
    private String childClass;

    /**
     * 教师姓名
     * */
    private String name;

    /**
     * 教师图片地址
     * */
    private String img;

    /**
     * 教师介绍
     * */
    private String intro;



}
