package com.gl.education.home.event;

/**
 * Created by zy on 2018/8/24.
 */

public class TransmitPersonDataEvent {
    String url;
    String nickName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
