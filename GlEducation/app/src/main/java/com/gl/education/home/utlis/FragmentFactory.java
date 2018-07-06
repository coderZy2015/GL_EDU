package com.gl.education.home.utlis;

import com.gl.education.home.base.BaseFragment;
import com.gl.education.home.fragment.CompostionFragment;
import com.gl.education.home.fragment.PhoneticBeautyFragment;
import com.gl.education.home.fragment.RecommendFragment;
import com.gl.education.home.fragment.SmallClassFragment;
import com.gl.education.home.fragment.TeachingAssistantFragment;
import com.gl.education.home.fragment.TeachingMaterialFragment;
import com.gl.education.home.fragment.VedioFragment;

import java.util.HashMap;

/**
 * Created by zy on 2018/6/11.
 */

public class FragmentFactory {

    public static final int FRAG_RECOMMEND = 0;    //推荐
    public static final int FRAG_TEACHING_MATERIAL = 1;    //教材
    public static final int FRAG_TEACHING_ASSISTANT = 2;    //教辅
    public static final int FRAG_SMALL_CLASS = 3;    //微课
    public static final int FRAG_PHONETIC_BEAUTY = 4;    //音体美
    public static final int FRAG_VIDEO = 5;    //视频
    public static final int FRAG_COMPOSITION = 6;    //作文及时批


    private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<Integer, 
            BaseFragment>();

    public static BaseFragment getFragment(Integer pos) {
        BaseFragment baseFragment = mBaseFragments.get(pos);

        if (baseFragment == null) {
            switch (pos) {
                case FRAG_RECOMMEND:
                    baseFragment = RecommendFragment.newInstance();//推荐
                    break;
                case FRAG_TEACHING_MATERIAL:
                    baseFragment = TeachingMaterialFragment.newInstance();//教材
                    break;
                case FRAG_TEACHING_ASSISTANT:
                    baseFragment = TeachingAssistantFragment.newInstance();//教辅
                    break;
                case FRAG_SMALL_CLASS:
                    baseFragment = SmallClassFragment.newInstance();//微课
                    break;
                case FRAG_PHONETIC_BEAUTY:
                    baseFragment = PhoneticBeautyFragment.newInstance();//音体美
                    break;
                case FRAG_VIDEO:
                    baseFragment = VedioFragment.newInstance();//视频
                    break;
                case FRAG_COMPOSITION:
                    baseFragment = CompostionFragment.newInstance();//作文及时批
                    break;

            }
            mBaseFragments.put(pos, baseFragment);
        }
        return baseFragment;
    }

    public static BaseFragment getSelectFragment(int id){
        BaseFragment baseFragment = mBaseFragments.get(id);
        return baseFragment;
    }

    public static String getTabName(int pos) {
        String name = "";
        switch (pos) {
            case FRAG_RECOMMEND:
                name = "推荐";
                break;
            case FRAG_TEACHING_MATERIAL:
                name = "教材";
                break;
            case FRAG_TEACHING_ASSISTANT:
                name = "教辅";
                break;
            case FRAG_SMALL_CLASS:
                name = "微课";
                break;
            case FRAG_PHONETIC_BEAUTY:
                name = "音体美";
                break;
            case FRAG_VIDEO:
                name = "视频";
                break;
            case FRAG_COMPOSITION:
                name = "作文及时批";
                break;
            default:
                break;
        }

        return name;
    }
}
