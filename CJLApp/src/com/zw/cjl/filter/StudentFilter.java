package com.zw.cjl.filter;

import java.util.List;

import android.widget.Filter;

import com.zw.cjl.adapter.StudentListAdapter;
import com.zw.cjl.dto.Database;
import com.zw.cjl.dto.Student;

public class StudentFilter extends Filter {

	private List<Student> mStudentList = null;
	private Database _db = null;
	private StudentListAdapter mAdapter;

	public StudentFilter(StudentListAdapter adapter, List<Student> list, Database db) {
		mAdapter = adapter;
		mStudentList = list;
		_db = db;
	}

	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		FilterResults results = new FilterResults();  
        if (constraint == null || constraint.length() == 0) {  
            results.values = mStudentList;
        	results.count = mStudentList.size();
        } else {
        	List<Student> list = _db.queryStudents(constraint.toString(), 0, 1000);
        	
        	results.values = list;
        	results.count = list.size();
        }  
        return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		List<Student> local = (List<Student>)results.values;
		mAdapter.setmStudentList(local);
		mAdapter.notifyDataSetChanged();
	}

}
