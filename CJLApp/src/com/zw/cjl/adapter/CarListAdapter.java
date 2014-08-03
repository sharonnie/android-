package com.zw.cjl.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.zw.cjl.format.Status;

@SuppressLint("InflateParams")
public class CarListAdapter extends BaseAdapter implements Filterable {
	private JSONArray mArray = null;
	private LayoutInflater mInflater = null;
	
	public CarListAdapter(Context context, JSONArray array) {
		mArray = array;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mArray.length();
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
		JSONObject e;
		try {
			e = mArray.getJSONObject(position);
			item.carNo.setText(e.getString("carNo"));
			item.ownerName.setText(e.getString("ownerName"));
			item.status.setText(Status.carStatusToString(e.getString("status")));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return convertView;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

}
