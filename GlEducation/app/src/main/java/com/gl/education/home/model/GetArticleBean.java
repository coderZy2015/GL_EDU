package com.gl.education.home.model;

/**
 * Created by zy on 2018/8/27.
 */

public class GetArticleBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"channel_itemid":1,"article_id":1,"contentHtml":"我是内容","author":"我是作者",
     * "editor":"我是编辑","visitors":0,"likes":0,"online_time":"时间戳"}
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
         * channel_itemid : 1
         * article_id : 1
         * contentHtml : 我是内容
         * author : 我是作者
         * editor : 我是编辑
         * visitors : 0
         * likes : 0
         * online_time : 时间戳
         */

        private int channel_itemid;
        private int article_id;
        private String contentHtml;
        private String author;
        private String editor;
        private int visitors;
        private int likes;
        private String online_time;

        public int getChannel_itemid() {
            return channel_itemid;
        }

        public void setChannel_itemid(int channel_itemid) {
            this.channel_itemid = channel_itemid;
        }

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public String getContentHtml() {
            return contentHtml;
        }

        public void setContentHtml(String contentHtml) {
            this.contentHtml = contentHtml;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

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

        public String getOnline_time() {
            return online_time;
        }

        public void setOnline_time(String online_time) {
            this.online_time = online_time;
        }
    }
}
