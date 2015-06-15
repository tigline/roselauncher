package com.tcl.launcher.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.base.LauncherBaseAdapter;
import com.tcl.launcher.json.entry.Episode;
import com.tcl.launcher.util.ImgLoader;

public class TvEpisodeAdapter<T> extends LauncherBaseAdapter<T> {
	private LayoutInflater mInflater;
	
	public TvEpisodeAdapter(List<T> list, Context context) {
		super(list, context);
		mInflater = LayoutInflater.from(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getAdapterView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = new TextView(mContext);
			holder = new ViewHolder();
			
			holder.name = (TextView) convertView;
			holder.name.setTextColor(mContext.getResources().getColor(R.color.white));
			holder.name.setTextSize(mContext.getResources().getDimension(R.dimen.media_detail_info_text_size));
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Episode episode = (Episode) getItem(position);
		holder.name.setText(episode.getEpisode());
		return convertView;
	}
	
	public void toNext(){
		this.notifyDataSetChanged();
	}

	public void toPre(){
		this.notifyDataSetChanged();
	}
	
	class ViewHolder{
		TextView name;
	}
}
