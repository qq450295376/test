package cn.ucai.fulicenter.activity;

import android.app.Activity;
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
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.adapter.GoodAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.Utils;
import cn.ucai.fulicenter.view.DisPlayUtils;

public class BoutiqueChildActivity extends Activity {
    BoutiqueChildActivity mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutMananger;
    GoodAdapter mAdapter;
    List<NewGoodBean> mGoodlist;
    TextView tvhint;

    int pageId=1;
    int lastItemPosition;
    int action= I.ACTION_DOWNLOAD;
    int catId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        mGoodlist=new ArrayList<NewGoodBean>();
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
        catId=getIntent().getIntExtra(D.Boutique.KEY_GOODS_ID,0);
        Log.e("main","catId="+catId);
        if (catId<0)finish();
        findNewGoodList(new OkHttpUtils2.OnCompleteListener<NewGoodBean[]>() {
            @Override
            public void onSuccess(NewGoodBean[] result) {
                tvhint.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setMore(true);
                mAdapter.setFooterString(getResources().getString(R.string.load_more));
                if (result!=null){
                    ArrayList<NewGoodBean> goodBeanArrayList= Utils.array2List(result);
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
    private void findNewGoodList(OkHttpUtils2.OnCompleteListener<NewGoodBean[]> listener){
        OkHttpUtils2<NewGoodBean[]> utils=new OkHttpUtils2<NewGoodBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGood.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodBean[].class)
                .execute(listener);
    }

    private void initView() {
        String name = getIntent().getStringExtra(D.Boutique.KEY_NAME);
        Log.e("main","name="+name);
        DisPlayUtils.initBackWithTitle(mContext,name);
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srl_child_boutique);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_yellow,
                R.color.google_red,
                R.color.google_green
        );
        mRecyclerView= (RecyclerView)findViewById(R.id.rv_child_boutique);
        mGridLayoutMananger=new GridLayoutManager(mContext, I.COLUM_NUM);
        mGridLayoutMananger.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutMananger);
        mAdapter=new GoodAdapter(mContext,mGoodlist);
        mRecyclerView.setAdapter(mAdapter);
        tvhint= (TextView) findViewById(R.id.tv_refresh_hint);
    }

}
