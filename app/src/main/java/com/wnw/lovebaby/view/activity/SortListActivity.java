package com.wnw.lovebaby.view.activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.SortListAdapter;
import com.wnw.lovebaby.domain.Mc;
import com.wnw.lovebaby.domain.Sc;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.FindMcsPresenter;
import com.wnw.lovebaby.presenter.FindScByMcIdPresenter;
import com.wnw.lovebaby.view.fragment.SortListFragment;
import com.wnw.lovebaby.view.viewInterface.IFindMcsVIew;
import com.wnw.lovebaby.view.viewInterface.IFindScByMcIdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wnw on 2016/12/20.
 */

public class SortListActivity extends FragmentActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, IFindMcsVIew, IFindScByMcIdView{

    private ImageView backSortList;
    private LinearLayout normalView;
    private TextView noSortTv;

    private Map map = new HashMap();
    private List<Mc> mcList = new ArrayList<>();

    private ListView sortListLv;
    private SortListAdapter sortListAdapter;
    private SortListFragment sortListFragment;

    private FindMcsPresenter findMcsPresenter;
    private FindScByMcIdPresenter findScByMcIdPresenter;

    private List<SortListFragment> fragmentList = new ArrayList<>();

    public static int mPosition;
    private LinearLayout sortListFag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        initView();
        initPresenter();
        startFindMcs();
    }

    private void initView(){
        backSortList = (ImageView)findViewById(R.id.back_sort_list);
        sortListLv = (ListView)findViewById(R.id.lv_sort_list);
        sortListFag = (LinearLayout)findViewById(R.id.fragment_sort_list);
        normalView = (LinearLayout)findViewById(R.id.normal_view);
        noSortTv = (TextView)findViewById(R.id.no_sort);

        sortListLv.setDivider(null);
        backSortList.setOnClickListener(this);
        sortListLv.setOnItemClickListener(this);
        noSortTv.setOnClickListener(this);
    }

    private void initPresenter(){
        findMcsPresenter = new FindMcsPresenter(this,this);
        findScByMcIdPresenter = new FindScByMcIdPresenter(this,this);
    }

    private void startFindMcs(){
        if(NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(this, "无网络", Toast.LENGTH_SHORT).show();
            noSortTv.setVisibility(View.VISIBLE);
            normalView.setVisibility(View.GONE);
        }else {
            findMcsPresenter.findMcs();
        }
    }

    private void startFindScByMcId(){
        findScByMcIdPresenter.findScbyMcId(mcList.get(pos).getId());
    }

    private void initFirstFragment(){
        //创建MyFragment对象
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_sort_list, fragmentList.get(0));
        fragmentTransaction.commit();
    }

    @Override
    public void showDialog() {
        showDialogs();
    }

    ProgressDialog dialog = null;
    private void showDialogs(){
        if(dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在努力中...");
        }
        dialog.show();
    }

    private void setAdapter(){
        sortListAdapter = new SortListAdapter(this, mcList);
        sortListLv.setAdapter(sortListAdapter);
    }

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showMcs(List<Mc> mcs) {
        if(mcs != null){
            this.mcList = mcs;
            startFindScByMcId();
            noSortTv.setVisibility(View.GONE);
            normalView.setVisibility(View.VISIBLE);
        }else{
            //查找失败
            noSortTv.setVisibility(View.VISIBLE);
            normalView.setVisibility(View.GONE);
        }
    }

    private int pos = 0;
    @Override
    public void showScs(List<Sc> scs) {
        if(scs == null){
            map.put(mcList.get(pos), new ArrayList<>());
        }else {
            map.put(mcList.get(pos), scs);
        }

        pos ++;
        if (pos == mcList.size()){
            //全部数据都加载完成
            dismissDialogs();
            setAdapter();
            initFragmentList();
            initFirstFragment();
        }else {
            startFindScByMcId();
        }
    }

    private void initFragmentList(){
        int length = mcList.size();
        for (int i = 0 ; i < length; i++){
            SortListFragment sortListFragment = new SortListFragment();
            sortListFragment.setScList((List<Sc>) map.get(mcList.get(i)));
            fragmentList.add(sortListFragment);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_sort_list:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.no_sort:
                startFindMcs();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //拿到当前位置
        //即使刷新adapter
        sortListAdapter.notifyDataSetChanged();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (!fragmentList.get(i).isAdded()) {	// 先判断是否被add过
            fragmentTransaction.hide(fragmentList.get(mPosition)).add(R.id.fragment_sort_list, fragmentList.get(i)).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            fragmentTransaction.hide(fragmentList.get(mPosition)).show(fragmentList.get(i)).commit(); // 隐藏当前的fragment，显示下一个
        }
        mPosition = i;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
