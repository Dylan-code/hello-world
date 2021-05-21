package com.marx.dao;

import com.marx.entity.Total;
import com.wobangkj.api.ITkMapper;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Total数据层
 * */
@Repository
@Mapper
public interface TotalMapper extends ITkMapper<Total> {

    /**
     * 根据Id获取信息
     * @param id 该条数据的Id
     * @return 查询到的信息
     * */
    Total queryById(String id);

    /**
     * 根据某些条件查询信息
     * @param total 由查询条件组成的对象
     * @return 查询到的结果
     * */
    List<Total> queryAllLimit(Total total, int offset, int limit);
}
