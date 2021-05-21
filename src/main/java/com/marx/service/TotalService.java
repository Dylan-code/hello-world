package com.marx.service;


import com.marx.entity.Total;
import com.wobangkj.api.IService;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
public interface TotalService extends IService<Total> {

    /**
     * 根据Id获取信息
     * @param id 该条数据的Id
     * @return 查询到的信息
     * */
    public Total getById(String id);

    /**
     * 添加数据
     * @param total 添加的数据的实体类
     * @return 影响的行数
     * */
    public Integer add(Total total);

    /**
     * 修改数据
     * @param total 新的数据
     * @return 影响的行数
     * */
    public Integer mod(Total total);

    /**
     * 根据Id删除一条数据
     * @param id 要删除的数据的Id
     * @return 影响的行数
     * */
    public Integer delete(String id);

}
