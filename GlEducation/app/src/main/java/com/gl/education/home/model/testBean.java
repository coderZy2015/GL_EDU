package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/19.
 */

public class testBean {

    /**
     * code : 1
     * msg : 操作成功
     * data : [{"ques_id":184498,"video_num":" "}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ques_id : 184498
         * video_num :  
         */

        private int ques_id;
        private String video_num;

        public int getQues_id() {
            return ques_id;
        }

        public void setQues_id(int ques_id) {
            this.ques_id = ques_id;
        }

        public String getVideo_num() {
            return video_num;
        }

        public void setVideo_num(String video_num) {
            this.video_num = video_num;
        }
    }
}
