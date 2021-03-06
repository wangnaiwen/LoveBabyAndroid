package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2017/2/18.
 */

public class AboutLoveBabyActivity extends Activity implements View.OnClickListener{
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_lovebaby);
        back = (ImageView)findViewById(R.id.about_lovebaby_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.about_lovebaby_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
