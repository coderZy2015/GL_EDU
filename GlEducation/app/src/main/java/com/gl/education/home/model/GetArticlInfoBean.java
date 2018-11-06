package com.gl.education.home.model;

/**
 * Created by zy on 2018/10/10.
 */

public class GetArticlInfoBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"visitors":1222,"likes":34,"commentsTotal":21}
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
         * visitors : 1222
         * likes : 34
         * commentsTotal : 21
         */

        private int visitors;
        private int likes;
        private int commentsTotal;

        public int getVisitors() {
            return visitors;
        }

        public void setVisitors(int visitors) {
            this.visitors = visitors;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getCommentsTotal() {
            return commentsTotal;
        }

        public void setCommentsTotal(int commentsTotal) {
            this.commentsTotal = commentsTotal;
        }
    }
}
