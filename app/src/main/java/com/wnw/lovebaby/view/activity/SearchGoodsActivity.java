package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2016/12/7.
 */

public class SearchGoodsActivity extends Activity implements View.OnClickListener,TextWatcher {
    private ImageView searchBack;
    private ImageView clearSearch;
    private EditText searchContent;
    private TextView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        initView();
    }

    private void initView(){
        searchBack = (ImageView)findViewById(R.id.search_back);
        clearSearch = (ImageView)findViewById(R.id.clear_search);
        searchContent = (EditText)findViewById(R.id.search_content);
        searchBtn = (TextView)findViewById(R.id.btn_search);

        searchBack.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        clearSearch.setOnClickListener(this);
        searchContent.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_back:
                finish();
                break;
            case R.id.clear_search:
                searchContent.setText("");
                break;
            case R.id.btn_search:

                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String str = searchContent.getText().toString().trim();
        if(str.length() > 0){
            if(clearSearch.getVisibility() == View.GONE){
                clearSearch.setVisibility(View.VISIBLE);
                searchBtn.setVisibility(View.VISIBLE);
            }

            if(str.length() > 20){
                Toast.makeText(this, "关键词太长", Toast.LENGTH_SHORT).show();
                searchContent.setText(str.substring(0,20));
                searchContent.setSelection(20);
            }
        }else{
            if(clearSearch.getVisibility() == View.VISIBLE){
                clearSearch.setVisibility(View.GONE);
                searchBtn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
