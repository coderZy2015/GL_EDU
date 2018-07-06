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

public class SearchISBNFragment extends Fragment {

    public static SearchISBNFragment newInstance() {
        Bundle args = new Bundle();

        SearchISBNFragment fragment = new SearchISBNFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_isbn, null);
        return view;
    }
}
