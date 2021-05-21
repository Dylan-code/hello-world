package com.marx.dao;

import com.marx.entity.JwtEntity;
import com.wobangkj.api.ITkMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface JwtMapper extends ITkMapper<JwtEntity> {
}
