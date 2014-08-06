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
	
	// TODO: 添加判断是否已经获得数据库全部数据的判断，如果是，则不再load
	// TODO: 为filter添加滑动到底部时自动加入新数据的功能
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
	/*
	final class TextViewHolder {
		public TextView carNo;
		public TextView ownerName;
		public TextView status;
	}
*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*TextViewHolder item  = null;
		
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
		*/
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
