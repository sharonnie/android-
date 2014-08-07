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
import com.zw.cjl.dto.Student;
import com.zw.cjl.filter.StudentFilter;

@SuppressLint("InflateParams")
public class StudentListAdapter extends BaseAdapter implements Filterable {
	private final int EACH_LOAD_COUNT = 100;
	private Database _db = null;
	private LayoutInflater mInflater = null;
	private StudentFilter filter = null;
	private List<Student> mStudentList = null;
	
	public StudentListAdapter(Context context, 
						  Database db) {
		mInflater = LayoutInflater.from(context);
		_db = db;
		mStudentList = _db.getStudents(0, EACH_LOAD_COUNT);
	}
	
	public void loadNewItems()
	{
		List<Student> list = _db.getStudents(getCount(), EACH_LOAD_COUNT);
		if (list.size() > 0) {
			mStudentList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void setmStudentList(List<Student> mStudentList) {
		this.mStudentList = mStudentList;
	}

	public List<Student> getmStudentList() {
		return mStudentList;
	}

	@Override
	public int getCount() {
		if (null != mStudentList)
		{
			return mStudentList.size();
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
		public TextView studentName;
		public TextView studentCardType;
		public TextView studentPhone;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextViewHolder item  = null;
		
		if (null == convertView)
		{
			convertView = mInflater.inflate(R.layout.student_list_item, null);
			item = new TextViewHolder();
			item.studentName = (TextView)convertView.findViewById(R.id.studentName);
			item.studentCardType = (TextView)convertView.findViewById(R.id.studentCardType);
			item.studentPhone = (TextView)convertView.findViewById(R.id.studentPhone);
			convertView.setTag(item);
		}
		else
		{
			item = (TextViewHolder)convertView.getTag();
		}
		
		Student student = mStudentList.get(position);
		
		item.studentName.setText(student.xm);
		item.studentCardType.setText(student.sqcx);
		item.studentPhone.setText(student.sjhm);
		
		return convertView;
	}

	@Override
	public Filter getFilter() {
		if (null == filter)
		{
			filter = new StudentFilter(this, mStudentList, _db);
		}
		
		return filter;
	}

}
