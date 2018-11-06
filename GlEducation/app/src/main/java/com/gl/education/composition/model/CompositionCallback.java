package com.gl.education.composition.model;

import java.io.Serializable;

/**
 * Created by zy on 2018/10/21.
 */

public class CompositionCallback implements Serializable{

    /**
     * code : 1
     * res :
     * size : 0
     * filePath :
     */

    private String code;
    private String res;
    private String size;
    private String filePath;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
