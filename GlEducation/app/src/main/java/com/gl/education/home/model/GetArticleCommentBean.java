package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/30.
 */

public class GetArticleCommentBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"channel_itemid":1,"channel_itemType":0,"total_amount":2,"comments":[{"id":2,
     * "item_id":1,"item_type":0,"content":"评论2","aid":"9aea87a0-866e-11e8-881d-7200073c8a50",
     * "create_time":"1970-01-01 08:00:00","username":"helloly"},{"id":1,"item_id":1,
     * "item_type":0,"content":"我是评论","aid":"9aea87a0-866e-11e8-881d-7200073c8a50",
     * "create_time":"1970-01-01 08:00:00","username":"helloly"}]}
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
         * channel_itemType : 0
         * total_amount : 2
         * comments : [{"id":2,"item_id":1,"item_type":0,"content":"评论2",
         * "aid":"9aea87a0-866e-11e8-881d-7200073c8a50","create_time":"1970-01-01 08:00:00",
         * "username":"helloly"},{"id":1,"item_id":1,"item_type":0,"content":"我是评论",
         * "aid":"9aea87a0-866e-11e8-881d-7200073c8a50","create_time":"1970-01-01 08:00:00",
         * "username":"helloly"}]
         */

        private int channel_itemid;
        private int channel_itemType;
        private int total_amount;
        private List<CommentsBean> comments;

        public int getChannel_itemid() {
            return channel_itemid;
        }

        public void setChannel_itemid(int channel_itemid) {
            this.channel_itemid = channel_itemid;
        }

        public int getChannel_itemType() {
            return channel_itemType;
        }

        public void setChannel_itemType(int channel_itemType) {
            this.channel_itemType = channel_itemType;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            /**
             * id : 2
             * item_id : 1
             * item_type : 0
             * content : 评论2
             * aid : 9aea87a0-866e-11e8-881d-7200073c8a50
             * create_time : 1970-01-01 08:00:00
             * username : helloly
             */

            private int id;
            private int item_id;
            private int item_type;
            private String content;
            private String aid;
            private String create_time;
            private String username;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getItem_id() {
                return item_id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public int getItem_type() {
                return item_type;
            }

            public void setItem_type(int item_type) {
                this.item_type = item_type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
