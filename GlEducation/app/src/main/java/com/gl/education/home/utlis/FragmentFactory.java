package com.gl.education.home.utlis;

import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.fragment.CompositionFragment;
import com.gl.education.home.fragment.RecommendFragment;
import com.gl.education.home.fragment.SmallClassFragment;
import com.gl.education.home.fragment.TeachingAssistantFragment;
import com.gl.education.home.fragment.TeachingMaterialFragment;
import com.gl.education.home.model.ChannelEntity;

import java.util.HashMap;

/**
 * Created by zy on 2018/6/11.
 */

public class FragmentFactory {

    public static final int FRAG_RECOMMEND = 1;    //推荐
    public static final int FRAG_TEACHING_MATERIAL = 2;    //教材
    public static final int FRAG_TEACHING_ASSISTANT = 3;    //教辅
    public static final int FRAG_SMALL_CLASS = 4;    //微课
    public static final int FRAG_COMPOSITION = 5;    //阅读与写作


    private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<Integer, 
            BaseFragment>();

    public static BaseFragment getFragment(ChannelEntity entity) {
        //BaseFragment baseFragment = mBaseFragments.get(pos);
        BaseFragment baseFragment = null;
        int position = (int)entity.getId();
        switch (position) {
            case FRAG_RECOMMEND:
                baseFragment = RecommendFragment.newInstance(entity);//推荐
                break;
            case FRAG_TEACHING_MATERIAL:
                baseFragment = TeachingMaterialFragment.newInstance(entity);//教材
                break;
            case FRAG_TEACHING_ASSISTANT:
                baseFragment = TeachingAssistantFragment.newInstance(entity);//教辅
                break;
            case FRAG_SMALL_CLASS:
                baseFragment = SmallClassFragment.newInstance(entity);//微课
                break;
            case FRAG_COMPOSITION:
                baseFragment = CompositionFragment.newInstance(entity);//阅读与写作
                break;
        }
        return baseFragment;
    }

}
