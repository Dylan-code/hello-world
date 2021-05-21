package com.marx.controller;

import com.marx.entity.Total;
import com.marx.service.TotalService;
import com.marx.vo.PageInfo;
import com.wobangkj.api.Response;
import com.wobangkj.bean.Pager;
import com.wobangkj.bean.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class TotalController {

    @Autowired
    private TotalService totalService;

    /**
     * 根据Id查询具体的信息
     */
    @GetMapping("/total/{id}")
    @ResponseBody
    public Res getTotalById(@PathVariable String id,PageInfo pageInfo) {
        Total total = totalService.queryById(id);
        return total != null ? Response.ok(total) : Response.fail("获取失败");
    }

    /**
     * 根据某些查询条件查询对应的信息，当所有查询条件都为空时，就是查询全部信息
     */
    @GetMapping("/total")
    @ResponseBody
    public Res getTotal(Total total, PageInfo pageInfo) {
        Pager<Total> totalResult = totalService.queryAll(total, pageInfo.toCon());
        return total != null ? Response.ok(totalResult) : Response.fail("获取失败");
    }

    /**
     * 向数据库中添加一条信息
     */
    @PostMapping("/add")
    @ResponseBody
    public Res add(@RequestBody Total total) {
        Integer i = totalService.add(total);
        return i > 0 ? Response.ok() : Response.fail("添加失败");
    }

    /**
     * 修改信息
     */
    @PutMapping("/mod")
    @ResponseBody
    public Res mod(@RequestBody Total total) {
        Integer i = totalService.mod(total);
        return i > 0 ? Response.ok("修改成功") : Response.fail("修改失败");
    }

    /**
     * 根据id删除对应的数据
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Res delete(@PathVariable String id) {
        Integer i = totalService.delete(id);
        return i > 0 ? Response.ok() : Response.fail("删除失败");
    }

}
