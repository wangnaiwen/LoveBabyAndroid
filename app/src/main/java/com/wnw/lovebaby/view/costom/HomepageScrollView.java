package com.wnw.lovebaby.view.costom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by wnw on 2016/12/6.
 */

public class HomepageScrollView extends ScrollView{
    /**
     * 滑动的距离和坐标
     * */
    private float xDistance, yDistance,xLast, yLast;

    public HomepageScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX-xLast);
                yDistance += Math.abs(curY-yLast);

                if(xDistance > yDistance){
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
