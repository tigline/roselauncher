package com.tcl.launcher.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * @author caomengqi
 * 2014年12月11日
 * @param <T>
 * @JDK version 1.8
 * @brief basic adapter for all adapters of this project
 * @version 1.0
 */
public abstract class LauncherBaseAdapter<T> extends BaseAdapter {
	protected List<T> mList;
	protected Context mContext;
	
	public LauncherBaseAdapter(List<T> list, Context context){
		mList = list;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		if(mList != null)
			return mList.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(mList != null)
			return mList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getAdapterView(position, convertView, parent);
	}
	
	public abstract View getAdapterView(int position, View convertView, ViewGroup parent);
	
	/**
	 * add item to the top to display
	 * @param list
	 */
	public void addToTop(List<T> list){
		if(mList != null && list != null){
			mList.addAll(0, list);
		}
		notifyDataSetChanged();
	}
	
	/**
	 * add item to the bottom to display
	 * @param list
	 */
	public void addToBottom(List<T> list){
		if(mList != null && list != null){
			mList.addAll(list);
		}
		notifyDataSetChanged();
	}
}