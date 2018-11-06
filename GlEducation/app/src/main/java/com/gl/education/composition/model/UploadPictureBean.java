package com.gl.education.composition.model;

/**
 * Created by zy on 2018/10/21.
 */

public class UploadPictureBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : {"path":"clientrw/e34901769b86332f81ebb8e07b936624.png"}
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
         * path : clientrw/e34901769b86332f81ebb8e07b936624.png
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
