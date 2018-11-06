package com.gl.education.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zy on 2018/7/24.
 */

public class GetUserChannelGradeBean implements Serializable {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"grade":4,"channel_data":[{"id":1,"name":"推荐","url":"home/recommend"},{"id":2,
     * "name":"教材","url":"home/jiaocai"}]}
     */

    private int result;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * grade : 4
         * channel_data : [{"id":1,"name":"推荐","url":"home/recommend"},{"id":2,"name":"教材",
         * "url":"home/jiaocai"}]
         */

        private int grade;
        private List<ChannelEntity> channel_data;

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public List<ChannelEntity> getChannel_data() {
            return channel_data;
        }

        public void setChannel_data(List<ChannelEntity> channel_data) {
            this.channel_data = channel_data;
        }

    }
}
