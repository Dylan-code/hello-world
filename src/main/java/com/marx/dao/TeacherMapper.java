package com.marx.dao;

import com.marx.entity.Teacher;
import com.marx.entity.Total;
import com.wobangkj.api.ITkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeacherMapper extends ITkMapper<Teacher> {

    /**
     * 根据教师Id查询教师信息
     * */
    Teacher queryById(String id);

    /**
     * 根据由childId,name,intro所组成的Teacher类来查询
     * */
    List<Teacher> queryAllLimit(Teacher teacher, int offset, int limit);

}
