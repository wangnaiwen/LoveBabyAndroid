package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2017/2/19.
 */

public class EditNickNameActivity extends Activity implements View.OnClickListener{
    private TextView saveNickName;
    private ImageView backNickName;
    private EditText editNickName;

    private String nickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_edit);
        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        initView();
    }

    private void initView(){
        saveNickName = (TextView)findViewById(R.id.nickname_save);
        backNickName = (ImageView) findViewById(R.id.nickname_back);
        editNickName = (EditText)findViewById(R.id.nickname_edit);

        saveNickName.setOnClickListener(this);
        backNickName.setOnClickListener(this);
        editNickName.setText(nickName);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nickname_save:
                String newNickName = editNickName.getText().toString().trim();
                if(newNickName.isEmpty()){
                    Toast.makeText(this, "昵称为空", Toast.LENGTH_SHORT).show();
                }else if (newNickName.equals(nickName)){
                    Toast.makeText(this, "昵称没有改变", Toast.LENGTH_SHORT).show();
                }else {
                    nickName = newNickName;
                    saveNickName();
                }
                break;
            case R.id.nickname_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void saveNickName(){
        Intent intent = new Intent();
        intent.putExtra("nickName", nickName);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
