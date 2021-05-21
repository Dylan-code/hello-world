package com.marx.entity;

import com.wobangkj.annotation.LikeExclude;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 类别尸体类
 * */
@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = 7780291287541229682L;
    /**
     * 类别Id
     * */
    @Id
    @LikeExclude
    private Long id;

    /**
     * 父类别Id   0表示无父类，最高类别
     * */
    private String parentId;

    /**
     * 类别名称
     * */
    private String name;

    public Category(Long id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
