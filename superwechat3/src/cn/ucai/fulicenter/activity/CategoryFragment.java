package cn.ucai.fulicenter.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CategoryAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    FuliCenterMainActivity mContext;
    List<CategoryGroupBean> mGroupList;
    List<ArrayList<CategoryChildBean>> mChildList;
    ExpandableListView mExpandableListView;
    CategoryAdapter mAdapter;

    int groupCount;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_category, container, false);
        mContext= (FuliCenterMainActivity) getContext();
        mGroupList=new ArrayList<CategoryGroupBean>();
        mChildList=new ArrayList<ArrayList<CategoryChildBean>>();
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {
        findCategoryGroupList(new OkHttpUtils2.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result!=null){
                    Log.e("CategoryFragment","result="+result);
                    ArrayList<CategoryGroupBean> groupList= Utils.array2List(result);
                    Log.e("CategoryFragment","result="+groupList.size());
                    mAdapter.initGroupData(groupList);
                    for (CategoryGroupBean g : groupList){
                        findCategoryChildList(g.getId());
                    }
//                    mAdapter.initGroupData(groupList);
//                    mGroupList.addAll(groupList);
//                    mGroupList=groupList;
                    /*int i=0;
                    if (groupList!=null){
                        for (CategoryGroupBean g : groupList){
                            mChildList.add(new ArrayList<CategoryChildBean>());
                            findCategoryChildList(g.getId(),i);
                            i++;
                        }
                    }*/
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void findCategoryChildList(int parentId/*, final int indext*/) {
        Log.e("CategoryFragment","111111"+parentId);
        OkHttpUtils2<CategoryChildBean[]> utils=new OkHttpUtils2<CategoryChildBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(parentId))
                .addParam(I.PAGE_ID,String.valueOf(I.PAGE_ID_DEFAULT))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CategoryChildBean[].class)
                .execute(new OkHttpUtils2.OnCompleteListener<CategoryChildBean[]>() {
                    @Override
                    public void onSuccess(CategoryChildBean[] result) {
                        Log.e("CategoryFragment","ChildResult="+result);
                        if (result!=null){
                            ArrayList<CategoryChildBean> childList = Utils.array2List(result);
                            Log.e("Category","childList="+childList.size());
                            mAdapter.initChildData(childList);
                            /*if (childList!=null){
                                mChildList.set(indext,childList);
                            }*/
                        }/*
                        if (groupCount==mGroupList.size()){
                            mAdapter.addAll(mGroupList,mChildList);
                        }*/
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void initView(View layout) {
        mAdapter=new CategoryAdapter(mContext,mGroupList,mChildList);
        mExpandableListView= (ExpandableListView) layout.findViewById(R.id.elvCategory);
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setGroupIndicator(null);
    }
    private void findCategoryGroupList(OkHttpUtils2.OnCompleteListener<CategoryGroupBean[]> listener){
        OkHttpUtils2<CategoryGroupBean[]> utils = new OkHttpUtils2<CategoryGroupBean[]>();
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

}
