package com.gl.education.home.event;

/**
 * Created by zy on 2018/8/16.
 */

public class ToMyBookShelfEvent {

    String message;

    String url;

    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
