package com.gl.education.composition.model;

import java.util.List;

/**
 * Created by zy on 2018/10/20.
 */

public class JSGetDetailBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"xh":3367,"rs_guid":"54532dff-f99a-45ac-ac8c-3be38cc13173",
     * "article_title":"一、偏离题意","context":"html格式的内容","creatime":"2015-08-05 09:01:30"}]
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
         * xh : 3367
         * rs_guid : 54532dff-f99a-45ac-ac8c-3be38cc13173
         * article_title : 一、偏离题意
         * context : html格式的内容
         * creatime : 2015-08-05 09:01:30
         */

        private int xh;
        private String rs_guid;
        private String article_title;
        private String context;
        private String creatime;

        public int getXh() {
            return xh;
        }

        public void setXh(int xh) {
            this.xh = xh;
        }

        public String getRs_guid() {
            return rs_guid;
        }

        public void setRs_guid(String rs_guid) {
            this.rs_guid = rs_guid;
        }

        public String getArticle_title() {
            return article_title;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getCreatime() {
            return creatime;
        }

        public void setCreatime(String creatime) {
            this.creatime = creatime;
        }
    }
}
