package com.zw.cjl.watcher;

import android.text.Editable;
import android.text.TextWatcher;

import com.zw.cjl.adapter.StudentListAdapter;

public class StudentTextWatcher implements TextWatcher {

	StudentListAdapter mAdapter = null;
	
	public StudentTextWatcher(StudentListAdapter adapter){
		mAdapter = adapter;
	}

	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) 
	{

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		mAdapter.getFilter().filter(s);
	}

}
