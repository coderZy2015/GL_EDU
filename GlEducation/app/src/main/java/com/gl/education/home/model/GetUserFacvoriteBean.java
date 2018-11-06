package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/30.
 */

public class GetUserFacvoriteBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"total_count":2,"list":[{"channel_itemid":295,"time":1538869667,
     * "title":"今日格言｜在今天和明天之间，有一段很长的时期，趁你还有精神的时候，学习迅速地办事。","type":1,"img":["http://gl-appres
     * .oss-cn-qingdao.aliyuncs.com/adminres/imageres/7d28f2682c78aa1c69fadda15360c391.png"],
     * "origin":"中国好老师","readNum":1599,"likes":1685},{"channel_itemid":328,"time":1539142474,
     * "title":"家教故事分享|家长不要习惯于给孩子收拾残局","type":1,"img":["http://gl-appres.oss-cn-qingdao.aliyuncs
     * .com/adminres/imageres/185d2aa827c879f7ce998b65228cc8d7.png"],"origin":"信天翁看教育",
     * "readNum":1484,"likes":1056}]}
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
         * total_count : 2
         * list : [{"channel_itemid":295,"time":1538869667,
         * "title":"今日格言｜在今天和明天之间，有一段很长的时期，趁你还有精神的时候，学习迅速地办事。","type":1,"img":["http://gl-appres
         * .oss-cn-qingdao.aliyuncs.com/adminres/imageres/7d28f2682c78aa1c69fadda15360c391.png"],
         * "origin":"中国好老师","readNum":1599,"likes":1685},{"channel_itemid":328,"time":1539142474,
         * "title":"家教故事分享|家长不要习惯于给孩子收拾残局","type":1,"img":["http://gl-appres.oss-cn-qingdao
         * .aliyuncs.com/adminres/imageres/185d2aa827c879f7ce998b65228cc8d7.png"],
         * "origin":"信天翁看教育","readNum":1484,"likes":1056}]
         */

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
             * channel_itemid : 295
             * time : 1538869667
             * title : 今日格言｜在今天和明天之间，有一段很长的时期，趁你还有精神的时候，学习迅速地办事。
             * type : 1
             * img : ["http://gl-appres.oss-cn-qingdao.aliyuncs
             * .com/adminres/imageres/7d28f2682c78aa1c69fadda15360c391.png"]
             * origin : 中国好老师
             * readNum : 1599
             * likes : 1685
             */

            private int channel_itemid;
            private int time;
            private String title;
            private int type;
            private String origin;
            private int readNum;
            private int likes;
            private List<String> img;

            public int getChannel_itemid() {
                return channel_itemid;
            }

            public void setChannel_itemid(int channel_itemid) {
                this.channel_itemid = channel_itemid;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public int getReadNum() {
                return readNum;
            }

            public void setReadNum(int readNum) {
                this.readNum = readNum;
            }

            public int getLikes() {
                return likes;
            }

            public void setLikes(int likes) {
                this.likes = likes;
            }

            public List<String> getImg() {
                return img;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }
        }
    }
}
