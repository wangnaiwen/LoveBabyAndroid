package com.wnw.lovebaby.view.costom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by wnw on 2016/12/13.
 */

public class AddressListView extends ListView {
    public AddressListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AddressListView(Context context) {
        super(context);
    }
    public AddressListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
