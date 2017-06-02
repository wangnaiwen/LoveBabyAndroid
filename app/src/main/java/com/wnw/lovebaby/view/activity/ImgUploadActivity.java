package com.wnw.lovebaby.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.wnw.lovebaby.R;
import com.wnw.lovebaby.config.NetConfig;
import com.wnw.lovebaby.net.NetUtil;
import com.wnw.lovebaby.presenter.UpdateUserImgPresenter;
import com.wnw.lovebaby.upload.ImageForm;
import com.wnw.lovebaby.upload.ImageUploadRequest;
import com.wnw.lovebaby.upload.ResponseListener;
import com.wnw.lovebaby.view.viewInterface.IUpdateUserImgView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by wxx on 2017/3/24.
 */

public class ImgUploadActivity extends Activity implements View.OnClickListener , IUpdateUserImgView{

    private static final int PICK_CODE =1;

    private ImageView userImg;
    private ImageView back;
    private TextView upload;
    private String ImagePath=null;
    private Bitmap myBitmapImage;

    private int userId;
    private String url = null;
    private UpdateUserImgPresenter updateUserImgPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_img_upload);
        getUser();
        initView();
        initPresenter();
    }

    private void getUser(){
        Intent intent= getIntent();
        url = intent.getStringExtra("url");
        userId = intent.getIntExtra("userId", 0);
    }

    //初始化View
    private void initView(){
        userImg = (ImageView) findViewById(R.id.img_user);
        back = (ImageView)findViewById(R.id.back_upload);
        upload = (TextView)findViewById(R.id.upload);
        userImg.setOnClickListener(this);
        back.setOnClickListener(this);
        upload.setOnClickListener(this);
        if (url.equals("")  || url.equals("null")){
        }else{
            Glide.with(this).load(url).error(R.drawable.user).into(userImg);
        }
    }

    private void initPresenter(){
        updateUserImgPresenter = new UpdateUserImgPresenter(this, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.upload:
                if (ImagePath != null){
                    startUploadImg();
                }else {
                    Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back_upload:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.img_user:
                //获取系统选择图片intent
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //开启选择图片功能响应码为PICK_CODE
                startActivityForResult(intent,PICK_CODE);
                break;
            default:
                break;
        }
    }

    private void startUploadImg(){
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE){
            Toast.makeText(ImgUploadActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
        }else {
            uploadAvatar(myBitmapImage);
        }
    }

    private void startUpdateUserImg(){
        updateUserImgPresenter.updateUserImg(userId, url);
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

    private void dismissDialogs(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void updateUserImg(boolean isSuccess) {
        dismissDialogs();
        if (isSuccess){
            Toast.makeText(ImgUploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
            //返回数据到上一个Activity
            Intent intent = new Intent();
            intent.putExtra("url", url);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            Toast.makeText(ImgUploadActivity.this, "上传失败，请重新上传", Toast.LENGTH_SHORT).show();
        }
    }

    //设置响应intent请求
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==PICK_CODE)
        {
            if(intent!=null)
            {
                //获取图片路径
                //获取所有图片资源
                Uri uri=intent.getData();
                //设置指针获得一个ContentResolver的实例
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                cursor.moveToFirst();
                //返回索引项位置
                int index=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                //返回索引项路径
                ImagePath=cursor.getString(index);
                Log.d("image", ImagePath);
                cursor.close();
                //这个jar包要求请求的图片大小不得超过3m所以要进行一个压缩图片操作
                resizePhoto();
                userImg.setImageBitmap(myBitmapImage);
            }
        }
    }

    //压缩图片
    private void resizePhoto() {
        //得到BitmapFactory的操作权
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 如果设置为 true ，不获取图片，不分配内存，但会返回图片的高宽度信息。
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(ImagePath,options);
        //计算宽高要尽可能小于1024
        double ratio=Math.max(options.outWidth*1.0d/1024f,options.outHeight*1.0d/1024f);
        //设置图片缩放的倍数。假如设为 4 ，则宽和高都为原来的 1/4 ，则图是原来的 1/16 。
        options.inSampleSize=(int)Math.ceil(ratio);
        //我们这里并想让他显示图片所以这里要置为false
        options.inJustDecodeBounds=false;
        //利用Options的这些值就可以高效的得到一幅缩略图。
        myBitmapImage= BitmapFactory.decodeFile(ImagePath,options);
    }

    //上传头像
    private void uploadAvatar(Bitmap bitmap) {
        showDialogs();
        try {
            //利用时间戳得到文件名称
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = new Date();
            String imageName = simpleDateFormat.format(date);
            List<ImageForm> imageFormList = new ArrayList<>();
            imageFormList.add(new ImageForm(bitmap, imageName));
            Request request = new ImageUploadRequest(NetConfig.IMAGE_UPLOAD, imageFormList, new ResponseListener() {
                @Override
                public void onResponse(String response) {
                    //得到返回的文章封面的链接
                    url = NetConfig.IMAGE_PATH + response;
                    startUpdateUserImg();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissDialogs();
                    error.printStackTrace();
                    Toast.makeText(ImgUploadActivity.this, "上传图片失败", Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
