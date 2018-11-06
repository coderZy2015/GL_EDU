package com.gl.education.composition.model;

import java.util.List;

/**
 * Created by zy on 2018/10/20.
 */

public class GetListByCatalogBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"total":12,"list":[{"xh":3367,"rs_guid":"54532dff-f99a-45ac-ac8c-3be38cc13173",
     * "article_title":"一、偏离题意","creatime":"2015-08-05 09:01:30"},{"xh":3368,
     * "rs_guid":"54532dff-f99a-45ac-ac8c-3be38cc13173","article_title":"二、切入角度僵化",
     * "creatime":"2015-08-05 09:01:30"}]}
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
         * total : 12
         * list : [{"xh":3367,"rs_guid":"54532dff-f99a-45ac-ac8c-3be38cc13173",
         * "article_title":"一、偏离题意","creatime":"2015-08-05 09:01:30"},{"xh":3368,
         * "rs_guid":"54532dff-f99a-45ac-ac8c-3be38cc13173","article_title":"二、切入角度僵化",
         * "creatime":"2015-08-05 09:01:30"}]
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * xh : 3367
             * rs_guid : 54532dff-f99a-45ac-ac8c-3be38cc13173
             * article_title : 一、偏离题意
             * creatime : 2015-08-05 09:01:30
             */

            private int xh;
            private String rs_guid;
            private String article_title;
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

            public String getCreatime() {
                return creatime;
            }

            public void setCreatime(String creatime) {
                this.creatime = creatime;
            }
        }
    }
}
