package com.marx.dao;

import com.marx.entity.Slide;
import com.wobangkj.api.ITkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SlideMapper extends ITkMapper<Slide> {

}
