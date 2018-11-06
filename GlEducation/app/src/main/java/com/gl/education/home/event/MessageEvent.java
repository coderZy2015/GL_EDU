package com.gl.education.home.event;

/**
 * Created by zy on 2018/7/4.
 * 传递一个单纯的信息  登录event
 */

public class MessageEvent {

    String msg;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
