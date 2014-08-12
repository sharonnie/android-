package com.zw.cjl.filter;

import java.util.List;

import android.widget.Filter;

import com.zw.cjl.adapter.CarListAdapter;
import com.zw.cjl.dto.Car;
import com.zw.cjl.dto.Database;

public class CarFilter extends Filter {

	private CarListAdapter mAdapter;
	private List<Car> mCarList = null;
	private Database _db = null;

	public CarFilter(CarListAdapter adapter, List<Car> list, Database db) {
		mAdapter = adapter;
		mCarList = list;
		_db = db;
	}

	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		FilterResults results = new FilterResults();  
        if (constraint == null || constraint.length() == 0) {  
            results.values = mCarList;
        	results.count = mCarList.size();
        } else {  
        	List<Car> list = _db.queryCars(constraint.toString(), 0, 1000);
        	
        	results.values = list;
        	results.count = list.size();
        }  
        return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		List<Car> local = (List<Car>)results.values;
		mAdapter.setmCarList(local);
		mAdapter.notifyDataSetChanged();
	}

}
