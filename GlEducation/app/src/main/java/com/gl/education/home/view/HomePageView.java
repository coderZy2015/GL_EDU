package com.gl.education.home.view;

import com.gl.education.home.model.ChannelEntity;

import java.util.List;

/**
 * Created by zy on 2018/6/6.
 */

public interface HomePageView {
    void getChannelGradeSuccess(int grade, List<ChannelEntity> mFragShowIdList);
    void getChannelGradeFail();
}
