package com.marx.service;

import com.marx.entity.Teacher;
import com.wobangkj.api.IService;
import com.wobangkj.bean.Pageable;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;

import java.util.List;

public interface TeacherService extends IService<Teacher> {


    /**
     * 根据教师Id查询教师信息
     *
     * @param id 教师Id
     * @return 查询到的教师信息
     */
    Teacher getTeacherById(String id);


    /**
     * 根据子分类查询教师信息
     *
     * @param childId 子分类
     * @return 查询结果
     */
    Pager<Teacher> getTeacher(Teacher teacher, Condition pageable);

    /**
     * 添加数据
     *
     * @param teacher 添加的教师的实体类
     * @return 影响的行数
     */
    Integer addTeacher(Teacher teacher);

    /**
     * 修改数据
     *
     * @param teacher 新教师数据
     * @return 影响的行数
     */
    Integer modTeacher(Teacher teacher);

    /**
     * 根据Id删除一条教师数据
     *
     * @param id 要删除的教师数据的Id
     * @return 影响的行数
     */
    Integer deleteTeacher(String id);

}
