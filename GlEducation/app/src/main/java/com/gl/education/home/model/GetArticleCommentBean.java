package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/30.
 */

public class GetArticleCommentBean {


    /**
     * result : 1000
     * message : 操作成功
     * data : {"channel_itemid":"2496","channel_itemType":"0","total_amount":1,
     * "comments":[{"id":2822,"item_id":"2496","item_type":0,
     * "content":"人事有代谢，往来成古今。这一句很熟悉，但原来不知道是哪首诗里的，学习了！",
     * "aid":"2da443dd-e346-11e8-8028-6c92bf3ab703","create_time":"2019-01-11 06:58:19",
     * "username":"体育老师","avatar":null,"create_time_intVal":1547161099}]}
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
         * channel_itemid : 2496
         * channel_itemType : 0
         * total_amount : 1
         * comments : [{"id":2822,"item_id":"2496","item_type":0,
         * "content":"人事有代谢，往来成古今。这一句很熟悉，但原来不知道是哪首诗里的，学习了！",
         * "aid":"2da443dd-e346-11e8-8028-6c92bf3ab703","create_time":"2019-01-11 06:58:19",
         * "username":"体育老师","avatar":null,"create_time_intVal":1547161099}]
         */

        private String channel_itemid;
        private String channel_itemType;
        private int total_amount;
        private List<CommentsBean> comments;

        public String getChannel_itemid() {
            return channel_itemid;
        }

        public void setChannel_itemid(String channel_itemid) {
            this.channel_itemid = channel_itemid;
        }

        public String getChannel_itemType() {
            return channel_itemType;
        }

        public void setChannel_itemType(String channel_itemType) {
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
             * id : 2822
             * item_id : 2496
             * item_type : 0
             * content : 人事有代谢，往来成古今。这一句很熟悉，但原来不知道是哪首诗里的，学习了！
             * aid : 2da443dd-e346-11e8-8028-6c92bf3ab703
             * create_time : 2019-01-11 06:58:19
             * username : 体育老师
             * avatar : null
             * create_time_intVal : 1547161099
             */

            private int id;
            private String item_id;
            private int item_type;
            private String content;
            private String aid;
            private String create_time;
            private String username;
            private String avatar;
            private int create_time_intVal;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getCreate_time_intVal() {
                return create_time_intVal;
            }

            public void setCreate_time_intVal(int create_time_intVal) {
                this.create_time_intVal = create_time_intVal;
            }
        }
    }
}
