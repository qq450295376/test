package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CollectAdapter;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.Utils;
import cn.ucai.fulicenter.view.DisPlayUtils;

public class CollectActivity extends Activity {
    CollectActivity mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutMananger;
    CollectAdapter mAdapter;
    List<CollectBean> mGoodlist;
    TextView tvhint;

    int pageId=1;
    int lastItemPosition;
    int action= I.ACTION_DOWNLOAD;
    int catId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        mGoodlist=new ArrayList<CollectBean>();
        mContext=this;
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        setPullDownRefreshListener();
        setPullUpRefreshListener();
    }

    private void setPullUpRefreshListener() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE && lastItemPosition==mAdapter.getItemCount()-1){
                    if (mAdapter.isMore()){
                        action=I.ACTION_PULL_UP;
                        pageId += I.PAGE_SIZE_DEFAULT;
                        initData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItemPosition=mGridLayoutMananger.findLastVisibleItemPosition();
            }
        });
    }
    //
    private void setPullDownRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                action=I.ACTION_PULL_DOWN;
                pageId = 0;
                mSwipeRefreshLayout.setRefreshing(true);
                tvhint.setVisibility(View.VISIBLE);
                initData();
            }
        });
    }

    private void initData() {
        String userName = FuliCenterApplication.getInstance().getUserName();
        if (userName.isEmpty()){finish();}
        findCollectList(new OkHttpUtils2.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                tvhint.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setMore(true);
                mAdapter.setFooterString(getResources().getString(R.string.load_more));
                if (result!=null){
                    ArrayList<CollectBean> goodBeanArrayList= Utils.array2List(result);
                    if (action==I.ACTION_DOWNLOAD || action==I.ACTION_PULL_DOWN){
                        mAdapter.initData(goodBeanArrayList);
                    }else {
                        mAdapter.addItem(goodBeanArrayList);
                    }
                    if (goodBeanArrayList.size()<I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                        mAdapter.setFooterString(getResources().getString(R.string.no_more));
                    }
                }else {
                    mAdapter.setMore(false);
                    mAdapter.setFooterString(getResources().getString(R.string.no_more));
                }
            }

            @Override
            public void onError(String error) {
                tvhint.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void findCollectList(OkHttpUtils2.OnCompleteListener<CollectBean[]> listener){
        OkHttpUtils2<CollectBean[]> utils=new OkHttpUtils2<CollectBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, FuliCenterApplication.getInstance().getUserName())
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    private void initView() {
        DisPlayUtils.initBackWithTitle(mContext,"收藏的宝贝");
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srl_collect);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_yellow,
                R.color.google_red,
                R.color.google_green
        );
        mRecyclerView= (RecyclerView)findViewById(R.id.rv_collect);
        mGridLayoutMananger=new GridLayoutManager(mContext, I.COLUM_NUM);
        mGridLayoutMananger.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutMananger);
        mAdapter=new CollectAdapter(mContext,mGoodlist);
        mRecyclerView.setAdapter(mAdapter);
        tvhint= (TextView) findViewById(R.id.tv_refresh_hint);

        updateCollectStatus();
    }
    class UpdateCollectStatus extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    }
    UpdateCollectStatus mReceiver;
    private void updateCollectStatus(){
        mReceiver=new UpdateCollectStatus();
        IntentFilter filter=new IntentFilter("update_collect_list");
        registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
