package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.Utils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class DownloadCartListTask {
    private final static String TAG=DownloadCartListTask.class.getSimpleName();
    String username;
    Context mcontext;

    public DownloadCartListTask(String username, Context mcontext) {
        this.username = username;
        this.mcontext = mcontext;
    }
    public void execute(){
        final OkHttpUtils2<CartBean[]> utils=new OkHttpUtils2<CartBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME,username)
                .addParam(I.PAGE_ID,String.valueOf(I.PAGE_ID_DEFAULT))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CartBean[].class)
                .execute(new OkHttpUtils2.OnCompleteListener<CartBean[]>() {
                    @Override
                    public void onSuccess(CartBean[] str) {
                        if (str!=null) {
                            ArrayList<CartBean> list = Utils.array2List(str);
                            Log.e("DownloadCart","listsize="+list.size());
                            List<CartBean> cartList=FuliCenterApplication.getInstance().getCartList() ;
                            for (final CartBean cart : list){
                                if (!cartList.contains(cart)){
                                    OkHttpUtils2<GoodDetailsBean> utils=new OkHttpUtils2<GoodDetailsBean>();
                                    utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                                            .addParam(D.GoodDetails.KEY_GOODS_ID,String.valueOf(cart.getGoodsId()))
                                            .targetClass(GoodDetailsBean.class)
                                            .execute(new OkHttpUtils2.OnCompleteListener<GoodDetailsBean>() {
                                                @Override
                                                public void onSuccess(GoodDetailsBean result) {
                                                    Log.e("DownloadCart","result="+result);
                                                    cart.setGoods(result);
                                                }

                                                @Override
                                                public void onError(String error) {

                                                }
                                            });
                                    cartList.add(cart);
                                }else {
                                    cartList.get(cartList.indexOf(cart)).setChecked(cart.isChecked());
                                    cartList.get(cartList.indexOf(cart)).setCount(cart.getCount());
                                }
                            }
                        }
                        mcontext.sendStickyBroadcast(new Intent("update_cart_list"));
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
