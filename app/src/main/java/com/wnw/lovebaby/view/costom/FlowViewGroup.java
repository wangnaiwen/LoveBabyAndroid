package com.wnw.lovebaby.view.costom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wnw on 2016/12/8.
 *  流式布局
 */

public class FlowViewGroup extends ViewGroup {

    public FlowViewGroup(Context context) {
        this(context, null);
    }

    public FlowViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 在onMeasure里，测量所有子View的宽高，以及确定ViewGroup自己的宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获取系统传过来测量出的宽度 高度，以及相应的测量模式
         * 如果测量模式为EXACTRY(确定dp值, match_parent)， 则可以调用setMeasureDimension()设置
         * 如果测量模式为 AT_MOST(wrap_content)，则需要经过计算再去调用setMeasureDimension()设置
         */
        int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        /***
         * 计算宽度 高度 wrap_content测量模式下会使用到
         */
        /**
         * 存储计算出来的最后的宽度，高度,当前行高度和宽度
         * */
        int maxLineWidth = 0;
        int totalHeight = 0;
        int curLineWidth = 0;
        int curLineHeight = 0;

        /**
         * 得到内部子View的个数
         * */
        int count = getChildCount();
        /**
         * 存储子View
         * 子View的LayoutParams
         * 子View Layout需要的宽高（包含margin）, 用于计算是否越界
         * */
        View child = null;
        MarginLayoutParams params = null;
        int childWidth;
        int childHeight;

        /**
         * 遍历子View
         * */
        for(int i = 0; i < count; i++){
            child = getChildAt(i);
            /**
             * 如果Visible为：GONE，不用测量
             * */
            if(child.getVisibility() == GONE){
                continue;
            }

            /**
             * 测量子View
             * */
            measureChild(child, widthMeasureSpec,heightMeasureSpec);

            /**
             * 获取子View的LayoutParams, 子View的LayoutParams对象类型，
             * 取决于其ViewGroup的generateLayoutParams()方法的返回的对象类型，
             * 这里返回的是MarginLayoutParams)
             */
            params = (MarginLayoutParams)child.getLayoutParams();
            //子View需要的宽度 为 子View 本身宽度+marginLeft + marginRight
            childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            /**
             * 如果当前的宽度大于父控件允许的最大宽度，则换行
             * 父控件允许的最大宽度，如果要适配padding这里要：-paddingLeft()-paddingRight()
             * 即测量出的宽度减去父控件的左右边距
             * */
            if(curLineWidth + childWidth > widthMeasure-getPaddingLeft()-getPaddingRight()){
                //换行
                maxLineWidth = Math.max(maxLineWidth, curLineWidth);
                totalHeight += curLineHeight;  //高度加上当前高度
                curLineWidth = childWidth;     //当前行宽度为子View的宽度
                curLineHeight = childHeight;
            }else{
                //不换行
                curLineWidth += childWidth;
                curLineHeight = Math.max(curLineHeight, childHeight);
            }

            //如果已经是最后一个View, 要比较当前行的宽度和最大宽度，叠加一共的高度
            if(i == count -1){
                maxLineWidth = Math.max(maxLineWidth, curLineWidth);
                totalHeight += curLineHeight;
            }
        }

        //适配padding,如果是wrap_content，则除了子控件本身，还要加上父控件的padding
        if(widthMode != MeasureSpec.EXACTLY){
            maxLineWidth = maxLineWidth + getPaddingLeft() + getPaddingRight();
        }else{
            maxLineWidth = widthMeasure;
        }

        if(heightMode != MeasureSpec.EXACTLY){
            totalHeight = totalHeight + getPaddingTop() + getPaddingBottom();
        }else{
            totalHeight = heightMeasure;
        }
        setMeasuredDimension(maxLineWidth, totalHeight);
    }

    /**
     *  布局父控件位置以及子控件的位置
     */
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //子控件的个数
        int count = getChildCount();
        //ViewParent宽度，包含padding
        int width = getWidth();
        //View parent的右边x的布局限制值
        int rightLimit = width - getPaddingRight();

        //存储基准的left top
        int baseLeft = 0 + getPaddingLeft();
        int baseTop = 0 + getPaddingTop();

        //存储现在的left top
        int curLeft = baseLeft;
        int curTop = baseTop;

        //子View
        View child = null;
        //子View用于Layout的left top right, bottom
        int viewL, viewT, viewR, viewB;
        //子View的LayoutParams
        MarginLayoutParams params = null;
        //子View Layout需要的宽高（包含margin）,用于计算是否越界
        int childWidth;
        int childHeight;
        //子View本身的宽高
        int childW;
        int childH;
        Log.i("wnw", "count=" + count);
        //临时增加一个temp存储上一个View的高度，解决过长的两行View导致显示不正确的bug
        int lastChildHeight = 0;
        for(int j = 0 ; j < count; j++){
            child = getChildAt(j);
            if(child.getVisibility() == GONE){
                continue;
            }

            //获取子View本身的宽高
            childW = child.getMeasuredWidth();
            childH = child.getMeasuredHeight();

            //获取子View的LayoutParams，用于获取其margin
            params = (MarginLayoutParams)child.getLayoutParams();
            childWidth = childW + params.leftMargin + params.rightMargin;
            childHeight = childH + params.topMargin + params.bottomMargin;

            //这里要考虑padding，所以右边界为ViewParent宽度包含padding
            if(curLeft + childWidth > rightLimit){
                //新一行
                curTop = curTop + lastChildHeight;
                viewL = baseLeft + params.leftMargin;
                viewT = curTop + params.topMargin;
                viewR = viewL + childW;
                viewB = viewT + childH;

                curLeft = baseLeft + childWidth;
            }else{
                viewL = curLeft + params.leftMargin;
                viewT = curTop + params.topMargin;
                viewR = viewL + childW;
                viewB = viewT + childH;

                curLeft = curLeft + childWidth;
            }
            lastChildHeight = childHeight;
            child.layout(viewL, viewT, viewR, viewB);
        }
    }

    /**
     * @return 当前ViewGroup返回的Params的类型
     */
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
