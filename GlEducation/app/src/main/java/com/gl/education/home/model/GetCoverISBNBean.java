package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/12/25.
 */

public class GetCoverISBNBean {


    /**
     * result : 1000
     * message : 操作成功
     * data : [{"guid":"9ce2d3fa-42a2-4e99-9843-ab9ba0f6e25f","isbn":"9787202071199","isbns":"",
     * "type":"jiaofu"}]
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
         * guid : 9ce2d3fa-42a2-4e99-9843-ab9ba0f6e25f
         * isbn : 9787202071199
         * isbns :
         * type : jiaofu
         */

        private String guid;
        private String isbn;
        private String isbns;
        private String type;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getIsbns() {
            return isbns;
        }

        public void setIsbns(String isbns) {
            this.isbns = isbns;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
