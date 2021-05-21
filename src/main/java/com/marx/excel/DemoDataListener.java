package com.marx.excel;
/*
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.marx.dao.UserMapper;
import com.marx.entity.Teacher;
import com.marx.entity.User;
import com.marx.service.TeacherService;
import com.wobangkj.api.SyncReadSimpleListener;
import com.wobangkj.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DemoDataListener extends SyncReadSimpleListener<Teacher> {

    @Autowired
    private TeacherService teacherService;

    protected static transient int maxSize = 2;

    public static void setMaxSize(int maxSize) {
        DemoDataListener.maxSize = maxSize;
    }


    @Override
    protected boolean filter(Teacher data) {

        if (data.getName() == null || data.getName().equals("") ) return false;

        return super.filter(data);
    }



    @Override
    protected int getMax() {
        return maxSize;
    }

    @Override
    protected void before() {
        super.before();
    }

    @Override
    protected void after() {
        super.after();
    }

    @Override
    protected void doProcess() {
        for (Teacher teacher : cache) {
            teacherService.insert(teacher);
        }
    }
}
*/