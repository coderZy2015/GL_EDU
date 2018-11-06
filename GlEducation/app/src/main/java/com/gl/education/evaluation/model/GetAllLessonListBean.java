package com.gl.education.evaluation.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zy on 2018/10/17.
 */

public class GetAllLessonListBean implements Serializable{

    /**
     * result : 1000
     * message : 操作成功
     * data : [{"id":1871,"title":"做负责的人","description":"","lesson_live_activity_id":140,
     * "type":1,"section":2,"subject":210,"grade":8,"version":1,"part":1,"chapter":11226,
     * "area_id":13,"live_start_time":1540359600,"score_end_time":0,"user_id":0,
     * "teacher_id":45926,"emcee_id":35485,"manage_id":"35990","jury_ids":"45945,45946,45947,
     * 45948,45949,45950,45951,45952,45953,45954,45955,45956,45957,45958",
     * "guanmo_jury_ids":"34923,36258","score_table_id":2057,
     * "score_table_name":"2018年初中道德与法治网评活动评课标准","score_rule_id":2023,"score_rule_name":"默认评分规则",
     * "status":0,"create_time":"2018-10-08 20:25:44","liveroom_id":"613163356",
     * "resource_id":7874,"pic_url":"/Public/lesson_live_pic/group_1/2/10.jpg",
     * "no_start_pic_url":null,"score_result_status":0,"vhall_stream_push_url":"","is_show":1,
     * "is_open":0,"push_lesson_live_id":0,"push_lesson_live_area_id":0,"plug_type":1,
     * "vcloud_http_pull_url":"http://flvaa188c25.live.126.net/live
     * /9f4c08e250214278a53f81c5a55547c8.flv?netease=flvaa188c25.live.126.net",
     * "vcloud_hls_pull_url":"http://pullhlsaa188c25.live.126.net/live
     * /9f4c08e250214278a53f81c5a55547c8/playlist.m3u8",
     * "vcloud_rtmp_pull_url":"rtmp://vaa188c25.live.126.net/live
     * /9f4c08e250214278a53f81c5a55547c8","vcloud_title":"140_11226_1539001543",
     * "vcloud_push_url":"rtmp://paa188c25.live.126.net/live/9f4c08e250214278a53f81c5a55547c8
     * ?wsSecret=fc31d68a9b163cb137ecb0eede47cd65&wsTime=1539001543",
     * "vcloud_liveroom_id":"9f4c08e250214278a53f81c5a55547c8","group_id":1,"use_video":0,
     * "teacher_name":"18初中法治石家庄3","school_name":"石家庄市","job_name":"教师","subject_name":"道德与法治",
     * "grade_name":"八年级","url":"","course_design":"1,2,3","course_files":"4,5,6"}]
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

    public static class DataBean  implements Serializable{
        /**
         * id : 1871
         * title : 做负责的人
         * description :
         * lesson_live_activity_id : 140
         * type : 1
         * section : 2
         * subject : 210
         * grade : 8
         * version : 1
         * part : 1
         * chapter : 11226
         * area_id : 13
         * live_start_time : 1540359600
         * score_end_time : 0
         * user_id : 0
         * teacher_id : 45926
         * emcee_id : 35485
         * manage_id : 35990
         * jury_ids : 45945,45946,45947,45948,45949,45950,45951,45952,45953,45954,45955,45956,
         * 45957,45958
         * guanmo_jury_ids : 34923,36258
         * score_table_id : 2057
         * score_table_name : 2018年初中道德与法治网评活动评课标准
         * score_rule_id : 2023
         * score_rule_name : 默认评分规则
         * status : 0
         * create_time : 2018-10-08 20:25:44
         * liveroom_id : 613163356
         * resource_id : 7874
         * pic_url : /Public/lesson_live_pic/group_1/2/10.jpg
         * no_start_pic_url : null
         * score_result_status : 0
         * vhall_stream_push_url :
         * is_show : 1
         * is_open : 0
         * push_lesson_live_id : 0
         * push_lesson_live_area_id : 0
         * plug_type : 1
         * vcloud_http_pull_url :
         * http://flvaa188c25.live.126.net/live/9f4c08e250214278a53f81c5a55547c8.flv?netease
         * =flvaa188c25.live.126.net
         * vcloud_hls_pull_url :
         * http://pullhlsaa188c25.live.126.net/live/9f4c08e250214278a53f81c5a55547c8/playlist.m3u8
         * vcloud_rtmp_pull_url :
         * rtmp://vaa188c25.live.126.net/live/9f4c08e250214278a53f81c5a55547c8
         * vcloud_title : 140_11226_1539001543
         * vcloud_push_url : rtmp://paa188c25.live.126.net/live/9f4c08e250214278a53f81c5a55547c8
         * ?wsSecret=fc31d68a9b163cb137ecb0eede47cd65&wsTime=1539001543
         * vcloud_liveroom_id : 9f4c08e250214278a53f81c5a55547c8
         * group_id : 1
         * use_video : 0
         * teacher_name : 18初中法治石家庄3
         * school_name : 石家庄市
         * job_name : 教师
         * subject_name : 道德与法治
         * grade_name : 八年级
         * url :
         * course_design : 1,2,3
         * course_files : 4,5,6
         */

        private int id;
        private String title;
        private String description;
        private int lesson_live_activity_id;
        private int type;
        private int section;
        private int subject;
        private int grade;
        private int version;
        private int part;
        private int chapter;
        private int area_id;
        private int live_start_time;
        private int score_end_time;
        private int user_id;
        private int teacher_id;
        private int emcee_id;
        private String manage_id;
        private String jury_ids;
        private String guanmo_jury_ids;
        private int score_table_id;
        private String score_table_name;
        private int score_rule_id;
        private String score_rule_name;
        private int status;
        private String create_time;
        private String liveroom_id;
        private int resource_id;
        private String pic_url;
        private Object no_start_pic_url;
        private int score_result_status;
        private String vhall_stream_push_url;
        private int is_show;
        private int is_open;
        private int push_lesson_live_id;
        private int push_lesson_live_area_id;
        private int plug_type;
        private String vcloud_http_pull_url;
        private String vcloud_hls_pull_url;
        private String vcloud_rtmp_pull_url;
        private String vcloud_title;
        private String vcloud_push_url;
        private String vcloud_liveroom_id;
        private int group_id;
        private int use_video;
        private String teacher_name;
        private String school_name;
        private String job_name;
        private String subject_name;
        private String grade_name;
        private String url;
        private String course_design;
        private String course_files;
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

        public int getLesson_live_activity_id() {
            return lesson_live_activity_id;
        }

        public void setLesson_live_activity_id(int lesson_live_activity_id) {
            this.lesson_live_activity_id = lesson_live_activity_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSection() {
            return section;
        }

        public void setSection(int section) {
            this.section = section;
        }

        public int getSubject() {
            return subject;
        }

        public void setSubject(int subject) {
            this.subject = subject;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getPart() {
            return part;
        }

        public void setPart(int part) {
            this.part = part;
        }

        public int getChapter() {
            return chapter;
        }

        public void setChapter(int chapter) {
            this.chapter = chapter;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public int getLive_start_time() {
            return live_start_time;
        }

        public void setLive_start_time(int live_start_time) {
            this.live_start_time = live_start_time;
        }

        public int getScore_end_time() {
            return score_end_time;
        }

        public void setScore_end_time(int score_end_time) {
            this.score_end_time = score_end_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getLiveroom_id() {
            return liveroom_id;
        }

        public void setLiveroom_id(String liveroom_id) {
            this.liveroom_id = liveroom_id;
        }

        public int getResource_id() {
            return resource_id;
        }

        public void setResource_id(int resource_id) {
            this.resource_id = resource_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public Object getNo_start_pic_url() {
            return no_start_pic_url;
        }

        public void setNo_start_pic_url(Object no_start_pic_url) {
            this.no_start_pic_url = no_start_pic_url;
        }

        public int getScore_result_status() {
            return score_result_status;
        }

        public void setScore_result_status(int score_result_status) {
            this.score_result_status = score_result_status;
        }

        public String getVhall_stream_push_url() {
            return vhall_stream_push_url;
        }

        public void setVhall_stream_push_url(String vhall_stream_push_url) {
            this.vhall_stream_push_url = vhall_stream_push_url;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public int getIs_open() {
            return is_open;
        }

        public void setIs_open(int is_open) {
            this.is_open = is_open;
        }

        public int getPush_lesson_live_id() {
            return push_lesson_live_id;
        }

        public void setPush_lesson_live_id(int push_lesson_live_id) {
            this.push_lesson_live_id = push_lesson_live_id;
        }

        public int getPush_lesson_live_area_id() {
            return push_lesson_live_area_id;
        }

        public void setPush_lesson_live_area_id(int push_lesson_live_area_id) {
            this.push_lesson_live_area_id = push_lesson_live_area_id;
        }

        public int getPlug_type() {
            return plug_type;
        }

        public void setPlug_type(int plug_type) {
            this.plug_type = plug_type;
        }

        public String getVcloud_http_pull_url() {
            return vcloud_http_pull_url;
        }

        public void setVcloud_http_pull_url(String vcloud_http_pull_url) {
            this.vcloud_http_pull_url = vcloud_http_pull_url;
        }

        public String getVcloud_hls_pull_url() {
            return vcloud_hls_pull_url;
        }

        public void setVcloud_hls_pull_url(String vcloud_hls_pull_url) {
            this.vcloud_hls_pull_url = vcloud_hls_pull_url;
        }

        public String getVcloud_rtmp_pull_url() {
            return vcloud_rtmp_pull_url;
        }

        public void setVcloud_rtmp_pull_url(String vcloud_rtmp_pull_url) {
            this.vcloud_rtmp_pull_url = vcloud_rtmp_pull_url;
        }

        public String getVcloud_title() {
            return vcloud_title;
        }

        public void setVcloud_title(String vcloud_title) {
            this.vcloud_title = vcloud_title;
        }

        public String getVcloud_push_url() {
            return vcloud_push_url;
        }

        public void setVcloud_push_url(String vcloud_push_url) {
            this.vcloud_push_url = vcloud_push_url;
        }

        public String getVcloud_liveroom_id() {
            return vcloud_liveroom_id;
        }

        public void setVcloud_liveroom_id(String vcloud_liveroom_id) {
            this.vcloud_liveroom_id = vcloud_liveroom_id;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getUse_video() {
            return use_video;
        }

        public void setUse_video(int use_video) {
            this.use_video = use_video;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCourse_design() {
            return course_design;
        }

        public void setCourse_design(String course_design) {
            this.course_design = course_design;
        }

        public String getCourse_files() {
            return course_files;
        }

        public void setCourse_files(String course_files) {
            this.course_files = course_files;
        }
    }
}
