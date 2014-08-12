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
import com.zw.cjl.dto.Database;
import com.zw.cjl.dto.Order;
import com.zw.cjl.filter.OrderFilter;

public class OrderListAdapter extends BaseAdapter implements Filterable {
	private final int EACH_LOAD_COUNT = 100;
	private LayoutInflater mInflater = null;
	private OrderFilter filter = null;
	private List<Order> mOrderList = null;
	
	public List<Order> getmOrderList() {
		return mOrderList;
	}

	public void setmOrderList(List<Order> mOrderList) {
		this.mOrderList = mOrderList;
	}

	private Database _db = null;
	
	public OrderListAdapter(Context context, 
			  Database db) {
		mInflater = LayoutInflater.from(context);
		_db = db;
		mOrderList = _db.getOrders(0, EACH_LOAD_COUNT);
	}
	
	public void loadNewItems()
	{
		List<Order> list = _db.getOrders(getCount(), EACH_LOAD_COUNT);
		if (list.size() > 0) {
			mOrderList.addAll(list);
			notifyDataSetChanged();
		}
	}
	
	@Override
	public int getCount() {
		if (null != mOrderList)
		{
			return mOrderList.size();
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
		public TextView orderName;
		public TextView orderStage;
		public TextView orderTime;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextViewHolder item  = null;
		
		if (null == convertView)
		{
			convertView = mInflater.inflate(R.layout.order_list_item, null);
			item = new TextViewHolder();
			item.orderName = (TextView)convertView.findViewById(R.id.orderName);
			item.orderStage = (TextView)convertView.findViewById(R.id.orderStage);
			item.orderTime = (TextView)convertView.findViewById(R.id.orderTime);
			convertView.setTag(item);
		}
		else
		{
			item = (TextViewHolder)convertView.getTag();
		}
		
		Order order = mOrderList.get(position);
		
		item.orderName.setText(order.name);
		item.orderStage.setText(order.stage);
		item.orderTime.setText(order.examDate);
		
		return convertView;
	}
	
	@Override
	public Filter getFilter() {
		if (null == filter)
		{
			filter = new OrderFilter(this, mOrderList, _db);
		}
		
		return filter;
	}

}
