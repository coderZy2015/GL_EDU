package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/30.
 */

public class GetUserFacvoriteBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"channel_itemid":3,"time":1534570087,
     * "title":"2018年高考学霸调查出炉！31个省，60多名高分学生：除了不上补课班，他们最显著的共同点却是\u2026\u2026","type":1,
     * "img":["1.png"],"origin":"河北教育资源云平台","readNum":0}]
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
         * channel_itemid : 3
         * time : 1534570087
         * title : 2018年高考学霸调查出炉！31个省，60多名高分学生：除了不上补课班，他们最显著的共同点却是……
         * type : 1
         * img : ["1.png"]
         * origin : 河北教育资源云平台
         * readNum : 0
         */

        private int channel_itemid;
        private int time;
        private String title;
        private int type;
        private String origin;
        private int readNum;
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

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
