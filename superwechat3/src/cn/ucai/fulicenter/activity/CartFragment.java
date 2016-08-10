package cn.ucai.fulicenter.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    FuliCenterMainActivity mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    CartAdapter mAdapter;
    List<CartBean> mCartList;
    TextView tvhint;
    TextView tvSumPrice;
    TextView tvSavePrice;
    int action;
//    int pageId=1;
//    int lastItemPosition;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCartList=new ArrayList<CartBean>();
        mContext= (FuliCenterMainActivity) getContext();
        View layout=View.inflate(mContext,R.layout.fragment_cart,null);
        initView(layout);
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullDownRefreshListener();
        updateCartStatus();
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
        List<CartBean> cartList=FuliCenterApplication.getInstance().getCartList();
        mCartList.clear();
        mCartList.addAll(cartList);
        mSwipeRefreshLayout.setRefreshing(false);
        tvhint.setVisibility(View.GONE);
        mAdapter.setMore(true);
        Log.e("main","result="+mCartList);
        if (cartList!=null){
            if (action==I.ACTION_PULL_DOWN){
                mAdapter.initData(mCartList);
            }
        }
        getSumPrice();
    }



    private void initView(View layout) {
        mSwipeRefreshLayout= (SwipeRefreshLayout) layout.findViewById(R.id.srl_cart);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_yellow,
                R.color.google_red,
                R.color.google_green
        );
        mRecyclerView= (RecyclerView) layout.findViewById(R.id.rv_cart);
        mLinearLayoutManager=new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter=new CartAdapter(mContext,mCartList);
        mRecyclerView.setAdapter(mAdapter);
        tvhint= (TextView) layout.findViewById(R.id.tv_refresh_hint);
        tvSavePrice= (TextView) layout.findViewById(R.id.tv_cart_save_price);
        tvSumPrice= (TextView) layout.findViewById(R.id.tv_cart_sum_price);
    }
    class UpdateCartStatus extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    }
    UpdateCartStatus mReceiver;
    public void updateCartStatus(){
        mReceiver=new UpdateCartStatus();
        IntentFilter filter=new IntentFilter("update_cart_list");
        filter.addAction("update_user");
        mContext.registerReceiver(mReceiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver!=null){
            mContext.unregisterReceiver(mReceiver);
        }
    }
    private void getSumPrice(){
        if (mCartList!=null && mCartList.size()>0){
            int sumPrice=0;
            int rankPrice=0;
            for (CartBean cart:mCartList){
                GoodDetailsBean good=cart.getGoods();
                if (good!=null && cart.isChecked()){
                    sumPrice+=converPrice(good.getCurrencyPrice())*cart.getCount();
                    rankPrice+=converPrice(good.getRankPrice())*cart.getCount();
                }
            }
            tvSumPrice.setText("合计：¥"+sumPrice);
            tvSavePrice.setText("节省：¥"+(sumPrice-rankPrice));
        }else {
            tvSumPrice.setText("合计：¥"+0);
            tvSavePrice.setText("节省：¥"+0);
        }
    }
    private int converPrice(String price){
        price=price.substring(1);
        return Integer.valueOf(price);
    }
}
