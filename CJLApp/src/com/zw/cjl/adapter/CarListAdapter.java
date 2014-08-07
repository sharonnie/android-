package com.zw.cjl.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.zw.cjl.activity.R;
import com.zw.cjl.dto.Car;
import com.zw.cjl.dto.Database;
import com.zw.cjl.filter.CarFilter;

@SuppressLint("InflateParams")
public class CarListAdapter extends BaseAdapter implements Filterable {
	private final int EACH_LOAD_COUNT = 100;
	private Database _db = null;
	private LayoutInflater mInflater = null;
	private CarFilter filter = null;
	private List<Car> mCarList = null;
	
	public CarListAdapter(Context context, 
						  Database db) {
		mInflater = LayoutInflater.from(context);
		_db = db;
		mCarList = _db.getCars(0, EACH_LOAD_COUNT);
	}
	
	public void loadNewItems()
	{
		List<Car> list = _db.getCars(getCount(), EACH_LOAD_COUNT);
		if (list.size() > 0) {
			mCarList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void setmCarList(List<Car> mCarList) {
		this.mCarList = mCarList;
	}

	public List<Car> getmCarList() {
		return mCarList;
	}

	@Override
	public int getCount() {
		if (null != mCarList)
		{
			return mCarList.size();
		}
		
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	final class TextViewHolder {
		public TextView carNo;
		public TextView ownerName;
		public TextView status;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextViewHolder item  = null;
		
		if (null == convertView)
		{
			convertView = mInflater.inflate(R.layout.car_list_item, null);
			item = new TextViewHolder();
			item.carNo = (TextView)convertView.findViewById(R.id.carNo);
			item.ownerName = (TextView)convertView.findViewById(R.id.ownerName);
			item.status = (TextView)convertView.findViewById(R.id.status);
			convertView.setTag(item);
		}
		else
		{
			item = (TextViewHolder)convertView.getTag();
		}
		
		Car car = mCarList.get(position);
		
		item.carNo.setText(car._carNo);
		item.ownerName.setText(car._ownerName);
		item.status.setText(car._status);
		
		return convertView;
	}

	@Override
	public Filter getFilter() {
		if (null == filter)
		{
			filter = new CarFilter(this, mCarList, _db);
		}
		
		return filter;
	}

}
