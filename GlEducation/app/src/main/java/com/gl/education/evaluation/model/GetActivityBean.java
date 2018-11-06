package com.gl.education.evaluation.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zy on 2018/10/15.
 */

public class GetActivityBean implements Serializable{

    /**
     * result : 1000
     * message : 操作成功
     * data : {"total_count":99,"list":[{"id":1,"area_id":13,"title":"2016年河北省初中数学优质课评选活动",
     * "description":"根据河北省教育科学研究所《关于开展2016年教育研究与评价项目活动的通知》（冀教所办〔2016〕5
     * 号）的通知精神，省教科所决定开展全省初中数学优质课网络评选活动。","contents":"12334455","user_id":0,"type":1,
     * "start_time":1477497600,"end_time":1477670399,"score_end_time":1477670399,
     * "create_time":"2016-10-19 22:48:31","pic_url":"/Public/Home/img/lesson_activity_default
     * .jpg","is_show":1,"score_result_status":0,"is_set_prize":0,"organizers":"河北省教科所",
     * "emcee_id":0,"manage_id":"35690","jury_ids":"35139,35140,35141,35142,35143,35144,35145,
     * 35146,35147,35148,35149,35150,35151","guanmo_jury_ids":"34923","score_table_id":88,
     * "score_table_name":"2016初中数学\u201c优质课\u201d评选量化评价参考表","score_rule_id":87,
     * "score_rule_name":"默认评分规则","is_sign":0,"allow_child_push":0,"allow_observer_enter":0,
     * "group_num":1,"manage_name":"缴志清"}]}
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
         * total_count : 99
         * list : [{"id":1,"area_id":13,"title":"2016年河北省初中数学优质课评选活动",
         * "description":"根据河北省教育科学研究所《关于开展2016年教育研究与评价项目活动的通知》（冀教所办〔2016〕5
         * 号）的通知精神，省教科所决定开展全省初中数学优质课网络评选活动。","contents":"12334455","user_id":0,"type":1,
         * "start_time":1477497600,"end_time":1477670399,"score_end_time":1477670399,
         * "create_time":"2016-10-19 22:48:31",
         * "pic_url":"/Public/Home/img/lesson_activity_default.jpg","is_show":1,
         * "score_result_status":0,"is_set_prize":0,"organizers":"河北省教科所","emcee_id":0,
         * "manage_id":"35690","jury_ids":"35139,35140,35141,35142,35143,35144,35145,35146,35147,
         * 35148,35149,35150,35151","guanmo_jury_ids":"34923","score_table_id":88,
         * "score_table_name":"2016初中数学\u201c优质课\u201d评选量化评价参考表","score_rule_id":87,
         * "score_rule_name":"默认评分规则","is_sign":0,"allow_child_push":0,"allow_observer_enter":0,
         * "group_num":1,"manage_name":"缴志清"}]
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

        public static class ListBean implements Serializable{
            /**
             * id : 1
             * area_id : 13
             * title : 2016年河北省初中数学优质课评选活动
             * description : 根据河北省教育科学研究所《关于开展2016年教育研究与评价项目活动的通知》（冀教所办〔2016〕5
             * 号）的通知精神，省教科所决定开展全省初中数学优质课网络评选活动。
             * contents : 12334455
             * user_id : 0
             * type : 1
             * start_time : 1477497600
             * end_time : 1477670399
             * score_end_time : 1477670399
             * create_time : 2016-10-19 22:48:31
             * pic_url : /Public/Home/img/lesson_activity_default.jpg
             * is_show : 1
             * score_result_status : 0
             * is_set_prize : 0
             * organizers : 河北省教科所
             * emcee_id : 0
             * manage_id : 35690
             * jury_ids : 35139,35140,35141,35142,35143,35144,35145,35146,35147,35148,35149,
             * 35150,35151
             * guanmo_jury_ids : 34923
             * score_table_id : 88
             * score_table_name : 2016初中数学“优质课”评选量化评价参考表
             * score_rule_id : 87
             * score_rule_name : 默认评分规则
             * is_sign : 0
             * allow_child_push : 0
             * allow_observer_enter : 0
             * group_num : 1
             * manage_name : 缴志清
             */

            private int id;
            private int area_id;
            private String title;
            private String description;
            private String contents;
            private int user_id;
            private int type;
            private int start_time;
            private int end_time;
            private int score_end_time;
            private String create_time;
            private String pic_url;
            private int is_show;
            private int score_result_status;
            private int is_set_prize;
            private String organizers;
            private int emcee_id;
            private String manage_id;
            private String jury_ids;
            private String guanmo_jury_ids;
            private int score_table_id;
            private String score_table_name;
            private int score_rule_id;
            private String score_rule_name;
            private int is_sign;
            private int allow_child_push;
            private int allow_observer_enter;
            private int group_num;
            private String manage_name;
            private int time_status;

            public int getTime_status() {
                return time_status;
            }

            public void setTime_status(int time_status) {
                this.time_status = time_status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getContents() {
                return contents;
            }

            public void setContents(String contents) {
                this.contents = contents;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStart_time() {
                return start_time;
            }

            public void setStart_time(int start_time) {
                this.start_time = start_time;
            }

            public int getEnd_time() {
                return end_time;
            }

            public void setEnd_time(int end_time) {
                this.end_time = end_time;
            }

            public int getScore_end_time() {
                return score_end_time;
            }

            public void setScore_end_time(int score_end_time) {
                this.score_end_time = score_end_time;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }

            public int getScore_result_status() {
                return score_result_status;
            }

            public void setScore_result_status(int score_result_status) {
                this.score_result_status = score_result_status;
            }

            public int getIs_set_prize() {
                return is_set_prize;
            }

            public void setIs_set_prize(int is_set_prize) {
                this.is_set_prize = is_set_prize;
            }

            public String getOrganizers() {
                return organizers;
            }

            public void setOrganizers(String organizers) {
                this.organizers = organizers;
            }

            public int getEmcee_id() {
                return emcee_id;
            }

            public void setEmcee_id(int emcee_id) {
                this.emcee_id = emcee_id;
            }

            public String getManage_id() {
                return manage_id;
            }

            public void setManage_id(String manage_id) {
                this.manage_id = manage_id;
            }

            public String getJury_ids() {
                return jury_ids;
            }

            public void setJury_ids(String jury_ids) {
                this.jury_ids = jury_ids;
            }

            public String getGuanmo_jury_ids() {
                return guanmo_jury_ids;
            }

            public void setGuanmo_jury_ids(String guanmo_jury_ids) {
                this.guanmo_jury_ids = guanmo_jury_ids;
            }

            public int getScore_table_id() {
                return score_table_id;
            }

            public void setScore_table_id(int score_table_id) {
                this.score_table_id = score_table_id;
            }

            public String getScore_table_name() {
                return score_table_name;
            }

            public void setScore_table_name(String score_table_name) {
                this.score_table_name = score_table_name;
            }

            public int getScore_rule_id() {
                return score_rule_id;
            }

            public void setScore_rule_id(int score_rule_id) {
                this.score_rule_id = score_rule_id;
            }

            public String getScore_rule_name() {
                return score_rule_name;
            }

            public void setScore_rule_name(String score_rule_name) {
                this.score_rule_name = score_rule_name;
            }

            public int getIs_sign() {
                return is_sign;
            }

            public void setIs_sign(int is_sign) {
                this.is_sign = is_sign;
            }

            public int getAllow_child_push() {
                return allow_child_push;
            }

            public void setAllow_child_push(int allow_child_push) {
                this.allow_child_push = allow_child_push;
            }

            public int getAllow_observer_enter() {
                return allow_observer_enter;
            }

            public void setAllow_observer_enter(int allow_observer_enter) {
                this.allow_observer_enter = allow_observer_enter;
            }

            public int getGroup_num() {
                return group_num;
            }

            public void setGroup_num(int group_num) {
                this.group_num = group_num;
            }

            public String getManage_name() {
                return manage_name;
            }

            public void setManage_name(String manage_name) {
                this.manage_name = manage_name;
            }

        }
    }
}
