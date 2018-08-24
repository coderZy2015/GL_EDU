package com.gl.education.home.view;

import com.gl.education.home.model.ChannelEntity;

import java.util.List;

/**
 * Created by zy on 2018/7/26.
 */

public interface ChannelView {

    void getChannelDataSuccess(List<ChannelEntity> mList, List<ChannelEntity>oList);
    void getChannelDataErroer();
}
