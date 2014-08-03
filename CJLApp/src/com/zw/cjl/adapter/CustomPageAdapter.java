package com.zw.cjl.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class CustomPageAdapter extends PagerAdapter {
	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
	}

	public List<View> mListView;
	
	public CustomPageAdapter(List<View> mListViews) {
		this.mListView = mListViews;
	}
	
	@Override
	public int getCount() {
		return mListView.size(); 
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override  
    public void destroyItem(ViewGroup container, int position,  
            Object object) {  
        container.removeView(mListView.get(position));  
    }  

    @Override  
    public int getItemPosition(Object object) {  
    	return super.getItemPosition(object);  
    }   

    @Override  
    public Object instantiateItem(ViewGroup container, int position) { 
    	container.addView(mListView.get(position));
        return mListView.get(position);  
    } 

}
