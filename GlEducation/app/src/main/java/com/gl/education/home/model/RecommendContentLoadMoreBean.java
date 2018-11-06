package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/10/11.
 */

public class RecommendContentLoadMoreBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"channel_itemid":298,"time":1538871026,"title":"家教故事分享|孩子过分胆小是大人出了问题！","type":1,
     * "img":["http://gl-appres.oss-cn-qingdao.aliyuncs
     * .com/adminres/imageres/8fd35ab01d68b69549add48314da8795.png"],"origin":"信天翁看教育",
     * "readNum":1569,"likes":1015},{"channel_itemid":292,"time":1538785627,
     * "title":"家教故事分享|为什么孩子逃避，没有责任感","type":1,"img":["http://gl-appres.oss-cn-qingdao.aliyuncs
     * .com/adminres/imageres/e4705332bbf32fad5eacba45570bfae5.png"],"origin":"信天翁看教育",
     * "readNum":1554,"likes":1676},{"channel_itemid":286,"time":1538693994,
     * "title":"家教故事分享|让孩子做\u201c不听话\u201d的小朋友","type":1,"img":["http://gl-appres.oss-cn-qingdao
     * .aliyuncs.com/adminres/imageres/1815a7b85ceadbb4b4505aea3358229b.png"],"origin":"信天翁看教育",
     * "readNum":1575,"likes":1172},{"channel_itemid":267,"time":1538437949,
     * "title":"家教故事分享｜写信给孩子是交流的好办法","type":1,"img":["http://gl-appres.oss-cn-qingdao.aliyuncs
     * .com/adminres/imageres/031947611ac1b922d1628c334278fc88.png"],"origin":"中国好老师",
     * "readNum":1998,"likes":1688},{"channel_itemid":260,"time":1538350079,
     * "title":"家教故事分享｜别逼孩子一条道走到黑","type":1,"img":["http://gl-appres.oss-cn-qingdao.aliyuncs
     * .com/adminres/imageres/4db43be0e9b717e5363e9c5f1946f421.png"],"origin":"信天翁看教育",
     * "readNum":1271,"likes":1783}]
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
         * channel_itemid : 298
         * time : 1538871026
         * title : 家教故事分享|孩子过分胆小是大人出了问题！
         * type : 1
         * img : ["http://gl-appres.oss-cn-qingdao.aliyuncs
         * .com/adminres/imageres/8fd35ab01d68b69549add48314da8795.png"]
         * origin : 信天翁看教育
         * readNum : 1569
         * likes : 1015
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
