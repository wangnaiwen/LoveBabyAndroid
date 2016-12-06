package com.wnw.lovebaby.view.costom;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by wnw on 2016/12/6.
 */

public class HomepageSwipeRefreshLayout extends SwipeRefreshLayout{
    private float startX;
    private float startY;

    /**
     * 记录viewpage是否被拖拽的标记
     * */
    private boolean mIsVpDragger;
    private final int mTouchSlop;

    public HomepageSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //down
                startX = ev.getX();
                startY = ev.getY();

                //初始化标记
                mIsVpDragger = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mIsVpDragger){
                    return false;
                }

                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX = Math.abs(endX-startX);
                float distanceY = Math.abs(endY-startY);

                if(distanceX > mTouchSlop && distanceX > distanceY){
                    mIsVpDragger = true;
                    return false; //是横划将事件交给viewpager处理
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsVpDragger = false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
