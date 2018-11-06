package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/28.
 */

public class GetUserMsgBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"id":3,"title":"系统公告","content":"系统将于今晚24：00维护","is_read":false},{"id":2,
     * "title":"通知","content":"您的评论已审批通过2","is_read":false}]
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

    public static class DataBean {
        /**
         * id : 3
         * title : 系统公告
         * content : 系统将于今晚24：00维护
         * is_read : false
         */

        private int id;
        private String title;
        private String content;
        private boolean is_read;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public boolean isIs_read() {
            return is_read;
        }

        public void setIs_read(boolean is_read) {
            this.is_read = is_read;
        }
    }
}
