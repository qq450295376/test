package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CategoryChildActivity;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.ImageUtils;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<CategoryGroupBean> mGroupList;
    List<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context mContext,
                           List<CategoryGroupBean> groupList,
                           List<ArrayList<CategoryChildBean>> childList) {
        this.mContext = mContext;
        this.mGroupList = new ArrayList<CategoryGroupBean>();
        mGroupList.addAll(groupList);
        this.mChildList = new ArrayList<ArrayList<CategoryChildBean>>();
        mChildList.addAll(childList);
        Log.i("main", "11111");
    }


    @Override
    public int getGroupCount() {
        Log.i("main", "222");
        return mGroupList!=null?mGroupList.size():0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.i("main", "333");
        return mChildList.get(groupPosition).size();
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        Log.i("main", "444");
        if (mGroupList!=null)return mGroupList.get(groupPosition);
        return null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        Log.i("main", "555");
        if (mChildList.get(groupPosition)!=null
                && mChildList.get(groupPosition).get(childPosition)!=null)
            return mChildList.get(groupPosition).get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int i) {
        Log.i("main", "666");
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Log.e("CategoryAdapter","mGrouplist="+mGroupList);
        GroupViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(mContext,R.layout.item_category_group, null);
            holder=new GroupViewHolder();
            holder.ivGroupThumb= (ImageView) convertView.findViewById(R.id.iv_group_thumb);
            holder.ivIndicator= (ImageView) convertView.findViewById(R.id.iv_indicator);
            holder.tvGroupName= (TextView) convertView.findViewById(R.id.tv_group_name);
            convertView.setTag(holder);
        }
        else {
            holder= (GroupViewHolder) convertView.getTag();
        }
        CategoryGroupBean group=getGroup(groupPosition);
        Log.e("CategoryAdapter","111111"+mGroupList);
        ImageUtils.setGroupCategoryImage(mContext,holder.ivGroupThumb,group.getImageUrl());
        holder.tvGroupName.setText(group.getName());
        if (isExpanded){
            holder.ivIndicator.setImageResource(R.drawable.expand_off);
        }else {
            holder.ivIndicator.setImageResource(R.drawable.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        ChildViewHolder holder;
        if (convertView==null){
            holder=new ChildViewHolder();
            convertView=View.inflate(mContext,R.layout.item_category_child,null);
            holder.tvCategoryChildName= (TextView) convertView.findViewById(R.id.tv_category_child_name);
            holder.ivCategoryChildThumb= (ImageView) convertView.findViewById(R.id.iv_category_child_thumb);
            holder.layoutCategoryChild= (RelativeLayout) convertView.findViewById(R.id.layout_category_child);
            convertView.setTag(holder);
        }else {
            holder= (ChildViewHolder) convertView.getTag();
        }
        final CategoryChildBean child=mChildList.get(groupPosition).get(childPosition);
        ImageUtils.setChildCategoryImage(mContext,holder.ivCategoryChildThumb,child.getImageUrl());
        holder.tvCategoryChildName.setText(child.getName());
        if (child!=null){
            ImageUtils.setChildCategoryImage(mContext,holder.ivCategoryChildThumb,child.getImageUrl());
            holder.tvCategoryChildName.setText(child.getName());
            holder.layoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, CategoryChildActivity.class).putExtra(I.NewAndBoutiqueGood.CAT_ID,child.getId()));
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    public void addAll(List<CategoryGroupBean> mGroupList, List<ArrayList<CategoryChildBean>> mChildList) {
        Log.i("main", "999");
        this.mGroupList.clear();
        this.mGroupList.addAll(mGroupList);
        this.mChildList.clear();
        this.mChildList.addAll(mChildList);
        notifyDataSetChanged();
    }

    public void initGroupData(ArrayList<CategoryGroupBean> groupList) {
        if (mGroupList != null) {
            mGroupList.clear();
        }
        mGroupList.addAll(groupList);
        notifyDataSetChanged();
    }

    public void initChildData(ArrayList<CategoryChildBean> childList) {
        mChildList.add(childList);
        notifyDataSetChanged();
    }

    class GroupViewHolder{
        ImageView ivGroupThumb,ivIndicator;
        TextView tvGroupName;
    }
    class ChildViewHolder{
        RelativeLayout layoutCategoryChild;
        ImageView ivCategoryChildThumb;
        TextView tvCategoryChildName;
    }
}
