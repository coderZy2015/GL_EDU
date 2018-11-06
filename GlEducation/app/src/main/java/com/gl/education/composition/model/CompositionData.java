package com.gl.education.composition.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2018/10/21.
 */

public class CompositionData {
    public String title = "";   //标题内容
    public String tImage = "";  //要求图片url 可以为空
    public List<String> cImage = new ArrayList<>();  //内容图片url 数组
    public String teacherName = ""; //老师名称
    public String teacherID = "";   //老师rs_guid
    public String rawType = "";    // 修改类型 0 评语 1 详批
    public List<File> upFile = new ArrayList<>();  //向作文及时批上传递
    public void clear(){
        title = "";
        tImage = "";
        cImage.clear();
        teacherName = "";
        teacherID = "";
        rawType = "";
        upFile.clear();
    }
}
