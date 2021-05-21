package com.marx.dao;

import com.marx.entity.TempFile;
import com.wobangkj.api.ITkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TempFileMapper extends ITkMapper<TempFile> {

    /**
     * 删除数据库中的所有临时文件信息
     * */
    public int deleteAllTempFile();

}
