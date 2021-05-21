package com.marx.service.impl;

import com.marx.dao.SlideMapper;
import com.marx.entity.Slide;
import com.marx.entity.Total;
import com.marx.service.SlideService;
import com.marx.util.FileUtil;
import com.marx.util.uuid.IdUtils;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import com.wobangkj.impl.TkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;


@Service
public class SlideServiceImpl extends TkServiceImpl<SlideMapper,Slide> implements SlideService {

    @Autowired
    private FileUtil fileUtil;

    /**
     * 根据Id获取信息
     * @param id 该条数据的Id
     * @return 查询到的信息
     * */
    @Override
    public Slide getById(String id) {
        return this.queryById(id);
    }

    /**
     * 获取所有的轮播图信息
     * @return 查询到的结果
     * */
    @Override
    public Pager<Slide> allSlide(Slide slide,Condition condition) {
        return this.getDao().queryAllPage(slide,condition);
    }

    /**
     * 添加一条轮播图信息
     * @param slide 要添加的轮播图信息
     * @return 影响的行数
     * */
    @Override
    @Transactional
    public Integer addSlide(Slide slide) {
        if (slide.getId() == null || slide.getId().equals("")){
            slide.setId(IdUtils.randomUUID());
        }
        /* 该条数据已经保存在数据库中，所以将该文件从临时文件表中删除，防止被定时任务删除 */
        fileUtil.deleteTempFileFromTable(slide.getImg());
        return this.getDao().insert(slide);
    }

    /**
     * 修改轮播图的信息
     * @param slide 新的轮播图信息
     * @return 影响的行数
     * */
    @Override
    @Transactional
    public Integer modSlide(Slide slide) {
        Slide result = getById(slide.getId());
        /* 如果新数据的文件信息和旧数据的文件信息相同，则说明没有修改添加的文件，反之则认为修改 */
        if(!slide.getImg().equals(result.getImg())){
            /* 如果不相等，则删除旧的保存在磁盘中的信息 */
            fileUtil.deleteDiskFile(result.getImg());
            /* 并把新的文件信息从临时文件库中删除 */
            fileUtil.deleteTempFileFromTable(slide.getImg());
        }
//        return slideMapper.modSlide(slide);
        return this.getDao().update(slide);
    }

    /**
     * 根据Id删除轮播图信息
     * @param id 要删除的轮播图Id
     * @return 影响的行数
     * */
    @Override
    @Transactional
    public Integer deleteSlide(String id) {
        Slide result = getById(id);
        fileUtil.deleteDiskFile(result.getImg());
//        return slideMapper.deleteSlide(id);
        return this.getDao().deleteById(id);
    }

}
