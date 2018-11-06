package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/23.
 */

public class SearchResultBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"total_count":3,"list":[{"channel_itemid":3,"time":1534570087,
     * "title":"2018年高考学霸调查出炉！31个省，60多名高分学生：除了不上补课班，他们最显著的共同点却是\u2026\u2026","type":1,
     * "img":["http://a1guanlin.eugames.cn/adminUpload/image\\201808\\2018081816492740.png"],
     * "origin":"河北教育资源云平台","readNum":0},{"channel_itemid":5,"time":1534588240,
     * "title":"2018年高考改革试水释放了哪些信号？为中小学生提示了哪些学习、备考方向？","type":3,"img":["http://a1guanlin.eugames
     * .cn/adminUpload/image\\201808\\2018081818223217.png"],"origin":"河北教育资源云平台","readNum":0},
     * {"channel_itemid":13,"time":1534682980,
     * "title":"河北教育这次露脸了！2018高考自主招生500强出炉：石家庄二中、衡水一中占据前两名！邯郸一中、邢台一中、保定一中⋯⋯名列前茅","type":2,
     * "img":["http://a1guanlin.eugames.cn/adminUpload/image/201808/2018081918232526.jpg",
     * "http://a1guanlin.eugames.cn/adminUpload/image/201808/2018081918232852.jpg",
     * "http://a1guanlin.eugames.cn/adminUpload/image/201808/20180819182331100.jpg"],
     * "origin":"河北教育资源云平台","readNum":0}]}
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
         * total_count : 3
         * list : [{"channel_itemid":3,"time":1534570087,
         * "title":"2018年高考学霸调查出炉！31个省，60多名高分学生：除了不上补课班，他们最显著的共同点却是\u2026\u2026","type":1,
         * "img":["http://a1guanlin.eugames.cn/adminUpload/image\\201808\\2018081816492740.png"],
         * "origin":"河北教育资源云平台","readNum":0},{"channel_itemid":5,"time":1534588240,
         * "title":"2018年高考改革试水释放了哪些信号？为中小学生提示了哪些学习、备考方向？","type":3,"img":["http://a1guanlin
         * .eugames.cn/adminUpload/image\\201808\\2018081818223217.png"],"origin":"河北教育资源云平台",
         * "readNum":0},{"channel_itemid":13,"time":1534682980,
         * "title":"河北教育这次露脸了！2018高考自主招生500强出炉：石家庄二中、衡水一中占据前两名！邯郸一中、邢台一中、保定一中⋯⋯名列前茅","type":2,
         * "img":["http://a1guanlin.eugames.cn/adminUpload/image/201808/2018081918232526.jpg",
         * "http://a1guanlin.eugames.cn/adminUpload/image/201808/2018081918232852.jpg",
         * "http://a1guanlin.eugames.cn/adminUpload/image/201808/20180819182331100.jpg"],
         * "origin":"河北教育资源云平台","readNum":0}]
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
             * channel_itemid : 3
             * time : 1534570087
             * title : 2018年高考学霸调查出炉！31个省，60多名高分学生：除了不上补课班，他们最显著的共同点却是……
             * type : 1
             * img : ["http://a1guanlin.eugames.cn/adminUpload/image\\201808\\2018081816492740.png"]
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
}
