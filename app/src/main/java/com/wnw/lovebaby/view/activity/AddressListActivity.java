package com.wnw.lovebaby.view.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.adapter.ReceAddressAdapter;
import com.wnw.lovebaby.domain.ReceAddress;
import com.wnw.lovebaby.presenter.DeleteReceAddressPresenter;
import com.wnw.lovebaby.presenter.FindReceAddressPresenter;
import com.wnw.lovebaby.view.viewInterface.IDeleteReceAddressView;
import com.wnw.lovebaby.view.viewInterface.IFindReceAddressView;
import com.wnw.lovebaby.view.viewInterface.MvpBaseActivity;
import java.util.List;

/**
 * Created by wnw on 2016/12/11.
 */

public class AddressListActivity extends MvpBaseActivity<IFindReceAddressView, FindReceAddressPresenter>
        implements View.OnClickListener, IFindReceAddressView{

    public static int RESULT_CODE = 2;

    private ListView receAddressListView;
    private ImageView addressBack;
    private RelativeLayout addReceAddress;

    private ReceAddressAdapter receAddressAdapter;
    private List<ReceAddress> receAddressList;

    private int userId;

    private int editIndex;   //选中哪一个编辑
    private int deleteIndex; //选中哪一个删除

    //得到上一个Activity是什么，如果是设置:1，就是用户点击地址，不能返回，如果是订单确认:0，这点击返回
    private int lastAty = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ReceAddressAdapter.SELECT_RECE_ADDRESS:
                    if(lastAty == 0){
                        forResultActivity(msg.arg1);
                    }
                    break;
                case ReceAddressAdapter.EDIT_RECE_ADDRESS:
                    startEditReceAddressAty(msg.arg1);
                    break;
                case ReceAddressAdapter.DELETE_RECE_ADDRESS:
                    showDeleteAddressDialog(msg.arg1);
                    break;
            }
        }
    };

    /**
     * start edit address activity
     * */
    private void startEditReceAddressAty(int index){
        editIndex = index;
        Intent intent = new Intent(AddressListActivity.this, EditReceAddressActivity.class);
        ReceAddress receAddress = receAddressList.get(index);
        intent.putExtra("receAddress_data", receAddress);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    //得到返回的数据
                    ReceAddress address = (ReceAddress)data.getSerializableExtra("address");
                    receAddressList.remove(editIndex);
                    receAddressList.add(0, address);
                    refreshView();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    ReceAddress address = (ReceAddress)data.getSerializableExtra("address");
                    receAddressList.add(0, address);
                    refreshView();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initView();
        getLastAty();
        loadData();
    }

    private void getLastAty(){
        Intent intent = getIntent();
        lastAty = intent.getIntExtra("lastAty", 0);
    }


    private void loadData(){
        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        userId = preferences.getInt("id", 0);
        mPresenter.findReceAddress(this, userId);
    }

    private void refreshView(){
        receAddressAdapter.setReceAddressList(receAddressList);
        receAddressAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDialog() {
        Toast.makeText(this, "正在努力中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void findReceAdress(List<ReceAddress> addressList) {
        if(addressList != null ){
            receAddressList = addressList;
            setAdapter();
        }else {
            Toast.makeText(this, "暂无收货地址", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter(){
        receAddressAdapter = new ReceAddressAdapter(this, handler, receAddressList);
        receAddressListView.setAdapter(receAddressAdapter);
    }

    private void initView(){
        addressBack = (ImageView)findViewById(R.id.back_address_list);
        receAddressListView = (ListView)findViewById(R.id.lv_rece_address);
        addReceAddress = (RelativeLayout)findViewById(R.id.add_address);
        addReceAddress.setOnClickListener(this);
        addressBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_address_list:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.add_address:
                Intent intent = new Intent(this, AddReceAddressActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, 2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    /**
     * show the dialog of delete the address
     * */
    private void showDeleteAddressDialog(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除地址");
        builder.setMessage("是否删除这个收货地址？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteIndex = index;
                mPresenter.deleteReceAddress(AddressListActivity.this,receAddressList.get(index).getId());
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    @Override
    public void deleteReceAddress(boolean isSuccess) {
        if (isSuccess){
            receAddressList.remove(deleteIndex);
            refreshView();
        }else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * start activity for result
     * */
    private void forResultActivity(int index){
        Intent intent = new Intent();
        ReceAddress receAddress = receAddressList.get(index);
        intent.putExtra("receAddress_data", receAddress);
        setResult(RESULT_CODE, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    protected FindReceAddressPresenter createPresenter() {
        return new FindReceAddressPresenter();
    }
}
