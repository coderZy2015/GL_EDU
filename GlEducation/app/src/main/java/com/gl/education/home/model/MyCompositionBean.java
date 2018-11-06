package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/10/22.
 */

public class MyCompositionBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"total_count":1,"list":[{"id":4,"title":"高考",
     * "url":"/upload/APP/201810/20181012144510378.jpg;/upload/APP/201810/20181012144510550.jpg",
     * "type":1,"teacher_name":"王玉华","create_time":"2018-10-12 14:45:08","is_check":0,
     * "comment":null,"comment_url":null,"comment_time":null}]}
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

        private int total_count;
        private List<ListBean> list;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 4
             * title : 高考
             * url : /upload/APP/201810/20181012144510378.jpg;
             * /upload/APP/201810/20181012144510550.jpg
             * type : 1
             * teacher_name : 王玉华
             * create_time : 2018-10-12 14:45:08
             * is_check : 0
             * comment : null
             * comment_url : null
             * comment_time : null
             */

            private int id;
            private String title;
            private String url;
            private int type;
            private String teacher_name;
            private String create_time;
            private int is_check;
            private String comment;
            private String comment_url;
            private String comment_time;

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTeacher_name() {
                return teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getIs_check() {
                return is_check;
            }

            public void setIs_check(int is_check) {
                this.is_check = is_check;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getComment_url() {
                return comment_url;
            }

            public void setComment_url(String comment_url) {
                this.comment_url = comment_url;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }
        }
    }
}
