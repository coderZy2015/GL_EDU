package com.gl.education.home.model;

/**
 * Created by zy on 2018/8/17.
 * 天恒的JS标准样式
 */

public class JSOpenWebViewBean {
    private String url;
    private String title;
    private String isContent;
    private String param;       //区分是哪个栏目

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsContent() {
        return isContent;
    }

    public void setIsContent(String isContent) {
        this.isContent = isContent;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
