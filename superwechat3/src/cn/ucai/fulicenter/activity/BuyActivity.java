package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import cn.ucai.fulicenter.R;

public class BuyActivity extends Activity {
    EditText etBuyer;
    EditText etPhone;
    Spinner spAdressCity;
    EditText etStreet;
    Button btnBuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        initView();
    }

    private void initView() {
        etBuyer= (EditText) findViewById(R.id.et_address_buyer);
        etPhone= (EditText) findViewById(R.id.et_address_tel);
        spAdressCity= (Spinner) findViewById(R.id.spi_address_city);
        etStreet= (EditText) findViewById(R.id.et_address_street);
        btnBuy= (Button) findViewById(R.id.btn_buy);
    }
}
