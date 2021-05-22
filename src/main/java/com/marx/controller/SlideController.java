package com.marx.controller;

import com.marx.entity.Slide;
import com.marx.service.SlideService;
import com.marx.common.Result;
import com.marx.vo.PageInfo;
import com.wobangkj.api.Response;
import com.wobangkj.bean.Pager;
import com.wobangkj.bean.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@CrossOrigin
public class SlideController {

    @Autowired
    private SlideService slideService;

    /**
     * 获取所有的轮播图信息
     * */
    @GetMapping("/slide")
    @ResponseBody
    public Res allSlide(Slide slide, PageInfo pageInfo, HttpServletResponse response){
        Pager<Slide> result = slideService.allSlide(slide,pageInfo.toCon());
        return Response.ok(result);
    }

    /**
     * 添加轮播图信息
     * */
    @PostMapping("/slide")
    @ResponseBody
    public Res addSlide(@RequestBody Slide slide){
        Integer i = slideService.addSlide(slide);
        return i > 0 ? Response.ok("添加成功") : Response.fail("添加失败");
    }

    /**
     * 修改轮播图信息
     * */
    @PutMapping("/slide")
    @ResponseBody
    public Res modSlide(@RequestBody Slide slide){
        Integer i = slideService.modSlide(slide);
        return i > 0 ? Response.ok("修改成功") : Response.fail("修改失败");
    }

    /**
     * 根据轮播图Id删除对应的轮播图信息
     * */
    @DeleteMapping("/slide/{id}")
    @ResponseBody
    public Res deleteSlide(@PathVariable String id){
        Integer i = slideService.deleteSlide(id);
        return i > 0 ? Response.ok("删除成功") : Response.fail("删除失败");
    }

}
