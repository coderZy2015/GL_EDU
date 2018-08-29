package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/8/28.
 */

public class GetOrderRecordBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"rechargeOrder":[{"content":"content","orderPrice":11000,"createtime":"2018-08-14
     * 15:40:09","orderType":"支付宝","rs_guid":"none"}],"buyOrder":[{"content":"小学同步课程包",
     * "orderPrice":-1000,"createtime":"2018-08-15 13:30:52","orderType":"扣除代币","rs_guid":"weike"}]}
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
        private List<RechargeOrderBean> rechargeOrder;
        private List<BuyOrderBean> buyOrder;

        public List<RechargeOrderBean> getRechargeOrder() {
            return rechargeOrder;
        }

        public void setRechargeOrder(List<RechargeOrderBean> rechargeOrder) {
            this.rechargeOrder = rechargeOrder;
        }

        public List<BuyOrderBean> getBuyOrder() {
            return buyOrder;
        }

        public void setBuyOrder(List<BuyOrderBean> buyOrder) {
            this.buyOrder = buyOrder;
        }

        public static class RechargeOrderBean {
            /**
             * content : content
             * orderPrice : 11000
             * createtime : 2018-08-14 15:40:09
             * orderType : 支付宝
             * rs_guid : none
             */

            private String content;
            private int orderPrice;
            private String createtime;
            private String orderType;
            private String rs_guid;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getOrderPrice() {
                return orderPrice;
            }

            public void setOrderPrice(int orderPrice) {
                this.orderPrice = orderPrice;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getRs_guid() {
                return rs_guid;
            }

            public void setRs_guid(String rs_guid) {
                this.rs_guid = rs_guid;
            }
        }

        public static class BuyOrderBean {
            /**
             * content : 小学同步课程包
             * orderPrice : -1000
             * createtime : 2018-08-15 13:30:52
             * orderType : 扣除代币
             * rs_guid : weike
             */

            private String content;
            private int orderPrice;
            private String createtime;
            private String orderType;
            private String rs_guid;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getOrderPrice() {
                return orderPrice;
            }

            public void setOrderPrice(int orderPrice) {
                this.orderPrice = orderPrice;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getRs_guid() {
                return rs_guid;
            }

            public void setRs_guid(String rs_guid) {
                this.rs_guid = rs_guid;
            }
        }
    }
}
