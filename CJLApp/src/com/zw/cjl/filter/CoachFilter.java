package com.zw.cjl.filter;

import java.util.List;

import android.widget.Filter;

import com.zw.cjl.adapter.CoachListAdapter;
import com.zw.cjl.dto.Coach;
import com.zw.cjl.dto.Database;

public class CoachFilter extends Filter {

	private CoachListAdapter mAdapter;
	private List<Coach> mCoachList = null;
	private Database _db = null;

	public CoachFilter(CoachListAdapter adapter, List<Coach> list, Database db) {
		mAdapter = adapter;
		mCoachList = list;
		_db = db;
	}

	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		FilterResults results = new FilterResults();  
        if (constraint == null || constraint.length() == 0) {  
            results.values = mCoachList;
        	results.count = mCoachList.size();
        } else {  
        	List<Coach> list = _db.queryCoachs(constraint.toString(), 0, 100);
        	
        	results.values = list;
        	results.count = list.size();
        }  
        return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		List<Coach> local = (List<Coach>)results.values;
		mAdapter.setmCoachList(local);
		mAdapter.notifyDataSetChanged();
	}

}
