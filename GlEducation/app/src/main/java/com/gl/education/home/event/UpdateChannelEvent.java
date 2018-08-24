package com.gl.education.home.event;

import com.gl.education.home.model.ChannelEntity;

import java.util.List;

/**
 * Created by zy on 2018/8/10.
 * 更新频道信息的event
 */

public class UpdateChannelEvent {

    String msg;

    int selectChannel;

    public List<ChannelEntity> channelList;

    public List<ChannelEntity> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<ChannelEntity> channelList) {
        this.channelList = channelList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSelectChannel() {
        return selectChannel;
    }

    public void setSelectChannel(int selectChannel) {
        this.selectChannel = selectChannel;
    }
}
