package com.marx.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix = "marx")
@Getter
@Setter
@ToString
public class MarxConfig {
    /**
     * 项目名称
     * */
    private String name;

    /**
     * 版本
     * */
    private String version;

    /**
     * 文件对外暴露的地址
     * */
    private String staticAccessPath;

    /**
     * 文件保存在磁盘中的地址
     * */
    private String uploadFolder;


    /**
     * 获取jar包运行所在的目录
     * linux和windows下通用
     * */
    public String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(this.getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
//        return System.getProperty("user.dir");
    }


}
