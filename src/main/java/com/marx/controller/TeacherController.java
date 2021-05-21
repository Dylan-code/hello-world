package com.marx.controller;

import com.marx.entity.Teacher;
import com.marx.service.TeacherService;
import com.marx.vo.PageInfo;
import com.wobangkj.api.Response;
import com.wobangkj.bean.Pager;
import com.wobangkj.bean.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 根据Id获取对应的教师信息
     */
    @GetMapping("/teacher/{id}")
    @ResponseBody
    public Res getTeacherById(@PathVariable String id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return teacher != null ? Response.ok(teacher) : Response.err("获取失败");
    }

    /**
     * 根据某些条件查询教师信息（当传递参数的所有值为空时，就是查询全部教师）
     */
    @GetMapping("/teacher")
    @ResponseBody
    public Res getTeacher(Teacher teacher, PageInfo pageInfo) {
        Pager<Teacher> teacherResult = teacherService.queryAll(teacher, pageInfo.toCon());
        return Response.ok(teacherResult);
    }

    /**
     * 添加教师信息
     */
    @PostMapping("/teacher")
    @ResponseBody
    public Res addTeacher(@RequestBody Teacher teacher) {
        Integer i = teacherService.addTeacher(teacher);
        return i > 0 ? Response.ok("添加成功") : Response.err("添加失败");
    }


    /**
     * 修改教师信息
     */
    @PutMapping("/teacher")
    @ResponseBody
    public Res modTeacher(@RequestBody Teacher teacher) {
        Integer i = teacherService.modTeacher(teacher);
        return i > 0 ? Response.ok("修改成功") : Response.err("修改失败");
    }

    /**
     * 根据教师Id删除教师信息
     */
    @DeleteMapping("/teacher/{id}")
    @ResponseBody
    public Res deleteTeacher(@PathVariable String id) {
        Integer i = teacherService.deleteTeacher(id);
        return i > 0 ? Response.ok("删除成功") : Response.err("删除失败");
    }
}
