package com.marx.service;

import com.marx.entity.Slide;
import com.marx.entity.Total;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;

import java.util.List;

public interface SlideService {

    /**
     * 根据Id获取信息
     * @param id 该条数据的Id
     * @return 查询到的信息
     * */
    public Slide getById(String id);

    /**
     * 获取所有的轮播图信息
     * @return 查询到的结果
     * */
    public Pager<Slide> allSlide(Slide slide, Condition condition);

    /**
     * 添加一条轮播图信息
     * @param slide 要添加的轮播图信息
     * @return 影响的行数
     * */
    public Integer addSlide(Slide slide);

    /**
     * 修改轮播图的信息
     * @param slide 新的轮播图信息
     * @return 影响的行数
     * */
    public Integer modSlide(Slide slide);

    /**
     * 根据Id删除轮播图信息
     * @param id 要删除的轮播图Id
     * @return 影响的行数
     * */
    public Integer deleteSlide(String id);

}
