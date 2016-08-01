package cn.ucai.fulicenter.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;

public class FuliCenterMainActivity extends BaseActivity implements View.OnClickListener{
    Button mbtnNewGoods,mbtnBoutique,mbtnCategory,mbtnCart,mbtnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli_center_main);
        initView();
        setListener();
    }

    private void setListener() {
        mbtnNewGoods.setOnClickListener(this);
        mbtnBoutique.setOnClickListener(this);
        mbtnCategory.setOnClickListener(this);
        mbtnCart.setOnClickListener(this);
        mbtnContact.setOnClickListener(this);
    }

    private void initView() {
        mbtnNewGoods= (Button) findViewById(R.id.btnNewGoods);
        mbtnBoutique= (Button) findViewById(R.id.btnNewGoods);
        mbtnCategory= (Button) findViewById(R.id.btnNewGoods);
        mbtnCart= (Button) findViewById(R.id.btnNewGoods);
        mbtnContact= (Button) findViewById(R.id.btnNewGoods);
    }

    @Override
    public void onClick(View view) {
        initDrawable();
        switch (view.getId()){
            case R.id.btnNewGoods:
                setDrawable(mbtnNewGoods,R.drawable.menu_item_new_good_selected,Color.BLACK);
                break;
            case R.id.btnBoutique:
                setDrawable(mbtnBoutique,R.drawable.boutique_selected,Color.BLACK);
                break;
            case R.id.btnCategory:
                setDrawable(mbtnCategory,R.drawable.menu_item_category_selected,Color.BLACK);
                break;
            case R.id.btnCart:
                setDrawable(mbtnCart,R.drawable.menu_item_cart_selected, Color.BLACK);
                break;
            case R.id.btnContact:
                setDrawable(mbtnContact,R.drawable.menu_item_personal_center_selected, Color.BLACK);
                break;

        }
    }
    private void initDrawable(){
        setDrawable(mbtnNewGoods, R.drawable.menu_item_new_good_normal, Color.GRAY);
        setDrawable(mbtnContact, R.drawable.menu_item_personal_center_normal, Color.GRAY);
        setDrawable(mbtnCart, R.drawable.menu_item_cart_normal, Color.GRAY);
        setDrawable(mbtnCategory, R.drawable.menu_item_category_normal, Color.GRAY);
        setDrawable(mbtnBoutique, R.drawable.boutique_normal, Color.GRAY);
    }
    private void setDrawable(Button button, int id, int color) {
        button.setTextColor(color);
        Drawable drawable = ContextCompat.getDrawable(this, id);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        button.setCompoundDrawables(null,drawable,null,null);

    }
}