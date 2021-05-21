package com.marx.service.impl;

import com.marx.dao.CategoryMapper;
import com.marx.entity.Category;
import com.marx.service.CategoryService;
import com.wobangkj.impl.TkServiceImpl;
import com.wobangkj.uuid.DefaultUidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends TkServiceImpl<CategoryMapper, Category> implements CategoryService {



}
