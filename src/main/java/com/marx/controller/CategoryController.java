package com.marx.controller;

import com.marx.common.Result;
import com.marx.entity.Category;
import com.marx.service.CategoryService;
import com.wobangkj.api.Respond;
import com.wobangkj.api.Response;
import com.wobangkj.bean.Res;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据类别信息（类别Id，父分类Id，类别名称）查询分类信息
     */
    @GetMapping("/category")
    @ResponseBody
    public Res getByParentId(Category category) {
        List<Category> result = categoryService.getDao().queryAll(category);
        return Response.ok(result);
    }

    /**
     * 添加一条类别信息
     */
    @PostMapping("/category")
    @ResponseBody
    public Res addCategory(@RequestBody Category category) {
        Category i = categoryService.insert(category);
        return i != null ? Response.ok("添加成功") : Response.fail("添加失败");
    }

    /**
     * 修改一条类别信息
     */
    @PutMapping("/category")
    @ResponseBody
    public Res modCategory(@RequestBody Category category) {
        Category update = categoryService.update(category);
        return Response.ok(update);
    }

    /**
     * 根据类别Id删除对用的类别
     */
    @DeleteMapping("/category/{id}")
    @ResponseBody
    public Res deleteCategory(@PathVariable String id) {
        boolean b = categoryService.deleteById(id);
        return b ? Response.ok() : Response.fail("删除失败");
    }
}
