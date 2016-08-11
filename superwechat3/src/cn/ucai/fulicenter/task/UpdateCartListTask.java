package cn.ucai.fulicenter.task;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
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
public class UpdateCartListTask {
    private final static String TAG=UpdateCartListTask.class.getSimpleName();
    CartBean mCart;
    Context mcontext;

    public UpdateCartListTask(CartBean cart, Context mcontext) {
        this.mCart = cart;
        this.mcontext = mcontext;
    }
    public void execute(){
        final List<CartBean> cartList=FuliCenterApplication.getInstance().getCartList();
        if (cartList.contains(mCart)){
            if (mCart.getCount()>0){
                updateCart(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null && result.isSuccess()){
                            cartList.set(cartList.indexOf(mCart),mCart);
                            mcontext.sendStickyBroadcast(new Intent("update_cart_list"));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }else {
                delCart(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null && result.isSuccess()){
                            cartList.remove(cartList.indexOf(mCart));
                            mcontext.sendStickyBroadcast(new Intent("update_cart_list"));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }else {
            addCart(new OkHttpUtils2.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result!=null && result.isSuccess()){
                        mCart.setId(Integer.valueOf(result.getMsg()));
                        cartList.add(mCart);
                        mcontext.sendStickyBroadcast(new Intent("update_cart_list"));
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    public void updateCart(OkHttpUtils2.OnCompleteListener<MessageBean> listener){
        OkHttpUtils2<MessageBean> utils=new OkHttpUtils2<MessageBean>();
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,String.valueOf(mCart.getId()))
                .addParam(I.Cart.COUNT,String.valueOf(mCart.getCount()))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(mCart.isChecked()))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public void delCart(OkHttpUtils2.OnCompleteListener<MessageBean> listener){
        OkHttpUtils2<MessageBean> utils=new OkHttpUtils2<MessageBean>();
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,String.valueOf(mCart.getId()))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public void addCart(OkHttpUtils2.OnCompleteListener<MessageBean> listener){
        OkHttpUtils2<MessageBean> utils=new OkHttpUtils2<MessageBean>();
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID,String.valueOf(mCart.getGoods().getGoodsId()))
                .addParam(I.Cart.COUNT,String.valueOf(mCart.getCount()))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(mCart.isChecked()))
                .addParam(I.Cart.USER_NAME,FuliCenterApplication.getInstance().getUserName())
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
