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
import com.zw.cjl.dto.Coach;
import com.zw.cjl.dto.Database;
import com.zw.cjl.filter.CoachFilter;

@SuppressLint("InflateParams")
public class CoachListAdapter extends BaseAdapter implements Filterable {

	private final int EACH_LOAD_COUNT = 100;
	private Database _db = null;
	private LayoutInflater mInflater = null;
	private CoachFilter filter = null;
	private List<Coach> mCoachList = null;
	
	public CoachListAdapter(Context context, 
						  Database db) {
		mInflater = LayoutInflater.from(context);
		_db = db;
		mCoachList = _db.getCoachs(0, EACH_LOAD_COUNT);
	}
	
	// TODO: 添加判断是否已经获得数据库全部数据的判断，如果是，则不再load
	// TODO: 为filter添加滑动到底部时自动加入新数据的功能
	public void loadNewItems()
	{
		List<Coach> list = _db.getCoachs(getCount(), EACH_LOAD_COUNT);
		if (list.size() > 0) {
			mCoachList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void setmCoachList(List<Coach> mCoachList) {
		this.mCoachList = mCoachList;
	}

	public List<Coach> getmCoachList() {
		return mCoachList;
	}

	@Override
	public int getCount() {
		if (null != mCoachList)
		{
			return mCoachList.size();
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
		public TextView coachName;
		public TextView coachSex;
		public TextView coachPhone;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextViewHolder item  = null;
		
		if (null == convertView)
		{
			convertView = mInflater.inflate(R.layout.coach_list_item, null);
			item = new TextViewHolder();
			item.coachName = (TextView)convertView.findViewById(R.id.coachName);
			item.coachSex = (TextView)convertView.findViewById(R.id.coachSex);
			item.coachPhone = (TextView)convertView.findViewById(R.id.coachPhone);
			convertView.setTag(item);
		}
		else
		{
			item = (TextViewHolder)convertView.getTag();
		}
		
		Coach coach = mCoachList.get(position);
		
		item.coachName.setText(coach.xm);
		item.coachSex.setText(coach.xbry);
		item.coachPhone.setText(coach.sjhm);
		
		return convertView;
	}

	@Override
	public Filter getFilter() {
		if (null == filter)
		{
			filter = new CoachFilter(this, mCoachList, _db);
		}
		
		return filter;
	}

}
