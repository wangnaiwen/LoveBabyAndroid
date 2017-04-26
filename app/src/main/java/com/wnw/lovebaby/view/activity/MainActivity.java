package com.wnw.lovebaby.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wnw.lovebaby.R;
import com.wnw.lovebaby.login.ActivityCollector;
import com.wnw.lovebaby.view.fragment.CollegeFragment;
import com.wnw.lovebaby.view.fragment.HomepageFragment;
import com.wnw.lovebaby.view.fragment.MyFragment;
import com.wnw.lovebaby.view.fragment.ShoppingCarFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * four button on the bottom
     * */
    private LinearLayout homepageIcon;
    private LinearLayout collegeIcon;
    private LinearLayout shoppingCarIcon;
    private LinearLayout myIcon;

    private TextView homepageTv;
    private TextView collegeTv;
    private TextView shoppingCarTv;
    private TextView myTv;

    /**
     * four fragment
     * */
    private FragmentManager fragmentManager;
    private HomepageFragment homepageFragment;
    private CollegeFragment collegeFragment;
    private ShoppingCarFragment shoppingCarFragment;
    private MyFragment myFragment;

    /**
     * current selected fragment :
     * 1 : HomepageFragment
     * 2. CollegeFragment
     * 3. ShoppingCarFragment
     * 4. MyFragment
     * */

    public int CURRENT_FRAGMENT ;
    public final static int HOMEPAGE_FRAGMENT = 1;
    public final static int COLLEGE_FRAGMENT = 2;
    public final static int SHOPPING_CAR_FRAGMENT = 3;
    public final static int MY_FRAGMENT = 4;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        initView();
        setDefaultFragment();
    }

    /**
     * init the view
     * */
    private void initView(){
        homepageIcon = (LinearLayout)findViewById(R.id.icon_homepage);
        collegeIcon = (LinearLayout)findViewById(R.id.icon_college);
        shoppingCarIcon = (LinearLayout)findViewById(R.id.icon_shopping_car);
        myIcon = (LinearLayout)findViewById(R.id.icon_my);

        homepageTv = (TextView)findViewById(R.id.tv_homepage);
        collegeTv = (TextView)findViewById(R.id.tv_college);
        shoppingCarTv = (TextView)findViewById(R.id.tv_shopping_car);
        myTv = (TextView)findViewById(R.id.tv_my);

        homepageIcon.setOnClickListener(this);
        collegeIcon.setOnClickListener(this);
        shoppingCarIcon.setOnClickListener(this);
        myIcon.setOnClickListener(this);
    }

    /**
     *
     */
    private void setDefaultFragment(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        homepageFragment = new HomepageFragment();
        transaction.replace(R.id.fragment_pager, homepageFragment);
        transaction.commit();
        currentFragment = homepageFragment;
        CURRENT_FRAGMENT = HOMEPAGE_FRAGMENT;
    }

    /**
     * reset the type of button to default
     * */
    private void resetBtn(){
        homepageTv.setBackgroundResource(R.drawable.homepage_icon_default);
        collegeTv.setBackgroundResource(R.drawable.college_icon_default);
        shoppingCarTv.setBackgroundResource(R.drawable.shopping_car_default);
        myTv.setBackgroundResource(R.drawable.my_icon_default);

        homepageTv.setTextColor(getResources().getColor(R.color.colorIconDefault));
        collegeTv.setTextColor(getResources().getColor(R.color.colorIconDefault));
        shoppingCarTv.setTextColor(getResources().getColor(R.color.colorIconDefault));
        myTv.setTextColor(getResources().getColor(R.color.colorIconDefault));
    }

    /**
     * set the selected button's type
     * */
    private void setSelectedBtnType(){
        if(CURRENT_FRAGMENT == HOMEPAGE_FRAGMENT){
            homepageTv.setBackgroundResource(R.drawable.homepage_icon_selected);
            homepageTv.setTextColor(getResources().getColor(R.color.colorIconSelected));
        }else if(CURRENT_FRAGMENT == COLLEGE_FRAGMENT){
            collegeTv.setBackgroundResource(R.drawable.college_icon_selected);
            collegeTv.setTextColor(getResources().getColor(R.color.colorIconSelected));
        }else if (CURRENT_FRAGMENT == SHOPPING_CAR_FRAGMENT){
            shoppingCarTv.setBackgroundResource(R.drawable.shopping_car_selected);
            shoppingCarTv.setTextColor(getResources().getColor(R.color.colorIconSelected));
        }else {
            myTv.setBackgroundResource(R.drawable.my_icon_selected);
            myTv.setTextColor(getResources().getColor(R.color.colorIconSelected));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_homepage:
                if(CURRENT_FRAGMENT != HOMEPAGE_FRAGMENT) {
                    if(homepageFragment == null){
                        homepageFragment = new HomepageFragment();
                    }
                    resetBtn();
                    CURRENT_FRAGMENT = HOMEPAGE_FRAGMENT;
                    setSelectedBtnType();
                    changeFragment(homepageFragment);
                }
                break;
            case R.id.icon_college:
                if(CURRENT_FRAGMENT != COLLEGE_FRAGMENT) {
                    if(collegeFragment == null){
                        collegeFragment = new CollegeFragment();
                    }
                    resetBtn();
                    CURRENT_FRAGMENT = COLLEGE_FRAGMENT;
                    setSelectedBtnType();
                    changeFragment(collegeFragment);
                }
                break;
            case R.id.icon_shopping_car:
                if(CURRENT_FRAGMENT != SHOPPING_CAR_FRAGMENT) {
                    if(shoppingCarFragment == null){
                        shoppingCarFragment = new ShoppingCarFragment();
                    }
                    resetBtn();
                    CURRENT_FRAGMENT = SHOPPING_CAR_FRAGMENT;
                    setSelectedBtnType();
                    changeFragment(shoppingCarFragment);
                }
                break;
            case R.id.icon_my:
                if(CURRENT_FRAGMENT != MY_FRAGMENT) {
                    if(myFragment == null){
                        myFragment = new MyFragment();
                    }
                    resetBtn();
                    CURRENT_FRAGMENT = MY_FRAGMENT;
                    setSelectedBtnType();
                    changeFragment(myFragment);
                }
                break;
            default:
                break;
        }
    }

    /**
     * change the fragment
     * */
    private void changeFragment(Fragment to){
        FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(
                android.R.anim.fade_in, android.R.anim.fade_out);
        if (!to.isAdded()) {	// 先判断是否被add过
            transaction.hide(currentFragment).add(R.id.fragment_pager, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        currentFragment = to;
    }

    //双击退出桌面
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            /**
             * 当侧边栏处于展开状态时，按下返回键，关闭侧边栏
             * */

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
