package com.zw.cjl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class CarListView extends ListView implements OnScrollListener {

	public CarListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		Log.v("ListView", "Scroll state changed!");
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.v("listView", "from " + firstVisibleItem + " count " + visibleItemCount + "total " + totalItemCount);
	}

}
