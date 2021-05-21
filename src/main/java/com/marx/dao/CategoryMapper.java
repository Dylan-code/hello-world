package com.marx.dao;

import com.marx.entity.Category;
import com.wobangkj.api.ITkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper extends ITkMapper<Category> {
    /**
     * 根据id删除分类，如果存在子分类，则一并删除
     * */
    int deleteById(Object id);
}
