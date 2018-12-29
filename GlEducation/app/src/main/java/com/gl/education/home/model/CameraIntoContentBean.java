package com.gl.education.home.model;

/**
 * Created by zy on 2018/12/4.
 */

public class CameraIntoContentBean {


    /**
     * result : 1000
     * message : 操作成功
     * data : {"isbuy":false,"catalogUrl":"http://appstuweb.hebeijiaoyu
     * .cn/#/jfinfo/detail?guid=eb3cf90c-77d9-4b50-a42c-d0c2dc256914&picurl=http://jiaofu-image
     * .oss-cn-qingdao.aliyuncs.com/upload/rsfilebig/20160205134933.jpg&price=7.5&column_ids='77
     * ','141','154','155'&grade_id=4&subject_id=2&name=同步训练语文四年级下册（2018版）",
     * "orderInfo":{"guid":"eb3cf90c-77d9-4b50-a42c-d0c2dc256914-457303","grade_id":102,"price":1}}
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
         * isbuy : false
         * catalogUrl : http://appstuweb.hebeijiaoyu
         * .cn/#/jfinfo/detail?guid=eb3cf90c-77d9-4b50-a42c-d0c2dc256914&picurl=http://jiaofu
         * -image.oss-cn-qingdao.aliyuncs
         * .com/upload/rsfilebig/20160205134933.jpg&price=7.5&column_ids='77','141','154',
         * '155'&grade_id=4&subject_id=2&name=同步训练语文四年级下册（2018版）
         * orderInfo : {"guid":"eb3cf90c-77d9-4b50-a42c-d0c2dc256914-457303","grade_id":102,
         * "price":1}
         */

        private boolean isbuy;
        private String catalogUrl;
        private OrderInfoBean orderInfo;

        public boolean isIsbuy() {
            return isbuy;
        }

        public void setIsbuy(boolean isbuy) {
            this.isbuy = isbuy;
        }

        public String getCatalogUrl() {
            return catalogUrl;
        }

        public void setCatalogUrl(String catalogUrl) {
            this.catalogUrl = catalogUrl;
        }

        public OrderInfoBean getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public static class OrderInfoBean {
            /**
             * guid : eb3cf90c-77d9-4b50-a42c-d0c2dc256914-457303
             * grade_id : 102
             * price : 1
             */

            private String guid;
            private int grade_id;
            private int price;

            public String getGuid() {
                return guid;
            }

            public void setGuid(String guid) {
                this.guid = guid;
            }

            public int getGrade_id() {
                return grade_id;
            }

            public void setGrade_id(int grade_id) {
                this.grade_id = grade_id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
