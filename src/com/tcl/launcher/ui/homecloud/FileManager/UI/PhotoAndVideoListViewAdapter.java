package com.tcl.launcher.ui.homecloud.FileManager.UI;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileItem;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels.FileType;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.DateUtils;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileManager;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileThumbUtils;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.ImageCache;

public class PhotoAndVideoListViewAdapter extends BaseAdapter {

	class ViewHolder {		
		public LinearLayout title;
		public TextView date;
		public TextView detailHead;	
		public TextView detailTail;

		public GridLayoutItem item1 = new GridLayoutItem();
		public GridLayoutItem item2 = new GridLayoutItem();
		public GridLayoutItem item3 = new GridLayoutItem();
		public GridLayoutItem item4 = new GridLayoutItem();
	}

	class GridLayoutItem{
		public LinearLayout container;
		public ImageView image;
		public ImageView imagePlay;
		public ImageView imageNew;
		public TextView name;
		public TextView creator;
		public TextView size; 
	}

	private LayoutInflater m_inflater;
	private ImageCache m_imgCache = new ImageCache();
	private FileType m_fileType;	
	private Context m_context;
	private LinkedList<List<FileItem>> linkedList;

	public PhotoAndVideoListViewAdapter(Context context,
			FileType type, List<FileItem> files) {	
		m_context = context;
		m_inflater = LayoutInflater.from(context);
		m_fileType = type;
		linkedList = DateUtils.classifyByDate2(DateUtils.classifyByDate(files));
	}

	@Override
	public int getCount() {
		return linkedList.size();
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
			convertView = m_inflater.inflate(R.layout.hdphoto_and_video_listview_item, null);
			viewHolder = new ViewHolder();

			initViewHolderTitle(viewHolder, convertView);
			initViewHolderGridLayoutItem(viewHolder.item1, convertView, R.id.item1);
			initViewHolderGridLayoutItem(viewHolder.item2, convertView, R.id.item2);
			initViewHolderGridLayoutItem(viewHolder.item3, convertView, R.id.item3);
			initViewHolderGridLayoutItem(viewHolder.item4, convertView, R.id.item4);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		List<FileItem> tempList = linkedList.get(position);

		decorateViewHolderTitle(viewHolder, tempList);
		decorateViewHolderGridLayoutItems(viewHolder, tempList);


		return convertView;
	}

	private void initViewHolderTitle(ViewHolder viewHolder, View convertView){
		viewHolder.date 
		= (TextView) convertView.findViewById(R.id.date);
		viewHolder.detailHead 
		= (TextView) convertView.findViewById(R.id.detail_head);
		viewHolder.detailTail 
		= (TextView) convertView.findViewById(R.id.detail_tail);
		viewHolder.title 
		= (LinearLayout)convertView.findViewById(R.id.title);
	}

	private void initViewHolderGridLayoutItem(GridLayoutItem item, 
			View convertView, int id){
		View view = convertView.findViewById(id);
		item.container = (LinearLayout)view;
		item.image = (ImageView)view.findViewById(R.id.item_image_bk);
		item.imagePlay = (ImageView)view.findViewById(R.id.item_image_play);
		item.imageNew = (ImageView)view.findViewById(R.id.item_image_new);
		item.name = (TextView)view.findViewById(R.id.file_name);
		item.creator = (TextView)view.findViewById(R.id.creator_name);
		item.size = (TextView)view.findViewById(R.id.size_name);
	}

	private void decorateViewHolderTitle(ViewHolder viewHolder,
			List<FileItem> list){
		FileItem divide = list.get(0);
		FileItem head = list.get(1);

		if(divide.name.equals(DateUtils.DIVIDE_VISIBLE)
				&& divide.author.equals(DateUtils.DIVIDE_VISIBLE)){
			viewHolder.title.setVisibility(View.VISIBLE);

			String date = DateUtils.generateDate(head.uploadtime);
			String detailHead = DateUtils.generateDetailHead(
					head.uploadtime, m_context);
			String detailTail = DateUtils.generateDetailTail(
					head.uploadtime, divide.path, 
					m_context, m_fileType);

			viewHolder.date.setText(date); 
			viewHolder.detailHead.setText(detailHead);
			viewHolder.detailTail.setText(detailTail);
		}else{
			viewHolder.title.setVisibility(View.GONE);
		}
	}

	private void decorateViewHolderGridLayoutItems(ViewHolder viewHolder, List<FileItem> list){
		List<GridLayoutItem> gridLayoutItems 
		= new LinkedList<PhotoAndVideoListViewAdapter.GridLayoutItem>();
		gridLayoutItems.add(viewHolder.item1);
		gridLayoutItems.add(viewHolder.item2);
		gridLayoutItems.add(viewHolder.item3);
		gridLayoutItems.add(viewHolder.item4);

		int size = list.size() - 1;
		for(int i = 0 ; i < 4; i++){
			if(size > i){
				gridLayoutItems.get(i).container.setVisibility(View.VISIBLE);
				gridLayoutItems.get(i).container.setFocusable(true);
				gridLayoutItems.get(i).container
						.setBackgroundResource(R.drawable.file_manager_main_btn_selector);
				gridLayoutItems.get(i).container
						.setLayoutParams(generateGridLayoutItemLayoutParams(m_context));
				updateGridLayoutItem(list.get(i+1), gridLayoutItems.get(i));
			}else{
				gridLayoutItems.get(i).container.setVisibility(View.INVISIBLE);
			}
		}
	}

	//Get the width of screen.
	private static int getScreenWidth(Context context){
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		((Activity)context).getWindowManager()
				.getDefaultDisplay().getMetrics(mDisplayMetrics);
		int screenWidth = mDisplayMetrics.widthPixels;

		return screenWidth;
	}

	private GridLayout.LayoutParams 
			generateGridLayoutItemLayoutParams(Context context){
		int screenWidth = getScreenWidth(context);

		//Calculate the width of each item of grid layout.
		//The height is wrap_content
		GridLayout.LayoutParams params
				= new GridLayout.LayoutParams();
		params.width = (screenWidth - 2 * 10 - 15) / 4;

		return params;
	}
	
	private RelativeLayout.LayoutParams 
			generateGridLayoutItemImageViewParams(Context context){
		int w = generateGridLayoutItemLayoutParams(context).width;
		
		//The width and height of image shall be same with 
		//the width of grid layout item.
		RelativeLayout.LayoutParams params
				= new RelativeLayout.LayoutParams(w, w * 3 / 4/*aspect ratio is 4:3*/);
		params.setMargins(5, 5, 5, 5);
		
		return params;
	}

	private void updateGridLayoutItem(final FileItem item, GridLayoutItem gridLayoutItem){
		//Setting the params dynamically, so it adjust different 
		//screen resolutions.
		RelativeLayout.LayoutParams params
				= generateGridLayoutItemImageViewParams(m_context);
		gridLayoutItem.image.setLayoutParams(params);
		
		//Set the thumbnail.
		Bitmap bm = getThumbCache(item.path, gridLayoutItem.image);
		if (bm != null) {
			gridLayoutItem.image.setImageBitmap(bm);
		} else {			
			setDefaultImage(gridLayoutItem.image);			
		}

		gridLayoutItem.container.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				openFile(item);
			}
		});

		if(m_fileType == FileType.Music || m_fileType == FileType.Video )
		{
			gridLayoutItem.imagePlay.setVisibility(View.VISIBLE);
		}
		else
		{
			gridLayoutItem.imagePlay.setVisibility(View.GONE);
		}

		if(item.isNew)
		{
			gridLayoutItem.imageNew.setVisibility(View.VISIBLE);
			if( Locale.getDefault().getLanguage().equalsIgnoreCase("zh"))
			{
				gridLayoutItem.imageNew
						.setBackgroundResource(R.drawable.file_manager_new_flag_zh);
			}
			else
			{
				gridLayoutItem.imageNew
						.setBackgroundResource(R.drawable.file_manager_new_flag_us);
			}
		}
		else
		{
			gridLayoutItem.imageNew.setVisibility(View.GONE);
		}		

		gridLayoutItem.name.setText(item.name);	
		gridLayoutItem.creator.setText(item.author);
		gridLayoutItem.size.setText(Formatter.formatFileSize(m_context, item.size));
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


	private void openFile(FileItem item){
		//modify by tzhang update new status
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		File file = new File(item.path);
		
		switch (m_fileType) {
		case Music:
			intent.setDataAndType(Uri.fromFile(file), "audio/*");
			if (item.isNew)
				FileManager.getInstance().decreaseNewMusicCnt();
			break;
		case Photo:
			intent.setDataAndType(Uri.fromFile(file), "image/*");
			if (item.isNew)
				FileManager.getInstance().decreaseNewPhotoCnt();
			break;
		case Video:
			intent.setDataAndType(Uri.fromFile(file), "video/*");
			if (item.isNew)
				FileManager.getInstance().decreaseNewVideoCnt();
			break;
		default:
			intent.setDataAndType(Uri.fromFile(file), "*/*");
			if (item.isNew)
				FileManager.getInstance().decreaseNewDocCnt();
			break;
		}

		if (item.isNew) {
			item.isNew = false;
			updateDatabase(item.path);
			
		}
		//modify by tzhang end
		m_context.startActivity(intent);
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
	
	private void updateDatabase(String path) {
		SQLiteDatabase db  = null;
		try {
			db = SQLiteDatabase.openDatabase(FileManager.getInstance().getRootPath() +"/System"+"/TCloudDB.db", null,SQLiteDatabase.CREATE_IF_NECESSARY);
			String subPath = path.substring(FileManager.getInstance().getRootPath().length() + 1);

			String sql = String
					.format("UPDATE info_file2 SET file_new = '%d'  WHERE file_path = '%s'; ",
							0 , subPath);
			db.execSQL(sql);

		} catch (SQLiteException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
