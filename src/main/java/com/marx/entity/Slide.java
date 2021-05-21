package com.marx.entity;



import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 轮播图实体类
 * */
@Entity
public class Slide {

    /**
     * 轮播图Id
     * */
    @Id
    private String id;

    /**
     * 轮播图标题
     * */
    private String title;

    /**
     * 轮播图图片保存在磁盘中的地址
     * */
    private String img;

    public Slide(String id, String title, String img) {
        this.id = id;
        this.title = title;
        this.img = img;
    }

    public Slide() {
    }

    @Override
    public String toString() {
        return "Slide{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
