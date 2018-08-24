package com.gl.education.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zy on 2018/7/24.
 */

public class GetAllChannelBean implements Serializable {

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"id":1,"name":"推荐","url":"home/recommend"},{"id":2,"name":"教材",
     * "url":"home/jiaocai"},{"id":3,"name":"教辅","url":"home/jiaofu"},{"id":4,"name":"微课",
     * "url":"home/weike"},{"id":7,"name":"阅读与写作","url":"home/yueduxiezuo"}]
     */

    private int result;
    private String message;
    private List<ChannelEntity> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChannelEntity> getData() {
        return data;
    }

    public void setData(List<ChannelEntity> data) {
        this.data = data;
    }

}
