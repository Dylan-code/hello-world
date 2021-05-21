package com.marx.service.impl;

import com.marx.dao.TotalMapper;
import com.marx.entity.Total;
import com.marx.service.TotalService;
import com.marx.util.FileUtil;
import com.marx.util.uuid.IdUtils;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import com.wobangkj.impl.TkServiceImpl;
import com.wobangkj.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class TotalServiceImpl extends TkServiceImpl<TotalMapper,Total> implements TotalService {
    @Autowired
    private FileUtil fileUtil;

    /**
     * 根据Id获取信息
     * @param id 该条数据的Id
     * @return 查询到的信息
     * */
    @Override
    public Total getById(String id) {
        return this.getDao().queryById(id);
    }

    /**
     * 根据某些条件查询信息
     * @param total 由查询条件组成的对象
     * @return 查询到的结果
     * */
    @Override
    public Pager<Total> queryAll(Total total, Condition condition) {
        return this.getDao().queryAllPage(total,condition);
    }


    /**
     * 添加数据
     * @param total 添加的数据的实体类
     * @return 影响的行数
     * */
    @Override
    @Transactional
    public Integer add(Total total) {
        //为该条信息加上UUId
        if (total.getId() == null || total.getId().equals("")){
            total.setId(IdUtils.randomUUID());
        }
        //为该信息加上当前的日期
        if (total.getDate() == null || total.getDate().equals("")){
            total.setDate(DateUtils.getNowTime());
        }
        /* 该条数据已经保存在数据库中，所以将该文件从临时文件表中删除，防止被定时任务删除 */
        fileUtil.deleteTempFileFromTable(total.getFileAddress());

        // 向数据库中插入信息
        return this.getDao().insert(total);
    }

    /**
     * 修改数据
     * @param total 新的数据
     * @return 影响的行数
     * */
    @Override
    @Transactional
    public Integer mod(Total total) {
        Total result = getById(total.getId());
        /* 如果新数据的文件信息和旧数据的文件信息相同，则说明没有修改添加的文件，反之则认为修改 */
        if(!total.getFileAddress().equals(result.getFileAddress())){
            /* 如果不相等，则删除旧的保存在磁盘中的信息 */
            fileUtil.deleteDiskFile(result.getFileAddress());
            /* 并把新的文件信息从临时文件库中删除 */
            fileUtil.deleteTempFileFromTable(total.getFileAddress());
        }
        //更新数据库中的信息
        return this.getDao().update(total);
    }

    /**
     * 根据Id删除一条数据
     * @param id 要删除的数据的Id
     * @return 影响的行数
     * */
    @Override
    @Transactional
    public Integer delete(String id) {
        //根据id获取信息，并将和该信息相关的其他信息都删除
        Total result = getById(id);
        fileUtil.deleteDiskFile(result.getFileAddress());
        return this.getDao().deleteById(id);
    }
}
