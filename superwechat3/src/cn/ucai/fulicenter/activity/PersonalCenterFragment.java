package cn.ucai.fulicenter.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.utils.UserUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends Fragment {
    FuliCenterMainActivity mContext;
    ImageView mivAvatar;
    TextView mtvUserName;
    TextView mtvSetting;
    ImageView mivMsg;
    TextView mtvCollectCount;
    RelativeLayout layoutUserCenter;
    LinearLayout layoutCollect;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext= (FuliCenterMainActivity) getContext();
        View layout=inflater.inflate(R.layout.fragment_personal_center, container, false);
        initView(layout);
        initData();
        setListner();
        return layout;
    }

    private void initData() {
        if (DemoHXSDKHelper.getInstance().isLogined()){
            UserAvatar user = FuliCenterApplication.getInstance().getUser();
            if (user!=null){
                UserUtils.setAppCurrentUserNick(mtvUserName);
                UserUtils.setAppCurrentUserAvatar(mContext,mivAvatar);
            }
        }
    }

    private void setListner() {
        MySettingListener listener=new MySettingListener();
        layoutCollect.setOnClickListener(listener);
        mtvSetting.setOnClickListener(listener);
        updatecollectCountListener();
        layoutCollect.setOnClickListener(listener);
    }

    private void initView(View layout) {
        mivAvatar= (ImageView) layout.findViewById(R.id.iv_user_avatar);
        mtvUserName= (TextView) layout.findViewById(R.id.tv_user_name);
        mtvSetting= (TextView) layout.findViewById(R.id.tv_center_settings);
        mivMsg= (ImageView) layout.findViewById(R.id.iv_personal_center_msg);
        mtvCollectCount= (TextView) layout.findViewById(R.id.tv_collect_count);
        layoutUserCenter= (RelativeLayout) layout.findViewById(R.id.center_user_info);
        layoutCollect= (LinearLayout) layout.findViewById(R.id.layout_center_collect);
        
        initOriderList(layout);
    }
    class MySettingListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (DemoHXSDKHelper.getInstance().isLogined()){
                switch (view.getId()){
                    case R.id.center_user_info:
                    case R.id.tv_center_settings:
                        startActivity(new Intent(mContext,SettingsActivity.class));
                        break;
                    case R.id.layout_center_collect:
                        startActivity(new Intent(mContext,CollectActivity.class));
                }
            }
        }
    }

    private void initOriderList(View layout) {
        GridView gvOrderList= (GridView) layout.findViewById(R.id.center_user_order_list);
        ArrayList<HashMap<String,Object>> data=new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object>order=new HashMap<String, Object>();
        order.put("order",R.drawable.order_list1);
        data.add(order);
        HashMap<String,Object>order2=new HashMap<String, Object>();
        order2.put("order",R.drawable.order_list2);
        data.add(order2);
        HashMap<String,Object>order3=new HashMap<String, Object>();
        order3.put("order",R.drawable.order_list3);
        data.add(order3);
        HashMap<String,Object>order4=new HashMap<String, Object>();
        order4.put("order",R.drawable.order_list4);
        data.add(order4);
        HashMap<String,Object>order5=new HashMap<String, Object>();
        order5.put("order",R.drawable.order_list5);
        data.add(order5);
        SimpleAdapter adapter=new SimpleAdapter(mContext,data,R.layout.simple_adapter,
                new String[]{"order"},new int[]{R.id.iv_order});
        gvOrderList.setAdapter(adapter);
    }
    class UpdateCollectCount extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int collectCount = FuliCenterApplication.getInstance().getCollectCount();
            Log.e("Personal","collectCount="+collectCount);
            mtvCollectCount.setText(String.valueOf(collectCount));
        }
    }
    UpdateCollectCount mReceiver;
    private void updatecollectCountListener(){
        mReceiver=new UpdateCollectCount();
        IntentFilter filter=new IntentFilter("update_collect");
        mContext.registerReceiver(mReceiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(mReceiver);
    }
}
