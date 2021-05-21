package com.marx.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 学院概括、教学培养、科学研究
 * 党群工作、专题专栏、传习讲坛、首页设置（通知公告、学院动态）
 * 以上部分信息都采用这个实体类
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Total {
    /**
     * 信息ID
     * */
    @Id
    private String id;

    /**
     * 子级分类
     * */
    private String childId;

    /**
     * 子分类名称
     * */
    @Transient
    private String childClass;

    /**
     * 标题
     * */
    private String title;

    /**
     * 内容
     * */
    private String content;

    /**
     * 文件保存在磁盘中的地址
     * */
    private String fileAddress;

    /**
     * 发布时间
     * */
    private String date;
}
