package com.gl.education.composition.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zy on 2018/10/12.
 * 获得教师列表
 */

public class GetTeacherListBean implements Serializable{

    /**
     * result : 1000
     * message : 操作成功
     * data : {"total_count":1,"list":[{"xh":3344,
     * "rs_guid":"55303383-ccd3-4e64-8544-5f083c6052cc","position":"四星评阅人",
     * "description
     * ":"保定市乐凯中学英语教师，副校长，河北省骨干教师，河北省学科名师，河北省优秀教师，河北省中考命题组成员。冀教版英语教材编者，冀教版英语教材教师用书作者，冀教版英语教材骨干培训教师。曾获河北省优质课大赛一等奖，主持并参与多项国家、省、市级课题研究，多篇论文在核心期刊发表，具有丰富的教学经验。","score_level":"4","name":"王凌凌","picture_bigurl":"/cms/webgame/images/gameLogo/compTeacher/20150617043345.jpg","picture_littleurl":"/cms/webgame/images/gameLogo/compTeacher/20150617043345王凌凌22.jpg"}]}
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

    public static class DataBean implements Serializable{
        /**
         * total_count : 1
         * list : [{"xh":3344,"rs_guid":"55303383-ccd3-4e64-8544-5f083c6052cc",
         * "position":"四星评阅人",
         * "description":"保定市乐凯中学英语教师，副校长，河北省骨干教师，河北省学科名师，河北省优秀教师，河北省中考命题组成员。冀教版英语教材编者，冀教版英语教材教师用书作者，冀教版英语教材骨干培训教师。曾获河北省优质课大赛一等奖，主持并参与多项国家、省、市级课题研究，多篇论文在核心期刊发表，具有丰富的教学经验。","score_level":"4","name":"王凌凌","picture_bigurl":"/cms/webgame/images/gameLogo/compTeacher/20150617043345.jpg","picture_littleurl":"/cms/webgame/images/gameLogo/compTeacher/20150617043345王凌凌22.jpg"}]
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

        public static class ListBean implements Serializable {
            /**
             * xh : 3344
             * rs_guid : 55303383-ccd3-4e64-8544-5f083c6052cc
             * position : 四星评阅人
             * description :
             * 保定市乐凯中学英语教师，副校长，河北省骨干教师，河北省学科名师，河北省优秀教师，河北省中考命题组成员。冀教版英语教材编者，冀教版英语教材教师用书作者，冀教版英语教材骨干培训教师。曾获河北省优质课大赛一等奖，主持并参与多项国家、省、市级课题研究，多篇论文在核心期刊发表，具有丰富的教学经验。
             * score_level : 4
             * name : 王凌凌
             * picture_bigurl : /cms/webgame/images/gameLogo/compTeacher/20150617043345.jpg
             * picture_littleurl : /cms/webgame/images/gameLogo/compTeacher/20150617043345王凌凌22.jpg
             */

            private int xh;
            private String rs_guid;
            private String position;
            private String description;
            private String score_level;
            private String name;
            private String picture_bigurl;
            private String picture_littleurl;

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

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getScore_level() {
                return score_level;
            }

            public void setScore_level(String score_level) {
                this.score_level = score_level;
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
}
