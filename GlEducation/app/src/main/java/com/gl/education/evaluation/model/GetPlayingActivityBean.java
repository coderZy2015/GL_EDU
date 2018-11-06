package com.gl.education.evaluation.model;

import java.util.List;

/**
 * Created by zy on 2018/10/17.
 */

public class GetPlayingActivityBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"id":1,"activity_id":1,"url":"www.baidu.com","description":"我是描述"}]
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
         * id : 1
         * activity_id : 1
         * url : www.baidu.com
         * description : 我是描述
         */

        private int id;
        private int activity_id;
        private String title;
        private String url;
        private String description;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
