package com.gl.education.camera.activity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zy on 2018/6/27.
 */

public class PhotographResultBean implements Serializable {

    /**
     * result : 1000
     * message : 成功！
     * data : [{"ques_id":36614,"ques_body":["/upload/2017-08-25/7e7d479f-4f24-48d7-9be9
     * -e03baea025f4/paper.files/34.jpg"],"ques_answer_txt":" C ","ques_answer_pic":[]},
     * {"ques_id":41816,"ques_body":["/upload/2017-08-26/4ec10dc1-4093-4ac0-b011-a5cef5ebdcb1
     * /paper.files/22.jpg","/upload/2017-08-26/4ec10dc1-4093-4ac0-b011-a5cef5ebdcb1/paper
     * .files/23.jpg","/upload/2017-08-26/4ec10dc1-4093-4ac0-b011-a5cef5ebdcb1/paper
     * .files/24.jpg","/upload/2017-08-26/4ec10dc1-4093-4ac0-b011-a5cef5ebdcb1/paper
     * .files/25.jpg","/upload/2017-08-26/4ec10dc1-4093-4ac0-b011-a5cef5ebdcb1/paper
     * .files/26.jpg","/upload/2017-08-26/4ec10dc1-4093-4ac0-b011-a5cef5ebdcb1/paper
     * .files/27.jpg"],"ques_answer_txt":null,
     * "ques_answer_pic":["/upload/2017-08-26/4ec10dc1-4093-4ac0-b011-a5cef5ebdcb1/paper
     * .files/28.jpg"]},{"ques_id":42048,
     * "ques_body":["/upload/2017-08-26/9ca505a9-8479-4fb6-8dd3-ab5fb7f0785d/paper
     * .files/30.jpg"],"ques_answer_txt":" C ","ques_answer_pic":[]},{"ques_id":60091,
     * "ques_body":["/upload/2017-08-14/3eebbe5d-0dc7-45a5-b483-4465af7b03d9/paper
     * .files/12.jpg"],"ques_answer_txt":null,
     * "ques_answer_pic":["/upload/2017-08-14/3eebbe5d-0dc7-45a5-b483-4465af7b03d9/paper
     * .files/13.jpg"]},{"ques_id":61816,
     * "ques_body":["/upload/2017-08-03/b87eb35d-8b3e-4129-9550-2387827bf508/paper.files/2.jpg"],
     * "ques_answer_txt":null,"ques_answer_pic":["/upload/2017-08-03/b87eb35d-8b3e-4129-9550
     * -2387827bf508/paper.files/3.jpg"]}]
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
         * ques_id : 36614
         * ques_body : ["/upload/2017-08-25/7e7d479f-4f24-48d7-9be9-e03baea025f4/paper
         * .files/34.jpg"]
         * ques_answer_txt :  C
         * ques_answer_pic : []
         */

        private int ques_id;
        private String ques_answer_txt;
        private List<String> ques_body;
        private List<String> ques_answer_pic;

        public int getQues_id() {
            return ques_id;
        }

        public void setQues_id(int ques_id) {
            this.ques_id = ques_id;
        }

        public String getQues_answer_txt() {
            return ques_answer_txt;
        }

        public void setQues_answer_txt(String ques_answer_txt) {
            this.ques_answer_txt = ques_answer_txt;
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
