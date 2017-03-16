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

public class EditEmailActivity extends Activity implements View.OnClickListener {

    private ImageView backEmail;
    private TextView saveEmail;
    private EditText editEmailView;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_edit);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        initView();
    }

    private void initView(){
        backEmail = (ImageView)findViewById(R.id.email_back);
        saveEmail = (TextView)findViewById(R.id.email_save);
        editEmailView = (EditText)findViewById(R.id.email_edit);

        backEmail.setOnClickListener(this);
        saveEmail.setOnClickListener(this);
        editEmailView.setText(email);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_back:
                onBackPressed();
                break;
            case R.id.email_save:
                String newEmail = editEmailView.getText().toString().trim();
                if(newEmail.isEmpty()){
                    Toast.makeText(this, "邮箱为空", Toast.LENGTH_SHORT).show();
                }else if (newEmail.equals(email)){
                    Toast.makeText(this, "邮箱没有改变", Toast.LENGTH_SHORT).show();
                }else {
                    email = newEmail;
                    saveEmail();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void saveEmail(){
        Intent intent = new Intent();
        intent.putExtra("email", email);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
