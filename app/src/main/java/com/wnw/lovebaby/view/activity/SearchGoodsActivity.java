package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.view.costom.FlowViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wnw on 2016/12/7.
 */

public class SearchGoodsActivity extends Activity implements View.OnClickListener,TextWatcher {
    private ImageView searchBack;
    private ImageView clearSearch;
    private EditText searchContent;
    private TextView searchBtn;

    private FlowViewGroup hotKeyWord;
    private FlowViewGroup historyKeyWord;

    private ImageView clearHistorySearch;

    private List<String> historyKeyWordList =  new ArrayList<>();

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

        hotKeyWord = (FlowViewGroup)findViewById(R.id.flow_layout_hot_keyword);
        historyKeyWord = (FlowViewGroup)findViewById(R.id.flow_layout_history_keyword);
        clearHistorySearch = (ImageView)findViewById(R.id.clear_history_search);
        clearHistorySearch.setOnClickListener(this);
        setFlowView();
    }

    /**
     * set hot search flow layout data
     * */
    private void setFlowView(){
        String[] mTexts = new String[]{"宝宝奶粉", "宝宝止尿布",
                "宝宝早教", " 宝宝故事书", "纸巾", "保温灯","宝宝浴缸"};
        TextView tv;
        for (int i=0;i<mTexts.length;i++) {
            tv = (TextView) LayoutInflater.from(this).inflate(R.layout.flow_item, hotKeyWord, false);
            tv.setText(mTexts[i]);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search(((TextView)v).getText().toString());
                }
            });
            if (i%2 == 1){
                tv.setBackgroundResource(R.drawable.bg_flow_item2);
            }
            hotKeyWord.addView(tv);
        }
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
                search(searchContent.getText().toString().trim());
                break;
            case R.id.clear_history_search:
                clearHistorySearch();
                break;
            default:
                break;
        }
    }

    /**
     * clear history search
     * */
    private void clearHistorySearch(){
        historyKeyWord.removeAllViews();
        int count = historyKeyWordList.size();
        for (int i = 0; i < count; i++){
            historyKeyWordList.remove(0);
        }
        clearHistorySearch.setVisibility(View.INVISIBLE);
    }

    /**
     * search by keyword
     * */
    private void search(String keyword){
        int index = isExistHistoryKeyWorld(keyword);
        if(index != -1){
            historyKeyWordList.remove(index);
        }
        historyKeyWordList.add(0, keyword);
        setHistoryFlowView();
        Intent intent = new Intent(this, SearchResultActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /**
     * setFlowView
     * */
    private void setHistoryFlowView(){
        historyKeyWord.removeAllViews();
        TextView tv;
        int count = historyKeyWordList.size();
        if(count > 0){
            clearHistorySearch.setVisibility(View.VISIBLE);
        }
        for (int i=0;i<count;i++) {
            tv = (TextView) LayoutInflater.from(this).inflate(R.layout.flow_item, historyKeyWord, false);
            tv.setText(historyKeyWordList.get(i));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search(((TextView)v).getText().toString());
                }
            });
            if (i%2 == 1){
                tv.setBackgroundResource(R.drawable.bg_flow_item2);
            }
            historyKeyWord.addView(tv);
        }
    }

    /***
     * 判断搜索历史是否存在该关键词词条,存在，返回词条在list中的位置，不存在就返回-1
     * */
    private int isExistHistoryKeyWorld(String str){
        int count = historyKeyWordList.size();
        for (int i = 0; i < count; i++){
            if(historyKeyWordList.get(i).equals(str)){
                return i;
            }
        }
        return -1;
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
