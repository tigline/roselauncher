package com.tcl.launcher.ui.homecloud.ui;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileItem;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels.FileType;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileThumbUtils;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.ImageCache;

public class FilesGridViewAdapter extends BaseAdapter {

	class ViewHolder {		
		public ImageView bgImage;	
		public ImageView playImage;	
		public ImageView newImage;	
		public TextView fileName;
		public TextView fileAuthor;
		public TextView fileSize;		
	}

	private LayoutInflater m_inflater;
	private List<FileItem> m_files;
	private ImageCache m_imgCache = new ImageCache();
	private FileType m_fileType;	
	private Context m_context;
	
	public FilesGridViewAdapter(Context context, FileType type, List<FileItem> files) {	
		m_context = context;
		m_inflater = LayoutInflater.from(context);
		m_fileType = type;
		m_files = files;		
	}

	@Override
	public int getCount() {
		return m_files.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = m_inflater.inflate(R.layout.gridview_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.bgImage = (ImageView) convertView
					.findViewById(R.id.item_image_bk);
			viewHolder.playImage = (ImageView) convertView
					.findViewById(R.id.item_image_play);
			viewHolder.newImage = (ImageView) convertView
					.findViewById(R.id.item_image_new);		
			
			viewHolder.fileName = (TextView) convertView
					.findViewById(R.id.file_name);
			viewHolder.fileAuthor = (TextView) convertView
					.findViewById(R.id.creator_name);
			viewHolder.fileSize = (TextView) convertView
					.findViewById(R.id.size_name);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		FileItem item = m_files.get(position);			
		Bitmap bm = getThumbCache(item.path, viewHolder.bgImage);
		if (bm != null) {
			viewHolder.bgImage.setImageBitmap(bm);
		} else {			
			setDefaultImage(viewHolder.bgImage);			
		}
		
		if(m_fileType == FileType.Music || m_fileType == FileType.Video )
		{
			viewHolder.playImage.setVisibility(View.VISIBLE);
		}
		else
		{
			viewHolder.playImage.setVisibility(View.GONE);
		}
		
		if(item.isNew)
		{
			viewHolder.newImage.setVisibility(View.VISIBLE);
			if( Locale.getDefault().getLanguage().equalsIgnoreCase("zh"))
			{
				viewHolder.newImage
					.setBackgroundResource(R.drawable.file_manager_new_flag_zh);
			}
			else
			{
				viewHolder.newImage
				.setBackgroundResource(R.drawable.file_manager_new_flag_us);
			}
		}
		else
		{
			viewHolder.newImage.setVisibility(View.GONE);
		}		

		viewHolder.fileName.setText(item.name);	
		viewHolder.fileAuthor.setText(item.author);
		viewHolder.fileSize.setText(Formatter.formatFileSize(m_context, item.size));
		
		return convertView;
	}
	
	public Bitmap getThumbCache(String path, ImageView image) {		
	
		Bitmap bm = getBitmapFromMemCache(path);
		if (bm == null) {
			
			BitmapWorkerTask task = new BitmapWorkerTask(path, image);
			task.execute();	
		}
		return bm;
	}
	
	public Bitmap getBitmapFromMemCache(String  path)
	{
		return m_imgCache.getBitmapFromMemCache(path);
	}
	
	public void addBitmapToMemoryCache(String path, Bitmap bm) {
		m_imgCache.addBitmapToMemoryCache(path, bm);		
	}
	
	private Bitmap getThumbImage(String path) {
		Bitmap bm = null;
		
		switch(m_fileType)
		{		
		case Music:
			bm = FileThumbUtils.getMusicThumbnail(path);
			break;
		case Photo:
			bm = FileThumbUtils.getPhotoThumb(path);
			break;
		case Video:
			bm = FileThumbUtils.getVideoThumbnail(path);
			break;
			
		default:			
			break;			
		}	

		if (bm != null) {
			addBitmapToMemoryCache(path, bm);		
		}

		return bm;
	}
	
	private void setDefaultImage(ImageView image)
	{
		switch(m_fileType)
		{		
		case Music:
			image.setImageResource(R.drawable.file_manager_music);
			break;
		case Photo:
			image.setImageResource(R.drawable.file_manager_picture);
			break;
		case Video:
			image.setImageResource(R.drawable.file_manager_video);
			break;			
		default:
			image.setImageResource(R.drawable.file_manager_other_document);
			break;			
		}	
		
	}
	
	class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {		
	
		private String m_path;
		private ImageView m_image;
		public BitmapWorkerTask(String path, ImageView image)
		{
			m_path = path;	
			m_image = image;
		}

		protected void onPreExecute() {			
			super.onPreExecute();
		}
		
		@Override
		protected Bitmap doInBackground(Void... params) {
			
			return getThumbImage(m_path);			
		}
		
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if(result != null)
			{
				m_image.setImageBitmap(result);				
			}			
		}
	}
	
}
