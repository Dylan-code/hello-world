package com.marx.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TempFile {
    @Id
    private Integer id;

    private String fileName;

    public TempFile(Integer id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "TempFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    public TempFile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
