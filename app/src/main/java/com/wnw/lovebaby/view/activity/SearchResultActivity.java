package com.wnw.lovebaby.view.activity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.wnw.lovebaby.R;

/**
 * Created by wnw on 2016/12/8.
 */

public class SearchResultActivity extends Activity implements View.OnClickListener{

    private ImageView searchRelBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initView();
    }

    private void initView(){
        searchRelBack = (ImageView) findViewById(R.id.search_result_back);
        searchRelBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_result_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            default:

                break;
        }
    }
}
