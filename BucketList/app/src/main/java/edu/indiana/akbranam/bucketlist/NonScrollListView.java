package edu.indiana.akbranam.bucketlist;

/**NonScrollListView.java: class for list view that doesn't scroll;
 * created to get scrolling behavior from a ListView in a fragment
 *
 * Created by Anna Branam
 * Created on: 3/3/2017
 * Last modified by: Anna Branam
 * Last modified on: 3/3/2017
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;


public class NonScrollListView extends ListView {

    //class to keep listView from scrolling so that it can be placed in a NestedListView in a fragment

    public NonScrollListView(Context context) {
        super(context);
    }

    public NonScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}