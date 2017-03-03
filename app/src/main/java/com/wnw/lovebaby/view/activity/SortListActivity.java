package com.wnw.lovebaby.view.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.SortListAdapter;
import com.wnw.lovebaby.view.fragment.SortListFragment;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ImageView backSortList;
    private ListView sortListLv;
    private SortListAdapter sortListAdapter;
    private SortListFragment sortListFragment;

    public static int mPosition;
    private String[] strs = { "奶粉", "尿裤", "图书", "童装寝具","玩具用品", "洗护喂养", "辅助营养", "居家生活",
            "个人护理", "清洁护理"};
    private LinearLayout sortListFag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        initView();
    }

    private void initView(){
        backSortList = (ImageView)findViewById(R.id.back_sort_list);
        sortListLv = (ListView)findViewById(R.id.lv_sort_list);
        sortListFag = (LinearLayout)findViewById(R.id.fragment_sort_list);
        sortListAdapter = new SortListAdapter(this, strs);
        sortListLv.setAdapter(sortListAdapter);
        sortListLv.setDivider(null);
        backSortList.setOnClickListener(this);
        sortListLv.setOnItemClickListener(this);

        //创建MyFragment对象
        sortListFragment = new SortListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_sort_list, sortListFragment);
        //通过bundle传值给MyFragment
        Bundle bundle = new Bundle();
        bundle.putString(SortListFragment.TAG, strs[mPosition]);
        sortListFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_sort_list:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            default:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //拿到当前位置
        mPosition = i;
        //即使刷新adapter
        sortListAdapter.notifyDataSetChanged();

        int length = strs.length;
        for (int j = 0; j < length; j++) {
            sortListFragment = new SortListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_sort_list, sortListFragment);
            Bundle bundle = new Bundle();
            bundle.putString(SortListFragment.TAG, strs[i]);
            sortListFragment.setArguments(bundle);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
