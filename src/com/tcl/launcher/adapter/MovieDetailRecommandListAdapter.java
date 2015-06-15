package com.tcl.launcher.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.base.LauncherBaseAdapter;
import com.tcl.launcher.json.entry.Movie;
import com.tcl.launcher.json.entry.VodBrief;
import com.tcl.launcher.util.ImgLoader;

public class MovieDetailRecommandListAdapter<T> extends LauncherBaseAdapter<T> {
	private LayoutInflater mInflater;
	private ImgLoader mImgLoader;

	public MovieDetailRecommandListAdapter(List<T> list, Context context) {
		super(list, context);
		mInflater = LayoutInflater.from(context);
		mImgLoader = ImgLoader.getInstance(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getAdapterView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.recommand_list_item, null);
			holder = new ViewHolder();

			holder.name = (TextView) convertView
					.findViewById(R.id.media_result_gridview_list_item_name);
			holder.post = (ImageView) convertView
					.findViewById(R.id.media_result_gridview_list_item_img);
			// holder.tag = (ImageView)
			// convertView.findViewById(R.id.media_result_gridview_list_item_tag);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		VodBrief vodBrief = (VodBrief) getItem(position);
		holder.name.setText(vodBrief.getTitle());
		mImgLoader.loadImage(vodBrief.getImg(), holder.post,
				R.drawable.media_list_item_default_img, R.drawable.media_list_item_default_img);

		return convertView;
	}

	public void toNext() {
		this.notifyDataSetChanged();
	}

	public void toPre() {
		this.notifyDataSetChanged();
	}

	class ViewHolder {
		ImageView post;
		TextView name;
		// ImageView tag;
	}
}
