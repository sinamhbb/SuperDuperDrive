package com.udacity.jwdnd.course1.cloudstorage.model;


import java.io.InputStream;

public class File {

    private Integer fileId;
    private String filename;
    private String contenttype;
    private Long filesize;
    private Integer userid;
    private InputStream filedata;
    

    public File(Integer fileId, String filename, String contenttype, Long filesize, Integer userid,  InputStream filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public InputStream getFiledata() {
        return filedata;
    }

    public void setFiledata(InputStream filedata) {
        this.filedata = filedata;
    }
}
