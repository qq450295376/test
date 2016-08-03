package cn.ucai.fulicenter.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {
    FuliCenterMainActivity mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    BoutiqueAdapter mAdapter;
    List<BoutiqueBean> mBoutiqueList;
    TextView tvhint;
    int action;
//    int pageId=1;
//    int lastItemPosition;

    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBoutiqueList=new ArrayList<BoutiqueBean>();
        mContext= (FuliCenterMainActivity) getContext();
        View layout=View.inflate(mContext,R.layout.fragment_boutique,null);
        initView(layout);
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullDownRefreshListener();
    }

    private void setPullDownRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                action=I.ACTION_PULL_DOWN;
                mSwipeRefreshLayout.setRefreshing(true);
                tvhint.setVisibility(View.VISIBLE);
                initData();
            }
        });
    }

    private void initData() {
       findNewBoutiqueList();
    }

    private void findNewBoutiqueList() {
        final OkHttpUtils2<BoutiqueBean[]> utils = new OkHttpUtils2<BoutiqueBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(new OkHttpUtils2.OnCompleteListener<BoutiqueBean[]>() {
                    @Override
                    public void onSuccess(BoutiqueBean[] result) {
                        tvhint.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("main","result="+result);
                        if (result!=null){
                            ArrayList<BoutiqueBean> goodBeanArrayList= Utils.array2List(result);
                            if (action==I.ACTION_PULL_DOWN){
                                mAdapter.initData(goodBeanArrayList);
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void initView(View layout) {
        mSwipeRefreshLayout= (SwipeRefreshLayout) layout.findViewById(R.id.srl_boutique);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_yellow,
                R.color.google_red,
                R.color.google_green
        );
        mRecyclerView= (RecyclerView) layout.findViewById(R.id.rv_boutique);
        mLinearLayoutManager=new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter=new BoutiqueAdapter(mContext,mBoutiqueList);
        mRecyclerView.setAdapter(mAdapter);
        tvhint= (TextView) layout.findViewById(R.id.tv_refresh_hint);
    }

}
