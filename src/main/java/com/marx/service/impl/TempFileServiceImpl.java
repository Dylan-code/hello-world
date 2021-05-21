package com.marx.service.impl;

import com.marx.dao.TempFileMapper;
import com.marx.entity.TempFile;
import com.marx.service.TempFileService;
import com.wobangkj.impl.TkServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempFileServiceImpl extends TkServiceImpl<TempFileMapper, TempFile> implements TempFileService {


}
