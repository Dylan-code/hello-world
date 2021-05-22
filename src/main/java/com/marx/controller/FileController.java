package com.marx.controller;

import com.marx.service.TeacherService;
import com.marx.util.FileUtil;
import com.wobangkj.api.Response;
import com.wobangkj.bean.Res;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

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
        if (Objects.isNull(file) || fileUtil.getFileSize(file.getSize()) > 10) {
            return Response.err("文件上传失败，请重写上传。注意文件大小不能超过10MB");
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
     * 用教师信息向word中填充内容
     */
    @PostMapping("/fill/{id}")
    @ResponseBody
    public String fill(@RequestParam("fill") MultipartFile fill, @PathVariable String id,HttpServletResponse response) {
        if (Objects.isNull(fill) || fileUtil.getFileSize(fill.getSize()) > 10) {
            return JsonUtils.toJson(Response.err("文件上传失败，请重写上传。注意文件大小不能超过10MB"));
        }
        //获取文件后缀名
        String fileName = fill.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //判断后缀名是否合法
        if (suffix.equals("docx") || suffix.equals("doc")) {
            try {
                Map<String, Object> map = BeanUtils.convert(teacherService.getTeacherById(id));
                //填充成功，则返回文件的访问路径
                String result = fileUtil.fillWord(fill, map,response).replaceAll("\\\\", "/");
                return JsonUtils.toJson(Response.ok(result));
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return JsonUtils.toJson(Response.fail("填充失败"));
        } else {
            return JsonUtils.toJson(Response.fail("请上传以docx或doc为后缀名的word文件"));
        }
    }


    @GetMapping("/testfile")
    public void testFile(HttpServletResponse response) {

        try {
            response.setContentType("application/octet-stream");
            // 强制下载文件，不打开
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + "wang.docx");

            FileInputStream in = new FileInputStream("D:/D/fill.docx");
            byte[] bytes = new byte[2048];
            int n = -1;
            ServletOutputStream out = response.getOutputStream();
            while ((n = in.read(bytes, 0, bytes.length)) != -1) {
                out.write(bytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
