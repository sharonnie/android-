package com.zw.cjl.filter;

import java.util.List;

import android.widget.Filter;

import com.zw.cjl.adapter.OrderListAdapter;
import com.zw.cjl.dto.Database;
import com.zw.cjl.dto.Order;

public class OrderFilter extends Filter{

	private OrderListAdapter mAdapter;
	private List<Order> mOrderList = null;
	private Database _db = null;

	public OrderFilter(OrderListAdapter adapter, List<Order> list, Database db) {
		mAdapter = adapter;
		mOrderList = list;
		_db = db;
	}
	
	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		FilterResults results = new FilterResults();  
        if (constraint == null || constraint.length() == 0) {  
            results.values = mOrderList;
        	results.count = mOrderList.size();
        } else {  
        	List<Order> list = _db.queryOrders(constraint.toString(), 0, 1000);
        	
        	results.values = list;
        	results.count = list.size();
        }  
        return results;
	}

	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		@SuppressWarnings("unchecked")
		List<Order> local = (List<Order>)results.values;
		mAdapter.setmOrderList(local);
		mAdapter.notifyDataSetChanged();
	}

}
