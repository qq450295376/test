package cn.ucai.fulicenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.FuliCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.utils.Utils;

public class FuliCenterMainActivity extends BaseActivity {
    private  final  static  String TAG = FuliCenterMainActivity.class.getSimpleName();
    RadioButton rbNewGoods;
    RadioButton rbBoutique;
    RadioButton rbCategory;
    RadioButton rbCart;
    RadioButton rbPersonalCenter;
    TextView tvCartHint;
    RadioButton[] mrbTabs;
    int index;
    int currentIndex;
    public static final int ACTION_LOGIN=100;
    NewGoodFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    PersonalCenterFragment mPersonalCenterFragment;
    CartFragment mCartFragment;
    Fragment [] mFragment;
    @Override
    protected  void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli_center_main);
        initView();
        initFragment();
        setListener();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
                .add(R.id.fragment_container,mBoutiqueFragment)
                .add(R.id.fragment_container,mCategoryFragment)
                .add(R.id.fragment_container,mPersonalCenterFragment)
                .add(R.id.fragment_container,mCartFragment)
                .hide(mBoutiqueFragment).hide(mCategoryFragment).hide(mPersonalCenterFragment).hide(mCartFragment)
                .show(mNewGoodsFragment)
                .commit();

    }

    private void setListener() {
        updateCartCount();
    }

    private void initFragment() {
        mNewGoodsFragment = new NewGoodFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mCartFragment=new CartFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mFragment = new Fragment[5];
        mFragment[0] = mNewGoodsFragment;
        mFragment[1] = mBoutiqueFragment;
        mFragment[2] = mCategoryFragment;
        mFragment[3]=mCartFragment;
        mFragment[4] = mPersonalCenterFragment;

    }

    private void initView() {
        rbNewGoods = (RadioButton)findViewById(R.id.layout_new_good);
        rbBoutique = (RadioButton) findViewById(R.id.layout_boutique);
        rbCategory = (RadioButton) findViewById(R.id.layout_category);
        rbCart = (RadioButton) findViewById(R.id.layout_cart);
        rbPersonalCenter = (RadioButton) findViewById(R.id.layout_personal_center);
        tvCartHint = (TextView) findViewById(R.id.tvCartHint);
        mrbTabs = new RadioButton[5];
        mrbTabs[0]= rbNewGoods;
        mrbTabs[1]=rbBoutique;
        mrbTabs[2]= rbCategory;
        mrbTabs[3]=rbCart;
        mrbTabs[4]=rbPersonalCenter;
    }
    public  void  onCheckedChange(View view){
        switch (view.getId()){
            case R.id.layout_new_good:
                index=0;
                break;
            case R.id.layout_boutique:
                index=1;
                break;
            case R.id.layout_category:
                index=2;
                break;
            case R.id.layout_cart:
                if (DemoHXSDKHelper.getInstance().isLogined()){
                    index = 3;
                }else {
                    gotoLogin();
                }
                break;
            case R.id.layout_personal_center:
                if (DemoHXSDKHelper.getInstance().isLogined()){
                    index = 4;
                }else {
                    gotoLogin();
                }
        }
        setFragment();
    }

    private void setFragment() {
        Log.e(TAG,"index="+index+"currentIndex="+currentIndex);
        if (index!=currentIndex){
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragment[currentIndex]);
            if (!mFragment[index].isAdded()){
                trx.add(R.id.fragment_container,mFragment[index]);
            }
            trx.show(mFragment[index]).commit();
            setRadioButtonStatus(index);
            currentIndex =index;
        }
    }

    private void gotoLogin() {
        startActivityForResult(new Intent(this,LoginActivity.class),ACTION_LOGIN);
    }

    private void setRadioButtonStatus(int index) {
        for (int i=0;i<mrbTabs.length;i++){
            if (index==i){
                mrbTabs[i].setChecked(true);
            }else {
                mrbTabs[i].setChecked(false);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ACTION_LOGIN){
            if (DemoHXSDKHelper.getInstance().isLogined()){
                index=4;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!DemoHXSDKHelper.getInstance().isLogined() && index ==4){
            index = 0;
        }
        setFragment();
        setRadioButtonStatus(currentIndex);
    }
    class UpdateCartStatus extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
                int cartCount = Utils.getCartCount();
                Log.e("Fulicenter", "cartCount=" + cartCount);
                tvCartHint.setText(String.valueOf(cartCount));
                tvCartHint.setVisibility(View.VISIBLE);
        }
    }
    UpdateCartStatus mReceiver;
    private void updateCartCount(){
        mReceiver=new UpdateCartStatus();
        IntentFilter filter=new IntentFilter("update_cart_list");
        filter.addAction("update_user");
        registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver!=null){
            unregisterReceiver(mReceiver);
        }
    }
}
