package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.GoodDetailsActivity;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.NewGoodBean;
import cn.ucai.fulicenter.task.UpdateCartListTask;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FootViewHolder;

/**
 * Created by Administrator on 2016/8/1.
 */
public class CartAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context mContext;
    List<CartBean> mGoodList;
    GoodViewHolder mGoodViewHolder;
    int sortBy;
    boolean isMore;

    public CartAdapter(Context context, List<CartBean> list){
        mContext=context;
        mGoodList=new ArrayList<CartBean>();
        mGoodList.addAll(list);
        sortBy=I.SORT_BY_ADDTIME_DESC;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(mContext);
        ViewHolder holder= new GoodViewHolder(inflate.inflate(R.layout.item_cart,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof GoodViewHolder){
            mGoodViewHolder= (GoodViewHolder) holder;
            final CartBean cart=mGoodList.get(position);
            ImageUtils.setGoodThumb(mContext,mGoodViewHolder.ivCartThumb,cart.getGoods().getGoodsThumb());
            mGoodViewHolder.tvCartCount.setText("("+cart.getCount()+")");
            mGoodViewHolder.tvCartName.setText(cart.getGoods().getGoodsName());
            mGoodViewHolder.tvCartPrice.setText(cart.getGoods().getCurrencyPrice());
            mGoodViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    cart.setChecked(b);
                    new UpdateCartListTask(cart,mContext).execute();
                }
            });
            mGoodViewHolder.ivAddCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mGoodViewHolder.ivDelCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return I.TYPE_FOOTER;
        }else {
            return I.TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mGoodList!=null?mGoodList.size():0;
    }

    public void initData(List<CartBean> list) {
        if (mGoodList!=null){
            mGoodList.clear();
        }
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(ArrayList<CartBean> list) {
        mGoodList.addAll(list);
        notifyDataSetChanged();
    }

    class GoodViewHolder extends ViewHolder{
        LinearLayout layout;
        ImageView ivCartThumb,ivDelCount,ivAddCount;
        TextView tvCartName;
        TextView tvCartPrice;
        TextView tvCartCount;
        CheckBox mCheckBox;
        public GoodViewHolder(View itemView) {
            super(itemView);
            mCheckBox= (CheckBox) itemView.findViewById(R.id.cb_cart_selected);
            tvCartCount= (TextView) itemView.findViewById(R.id.tv_cart_num);
            ivCartThumb= (ImageView) itemView.findViewById(R.id.iv_cart_thumb);
            tvCartName= (TextView) itemView.findViewById(R.id.tv_cart_good_name);
            tvCartPrice= (TextView) itemView.findViewById(R.id.tv_cart_price);
            ivAddCount= (ImageView) itemView.findViewById(R.id.iv_cart_add);
            ivDelCount= (ImageView) itemView.findViewById(R.id.iv_cart_del);
        }
    }

}
