package com.marx.controller;

import com.marx.entity.Teacher;
import com.marx.entity.User;
import com.marx.service.TeacherService;
import com.marx.service.UserService;
import com.marx.util.FileUtil;
import com.wobangkj.api.Response;
import com.wobangkj.bean.Res;
import com.wobangkj.utils.BeanUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@CrossOrigin
public class FileController {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/upload")
    @ResponseBody
    public Res upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            return Response.err("文件上传失败，请重写上传");
        }

        /* 执行将文件保存到磁盘的操作 */
        String result = fileUtil.upload(file);
        String dbPath = "文件上传失败，请重写上传";
        if (!result.equals("false")) {
            /* 浏览器访问图片的路径 */
            dbPath = result.replaceAll("\\\\", "/");
            return Response.ok(dbPath);
        } else {
            return Response.err("文件上传失败，请重写上传");
        }
    }

    /**
     * 向word中填充文件
     * */
    @PostMapping("/fill/{id}")
    @ResponseBody
    public Res fill(@RequestParam("fill") MultipartFile fill,@PathVariable String id){


        //获取文件后缀名
        String fileName = fill.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        //判断后缀名是否合法
        if(suffix.equals("docx") || suffix.equals("doc")){
            try {
                Map<String, Object> map = BeanUtils.convert(teacherService.getTeacherById(id));
                //填充成功，则返回文件的访问路径
               String result = fileUtil.fillWord(fill,map).replaceAll("\\\\", "/");
               return Response.ok(result);
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return Response.fail("填充失败");
        } else {
            return Response.fail("请上传以docx或doc为后缀名的word文件");
        }
    }

}
