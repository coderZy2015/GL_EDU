package com.uuzuche.lib_zxing.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uuzuche.lib_zxing.R;

/**
 * Created by zy on 2018/6/19.
 */

public class SearchCoverFragment extends Fragment {

    public static SearchCoverFragment newInstance() {
        Bundle args = new Bundle();

        SearchCoverFragment fragment = new SearchCoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cover, null);
        return view;
    }
}
