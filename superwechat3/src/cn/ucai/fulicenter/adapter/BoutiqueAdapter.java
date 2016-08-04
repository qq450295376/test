package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.activity.BoutiqueDetailsActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageUtils;
import cn.ucai.fulicenter.view.FootViewHolder;

/**
 * Created by Administrator on 2016/8/1.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context mContext;
    List<BoutiqueBean> mBoutiqueList;
    BoutiqueViewHolder mBoutiqueViewHolder;
    FootViewHolder mFooterViewHolder;
    boolean isMore;
    String footerString;
    public BoutiqueAdapter(Context context, List<BoutiqueBean> list){
        mContext=context;
        mBoutiqueList=new ArrayList<BoutiqueBean>();
        mBoutiqueList.addAll(list);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public void setFooterString(String footerString) {
        this.footerString = footerString;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(mContext);
        ViewHolder holder=null;
        switch (viewType){
            case I.TYPE_FOOTER:
                holder=new FootViewHolder(inflate.inflate(R.layout.item_footer,parent,false));
                break;
            case I.TYPE_ITEM:
                holder=new BoutiqueViewHolder(inflate.inflate(R.layout.item_boutique,parent,false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof BoutiqueViewHolder){
            mBoutiqueViewHolder= (BoutiqueViewHolder) holder;
            final BoutiqueBean boutique=mBoutiqueList.get(position);
            ImageUtils.setGoodThumb(mContext,mBoutiqueViewHolder.ivBoutique,boutique.getImageurl());
            mBoutiqueViewHolder.tvBoutiqueTitle.setText(boutique.getTitle());
            mBoutiqueViewHolder.tvBoutiqueName.setText(boutique.getName());
            mBoutiqueViewHolder.tvBoutiqueDes.setText(boutique.getDescription());
            mBoutiqueViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, BoutiqueChildActivity.class)
                    .putExtra(D.Boutique.KEY_GOODS_ID,boutique.getId())
                    .putExtra(D.Boutique.KEY_NAME,boutique.getName()));

                }
            });
        }
        if (holder instanceof FootViewHolder){
            mFooterViewHolder= (FootViewHolder) holder;
            mFooterViewHolder.tvFooter.setText(footerString);
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
        return mBoutiqueList!=null?mBoutiqueList.size()+1:1;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mBoutiqueList!=null){
            mBoutiqueList.clear();
        }
        mBoutiqueList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(ArrayList<BoutiqueBean> list) {
        mBoutiqueList.addAll(list);
        notifyDataSetChanged();
    }

    class BoutiqueViewHolder extends ViewHolder{
        RelativeLayout layout;
        ImageView ivBoutique;
        TextView tvBoutiqueTitle;
        TextView tvBoutiqueName;
        TextView tvBoutiqueDes;
        public BoutiqueViewHolder(View itemView) {
            super(itemView);
            layout= (RelativeLayout) itemView.findViewById(R.id.layout_boutique_item);
            ivBoutique= (ImageView) itemView.findViewById(R.id.ivBoutiqueImg);
            tvBoutiqueTitle= (TextView) itemView.findViewById(R.id.tvBoutiqueTitle);
            tvBoutiqueName= (TextView) itemView.findViewById(R.id.tvBoutiqueName);
            tvBoutiqueDes= (TextView) itemView.findViewById(R.id.tvBoutiqueDescription);
        }
    }
//    private void soryByAddTime(){
//        Collections.sort(mGoodList, new Comparator<NewGoodBean>() {
//            @Override
//            public int compare(NewGoodBean goodLeft, NewGoodBean goodRight) {
//                return (int)(Long.valueOf(goodRight.getAddTime())-Long.valueOf(goodLeft.getAddTime()));
//            }
//        });
//    }
}
