package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.ucai.fulicenter.D;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.AlbumBean;
import cn.ucai.fulicenter.bean.GoodDetailsBean;
import cn.ucai.fulicenter.data.OkHttpUtils2;
import cn.ucai.fulicenter.utils.I;
import cn.ucai.fulicenter.view.DisPlayUtils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodDetailsActivity extends Activity {
    ImageView ivShare;
    ImageView ivCollect;
    ImageView ivCart;
    TextView tvCartCount;
    TextView tvGoodEnglish;
    TextView tvGoodName;
    TextView tvGoodPriceCurrent;
    TextView tvGoodPriceShop;
    SlideAutoLoopView mSlideAutoLoopView;
    FlowIndicator mFlowIndicator;
    WebView wvGoodBrief;

    GoodDetailsActivity mContext;
    int mGoodId;
    GoodDetailsBean mGoodDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);
        mContext=this;
        initView();
        initData();
    }

    private void getGoodDetailsByGoodId(OkHttpUtils2.OnCompleteListener<GoodDetailsBean> listener){
        final OkHttpUtils2<GoodDetailsBean> utils=new OkHttpUtils2<GoodDetailsBean>();
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(D.GoodDetails.KEY_GOODS_ID,String.valueOf(mGoodId))
                .targetClass(GoodDetailsBean.class)
                .execute(listener);


    }

    private void initData() {
        mGoodId=getIntent().getIntExtra(D.GoodDetails.KEY_GOODS_ID,0);
        Log.e("main","mGoodId"+mGoodId);
        if (mGoodId>0){
            getGoodDetailsByGoodId(new OkHttpUtils2.OnCompleteListener<GoodDetailsBean>() {
                @Override
                public void onSuccess(GoodDetailsBean result) {
                    if (result!=null){
                        Log.e("main","result="+result);
                        mGoodDetails = result;
                        showGoodDetails(result);
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("main","error="+error);
                    finish();
                    Toast.makeText(mContext,"获取商品数据失败",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            finish();
            Toast.makeText(mContext,"获取商品详情数据失败",Toast.LENGTH_SHORT).show();
        }
    }

    private void showGoodDetails(GoodDetailsBean detail) {
        tvGoodEnglish.setText(detail.getGoodsEnglishName());
        tvGoodName.setText(detail.getGoodsName());
        tvGoodPriceCurrent.setText(detail.getCurrencyPrice());
        tvGoodPriceShop.setText(detail.getShopPrice());
        mSlideAutoLoopView.startPlayLoop(mFlowIndicator,
                getAlbumImageUrl(),getAlbumImageSize());
        wvGoodBrief.loadDataWithBaseURL(null,mGoodDetails.getGoodsBrief(),D.TEXT_HTML,D.UTF_8,null);
    }

    private String[] getAlbumImageUrl() {
        String[] albumImageUrl=new String[]{};
        if (mGoodDetails.getProperties()!=null && mGoodDetails.getProperties().length>0){
            AlbumBean[] albums=mGoodDetails.getProperties()[0].getAlbums();
            albumImageUrl=new String[albums.length];
            for (int i=0;i<albumImageUrl.length;i++){
                albumImageUrl[i]=albums[i].getImgUrl();
            }
        }
        return albumImageUrl;
    }

    private int getAlbumImageSize() {
        if (mGoodDetails.getProperties()!=null && mGoodDetails.getProperties().length>0){
            return mGoodDetails.getProperties()[0].getAlbums().length;
        }
        return 0;
    }


    private void initView() {
        DisPlayUtils.initBack(mContext);
        ivShare= (ImageView) findViewById(R.id.iv_good_share);
        ivCollect= (ImageView) findViewById(R.id.iv_good_collect);
        ivCart= (ImageView) findViewById(R.id.ivAddCart);
        tvCartCount= (TextView) findViewById(R.id.tv_cart_count);
        tvGoodEnglish= (TextView) findViewById(R.id.tv_good_name_english);
        tvGoodName= (TextView) findViewById(R.id.tv_good_name);
        tvGoodPriceCurrent= (TextView) findViewById(R.id.tv_good_price_current);
        tvGoodPriceShop= (TextView) findViewById(R.id.tv_good_price_shop);
        mSlideAutoLoopView= (SlideAutoLoopView) findViewById(R.id.salv);
        mFlowIndicator= (FlowIndicator) findViewById(R.id.indicator);
        wvGoodBrief= (WebView) findViewById(R.id.wv_good_brief);
        WebSettings settings=wvGoodBrief.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true);
    }
}
