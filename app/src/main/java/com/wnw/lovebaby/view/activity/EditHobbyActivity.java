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
 * Created by wnw on 2017/2/20.
 */

public class EditHobbyActivity extends Activity implements View.OnClickListener {

    private ImageView backHobby;
    private TextView saveHobby;
    private EditText editHobbyView;

    private String hobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_edit);
        Intent intent = getIntent();
        hobby = intent.getStringExtra("hobby");
        initView();
    }

    private void initView(){
        backHobby = (ImageView)findViewById(R.id.hobby_back);
        saveHobby = (TextView)findViewById(R.id.hobby_save);
        editHobbyView = (EditText)findViewById(R.id.hobby_edit);

        backHobby.setOnClickListener(this);
        saveHobby.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hobby_back:
                onBackPressed();
                break;
            case R.id.hobby_save:
                if(editHobbyView.getText().toString().trim().isEmpty()){
                    Toast.makeText(this, "兴趣为空", Toast.LENGTH_SHORT).show();
                }else if (editHobbyView.getText().toString().trim().equals(hobby)){
                    Toast.makeText(this, "兴趣没有改变", Toast.LENGTH_SHORT).show();
                }else {
                    saveHobby();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void saveHobby(){
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
