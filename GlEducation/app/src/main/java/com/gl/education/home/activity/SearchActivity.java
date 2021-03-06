package com.gl.education.home.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gl.education.R;
import com.gl.education.app.AppConstant;
import com.gl.education.app.HomeAPI;
import com.gl.education.helper.JsonCallback;
import com.gl.education.home.adapter.SearchHistoryAdapter;
import com.gl.education.home.base.BaseActivity;
import com.gl.education.home.base.BasePresenter;
import com.gl.education.home.model.GetHotKeyBean;
import com.just.agentweb.AgentWeb;
import com.lzy.okgo.model.Response;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索的界面
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    RelativeLayout btn_back;

    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btn_search)
    EditText btn_search;

    @BindView(R.id.search)
    RelativeLayout search;

    protected AgentWeb mAgentWeb;

    private LayoutInflater mInflater;

    private SearchHistoryAdapter adapter;
    private List<String> hisList = new ArrayList<>();
    private int hisListNum = 0;

    private String searchStr = "";

    private List<String> mVals = new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected String setIdentifier() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        //获取热词列表
        HomeAPI.getHotKey(new JsonCallback<GetHotKeyBean>() {
            @Override
            public void onSuccess(Response<GetHotKeyBean> response) {
                if (response.body().getResult() == 1000) {
                    mVals = response.body().getData();
                    initFlowTablayout();
                }
            }
        });

        initHistory();

        btn_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode
                        () == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    searchStr = btn_search.getText().toString();
                    searchText(searchStr);
                    return true;
                }
                return false;
            }
        });
    }

    public void initFlowTablayout() {

        mInflater = LayoutInflater.from(this);

        flowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s) {
                //将tv.xml文件填充到标签内.
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        flowlayout, false);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }

            //为标签设置预点击内容(就是一开始就处于点击状态的标签)
            @Override
            public boolean setSelected(int position, String s) {
                return false;
            }
        });

        // 为点击标签设置点击事件.
        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout
                    parent) {
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("name", mVals.get(position));
                startActivity(intent);
                return true;
            }
        });
    }

    public void initHistory() {
        hisListNum = SPUtils.getInstance().getInt(AppConstant.SP_SEARCH_HISTORY_NUM, 0);

        for (int i = 0; i < hisListNum; i++) {
            hisList.add(SPUtils.getInstance().getString(AppConstant.SP_SEARCH_HISTORY + i));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //适配器参数：item布局、列表数据源
        adapter = new SearchHistoryAdapter(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setData(hisList);

        adapter.setItemListener(new SearchHistoryAdapter.onRecyclerItemClickerListener() {
            @Override
            public void onRecyclerItemClick(View view, String data, int position) {
                searchStr = hisList.get(position);
                searchText(searchStr);
            }
        });

    }

    @OnClick(R.id.btn_back)
    public void btn_back() {
        onBackPressed();
        finish();
    }


    @OnClick(R.id.search)
    public void search() {
        searchStr = btn_search.getText().toString();
        searchText(searchStr);
    }

    public void searchText(String str) {
        searchStr = str;
        if (searchStr.equals("")) {
            ToastUtils.showShort("搜索词不能为空");
            return;
        }
        //检测重复
        boolean repeat = false;
        for (int i = 0; i < hisList.size(); i++) {
            String oldHis = hisList.get(i);
            if (oldHis.equals(searchStr)) {
                repeat = true;
                LogUtils.d("搜索词重复");
            }
        }

        if (hisListNum >= 5) {
            if (repeat){
                hisList.remove(searchStr);
            }else {
                hisList.remove(4);
            }
            hisList.add(0, searchStr);

            for (int i = 0; i < 5; i++) {
                SPUtils.getInstance().put(AppConstant.SP_SEARCH_HISTORY + i, hisList.get(i));
            }
            adapter.setData(hisList);
        }else{
            if (repeat){
                hisList.remove(searchStr);
            }else{
                hisListNum = hisListNum + 1;
            }
            SPUtils.getInstance().put(AppConstant.SP_SEARCH_HISTORY_NUM, hisListNum);

            hisList.add(0, searchStr);
            for (int i=0; i<hisListNum; i++){
                SPUtils.getInstance().put(AppConstant.SP_SEARCH_HISTORY + i, hisList.get(i));
            }
            adapter.setData(hisList);
        }

        Intent intent = new Intent();
        intent.setClass(this, SearchResultActivity.class);
        intent.putExtra("name", searchStr);
        startActivity(intent);
    }

}
