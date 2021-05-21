package com.marx.service.impl;

import com.marx.dao.TeacherMapper;
import com.marx.entity.Teacher;
import com.marx.service.TeacherService;
import com.marx.util.FileUtil;
import com.marx.util.uuid.IdUtils;
import com.wobangkj.bean.Pageable;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import com.wobangkj.domain.DateAmong;
import com.wobangkj.impl.TkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class TeacherServiceImpl extends TkServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private FileUtil fileUtil;

    /**
     * 根据教师Id查询教师信息
     *
     * @param id 教师Id
     * @return 查询到的教师信息
     */
    @Override
    public Teacher getTeacherById(String id) {
        return this.getDao().queryById(id);
    }

    /**
     * 根据子分类查询教师信息
     *
     * @param teacher 子分类
     * @return 查询结果
     */
    @Override
    public Pager<Teacher> getTeacher(Teacher teacher, Condition condition) {
        return this.getDao().queryAllPage(teacher,condition);
    }

    /**
     * 添加数据
     *
     * @param teacher 添加的教师的实体类
     * @return 影响的行数
     */
    @Override
    @Transactional
    public Integer addTeacher(Teacher teacher) {
        //如果该teacher对象没有Id则添加一个UUId作为teacher的Id
        if (teacher.getId() == null || teacher.getId().equals("")) {
            teacher.setId(IdUtils.randomUUID());
        }
        /* 该条数据已经保存在数据库中，所以将该文件从临时文件表中删除，防止被定时任务删除 */
        fileUtil.deleteTempFileFromTable(teacher.getImg());
        //向数据库中插入教师信息
        return this.getDao().insert(teacher);
    }

    /**
     * 修改数据
     *
     * @param teacher 新教师数据
     * @return 影响的行数
     */
    @Override
    @Transactional
    public Integer modTeacher(Teacher teacher) {
        Teacher result = getTeacherById(teacher.getId());
        /* 如果新数据的文件信息和旧数据的文件信息相同，则说明没有修改添加的文件，反之则认为修改 */
        if (!teacher.getImg().equals(result.getImg())) {
            /* 如果不相等，则删除旧的保存在磁盘中的信息 */
            fileUtil.deleteDiskFile(teacher.getImg());
            /* 并把新的文件信息从临时文件库中删除 */
            fileUtil.deleteTempFileFromTable(teacher.getImg());
        }
        //更新教师信息
        return this.getDao().update(teacher);
    }

    /**
     * 根据Id删除一条教师数据
     *
     * @param id 要删除的教师数据的Id
     * @return 影响的行数
     */
    @Override
    @Transactional
    public Integer deleteTeacher(String id) {
        //根据id获取教师信息，并将和该教师相关的其他信息都删除
        Teacher teacher = getTeacherById(id);
        fileUtil.deleteDiskFile(teacher.getImg());
        return this.getDao().deleteById(id);
    }
}
