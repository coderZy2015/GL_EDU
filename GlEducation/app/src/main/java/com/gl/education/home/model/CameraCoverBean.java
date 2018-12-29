package com.gl.education.home.model;

import java.util.List;

/**
 * Created by zy on 2018/12/24.
 */

public class CameraCoverBean {

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"id":30875936,"guid":"fb8addf6-b0d4-4882-be30-6275d9c23e49","name":"同步训练英语五年级下册",
     * "picture_bigurl":"/upload/rsfilebig/20180228121128同步训练英语五年级下册.jpg",
     * "picture_littleurl":"/upload/rsfilebig/20180228121128同步训练英语五年级下册.jpg"},{"id":12084,
     * "guid":"9ce2d3fa-42a2-4e99-9843-ab9ba0f6e25f","name":"同步训练英语五年级下册（2017版）",
     * "picture_bigurl":"/upload/rsfilebig/jfxc03080073.jpg",
     * "picture_littleurl":"/upload/rsfilebig/jfxc03080073.jpg"},{"id":12023,
     * "guid":"97fa885c-1444-4df6-b54a-d4b776c5622a","name":"同步训练语文五年级下册",
     * "picture_bigurl":"/upload/rsfilebig/jfxc03080013.jpg",
     * "picture_littleurl":"/upload/rsfilebig/jfxc03080013.jpg"}]
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
         * id : 30875936
         * guid : fb8addf6-b0d4-4882-be30-6275d9c23e49
         * name : 同步训练英语五年级下册
         * picture_bigurl : /upload/rsfilebig/20180228121128同步训练英语五年级下册.jpg
         * picture_littleurl : /upload/rsfilebig/20180228121128同步训练英语五年级下册.jpg
         */

        private int id;
        private String guid;
        private String name;
        private String picture_bigurl;
        private String picture_littleurl;
        private String pic_url;

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture_bigurl() {
            return picture_bigurl;
        }

        public void setPicture_bigurl(String picture_bigurl) {
            this.picture_bigurl = picture_bigurl;
        }

        public String getPicture_littleurl() {
            return picture_littleurl;
        }

        public void setPicture_littleurl(String picture_littleurl) {
            this.picture_littleurl = picture_littleurl;
        }
    }
}
