package com.gl.education.home.model;

import java.io.Serializable;

/**
 * Created by zy on 2018/11/15.
 */

public class CouponDataBean implements Serializable {
    String url;
    int pic_url;
    String title;
    String content;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPic_url() {
        return pic_url;
    }

    public void setPic_url(int pic_url) {
        this.pic_url = pic_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
