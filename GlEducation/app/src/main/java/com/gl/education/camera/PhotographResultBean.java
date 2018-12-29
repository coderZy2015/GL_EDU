package com.gl.education.camera;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zy on 2018/6/27.
 */

public class PhotographResultBean implements Serializable {


    /**
     * result : 1000
     * message : 操作成功
     * data : [{"ques_id":397953,"ques_body":["/upload/2018-01-23/30003944-ae48-46ad-81b5
     * -a75ac49cbb60/paper.files/image35.jpeg"],"ques_answer_txt":null,
     * "ques_answer_pic":["/upload/2018-01-23/30003944-ae48-46ad-81b5-a75ac49cbb60/paper
     * .files/image36.jpeg"],"video_num":"","catalog_id":53059}]
     */

    private int result;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * ques_id : 397953
         * ques_body : ["/upload/2018-01-23/30003944-ae48-46ad-81b5-a75ac49cbb60/paper
         * .files/image35.jpeg"]
         * ques_answer_txt : null
         * ques_answer_pic : ["/upload/2018-01-23/30003944-ae48-46ad-81b5-a75ac49cbb60/paper
         * .files/image36.jpeg"]
         * video_num :
         * catalog_id : 53059
         */

        private int ques_id;
        private Object ques_answer_txt;
        private String video_num;
        private String catalog_id;
        private List<String> ques_body;
        private List<String> ques_answer_pic;

        public int getQues_id() {
            return ques_id;
        }

        public void setQues_id(int ques_id) {
            this.ques_id = ques_id;
        }

        public Object getQues_answer_txt() {
            return ques_answer_txt;
        }

        public void setQues_answer_txt(Object ques_answer_txt) {
            this.ques_answer_txt = ques_answer_txt;
        }

        public String getVideo_num() {
            return video_num;
        }

        public void setVideo_num(String video_num) {
            this.video_num = video_num;
        }

        public String getCatalog_id() {
            return catalog_id;
        }

        public void setCatalog_id(String catalog_id) {
            this.catalog_id = catalog_id;
        }

        public List<String> getQues_body() {
            return ques_body;
        }

        public void setQues_body(List<String> ques_body) {
            this.ques_body = ques_body;
        }

        public List<String> getQues_answer_pic() {
            return ques_answer_pic;
        }

        public void setQues_answer_pic(List<String> ques_answer_pic) {
            this.ques_answer_pic = ques_answer_pic;
        }
    }
}
