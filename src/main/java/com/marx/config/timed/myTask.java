package com.marx.config.timed;


import com.marx.entity.TempFile;
import com.marx.util.FileUtil;
import com.marx.util.IocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


public class myTask implements Runnable {

    private FileUtil fileUtils;

    public myTask() {
        this.fileUtils = IocUtils.getBean(FileUtil.class);
    }

    @Override
    public void run() {
        /* 获取所有临时文件路径信息，然后一个一个删除 */
        List<TempFile> tempFiles = fileUtils.allTempFile();
        /* 将所有保存在磁盘中的临时文件删除 */
        fileUtils.deleteDiskTempFile(tempFiles);
        /* 当所有临时文件从磁盘中删除后，路径信息也从数据库中删除 */
        fileUtils.deleteTempFile();
    }
}
