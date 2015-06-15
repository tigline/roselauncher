package com.tcl.launcher.ui.homecloud.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.core.VoiceCommandCallback;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.ui.homecloud.Classlib.Utils.SysUtils;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileItem;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileMessage;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels.FileType;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels.MediaFile;
import com.tcl.launcher.ui.homecloud.FileManager.Receiver.FileEventReceiver;
import com.tcl.launcher.ui.homecloud.FileManager.Receiver.FileStorageReceiver;
import com.tcl.launcher.ui.homecloud.FileManager.UI.PhotoAndVideoListViewAdapter;
import com.tcl.launcher.ui.homecloud.FileManager.UI.SortListViewAdapter;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileManager;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileSortHelper;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileSortHelper.SortMethod;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileUtils;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.IFileEventListener;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.Utils;
import com.tcl.launcher.util.TLog;

public class FileSearchActivity extends VoiceBarActivity implements
		OnItemClickListener,IFileEventListener {
	private static final String TAG = "FileSearchActivity";
	private GridView m_gridview;
	private FileType m_fileType;
	private FilesGridViewAdapter m_adapter;
	private FileEventReceiver m_fileReceiver;
	private final static String TITLE_SEPARATOR = " > ";
	private TextView m_txTitle;
	private PopupWindow m_sortWindow;
	private String[] m_sortTypes;
	private ListView m_sortTypeListview;
	private SortListViewAdapter m_sortAdapter;

	//Add-by-feiping.li@tcl.com-begin.
	private ListView musicAndVideoListView;
	private boolean isMusicAndVideoCategory = false;
	private PhotoAndVideoListViewAdapter musicAndVideoListViewAdapter;
	//Add-by-feiping.li@tcl.com-end.

	private static final int DEF_WIDTH = 500;
	private static final int DEF_HEIGHT = 255;

	private static final int SORT_TYPE_NAME = 0;
	private static final int SORT_TYPE_SIZE = 1;
	private static final int SORT_TYPE_DATE = 2;
	private static final int SORT_TYPE_TYPE = 3;
	private static final int SORT_TYPE_MAX = SORT_TYPE_TYPE;

	private Button m_menuBtn;
	private LinearLayout m_main_view;
	private FileStorageReceiver m_sReceiver;
	
	public List<FileItem> m_photoList = new ArrayList<FileItem>();
	public List<FileItem> m_musicList = new ArrayList<FileItem>();
	public List<FileItem> m_videoList = new ArrayList<FileItem>();
	public List<FileItem> m_docList = new ArrayList<FileItem>();
	
	public int m_newMusicCnt;
	public int m_newPhotoCnt;
	public int m_newVideoCnt;
	public int m_newDocCnt;
	private ArrayList<String> filePathList = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homecloud_activity_file_manager_catagory);
		m_main_view = (LinearLayout) findViewById(R.id.file_category_main_view);
		// m_fileType = FileModels.getFileType(this.getIntent().getType());
		m_fileType = FileType.Photo;
		initGridView();
		//Add-by-feiping.li@tcl.com-begin.
//		if(FileModels.FileType.Photo.equals(m_fileType)
//				|| FileModels.FileType.Video.equals(m_fileType)){
//			isMusicAndVideoCategory = true;
//			initListView();
//		}else{
//			initGridView();
//		}
		//Add-by-feiping.li@tcl.com-end.

		registerFileMsgReceiver();
		initSortWindow();
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.mApplication.setmVoiceCommandCallback(new VoiceCommandCallback() {
			
			@Override
			public boolean onLocalCommandDeal(CmdCtrl cmdCtrl) {
				// TODO Auto-generated method stub
				String localCmd = cmdCtrl.getCommand();
				TLog.i(TAG, "localCmd ----------------->"+localCmd);
				if(localCmd.equals("HOME_CLOUD_PLAY_SELECT_PHOTO"))
				{
//					TLog.i(TAG, "accept HOME_CLOUD_PLAY_SELECT_PHOTO!!!!");
//					filePathList = new ArrayList<String>();
//					for(int i = 0;i<m_photoList.size();i++)
//					{
//						filePathList.add(m_photoList.get(i).path);
//					}
//					Intent intent = new Intent(FileSearchActivity.this, ImgSwitchActivity.class);
//					intent.putStringArrayListExtra("filePathList", filePathList);
//					startActivity(intent);
					
					TLog.i(TAG, "accept HOME_CLOUD_PLAY_LAST_PHOTO!!!");
					filePathList = new ArrayList<String>();
					String path = Environment.getExternalStorageDirectory()  
			                + "/DCIM/"+ "/Camera/";		
					getFiles(path);
					
					Bundle b = new Bundle();
					b.putSerializable("CmdPage", cmdCtrl);
					b.putStringArrayList("filePathList", filePathList);
					FileSearchActivity.this.mApplication.mVoiceControl.openActivity(FileSearchActivity.this.mApplication.getLastActivity(),
							ImgSwitchActivity.class, b);
					
//					Intent intent = new Intent(FileSearchActivity.this, ImgSwitchActivity.class);
//					intent.putStringArrayListExtra("filePathList", filePathList);
//					startActivity(intent);
					return false;
				}
				if(localCmd.equals("HOME_CLOUD_SEARCHE_QUIT"))
				{
					TLog.i(TAG, "accept HOME_CLOUD_SEARCHE_QUIT!!!!");
					if(ImgSwitchActivity.m_self != null)
					{
						ImgSwitchActivity.m_self.finish();
					}
					
					FileSearchActivity.this.finish();
					return false;
				}
				return true;
			}
		});
	}
	private void getFileItemList(String string) {
        // TODO Auto-generated method stub 
        File file = new File(string);
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            String name = files[j].getName();
            if (files[j].isDirectory()) {
                String dirPath = files[j].toString().toLowerCase(); 
                System.out.println(dirPath);
                getFiles(dirPath + "/");
            } else if (files[j].isFile() & name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".bmp") || name.endsWith(".gif") || name.endsWith(".jpeg")) {
//                System.out.println("FileName===" + files[j].getName());
//            	filePathList.add(string+files[j].getName());
            	FileItem temp = new FileItem();
            	temp.author = "qinbl";
            	temp.isNew = true;
            	temp.name = files[j].getName();
            	temp.path =string+files[j].getName();
            	temp.type = FileType.Photo;
            	temp.size = files[j].lastModified();
            	temp.uploadtime = "2015-06-06";
            	
            	m_photoList.add(temp);
            }
        }         
    }
	private void getFiles(String string) {
        // TODO Auto-generated method stub 
        File file = new File(string);
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            String name = files[j].getName();
            if (files[j].isDirectory()) {
                String dirPath = files[j].toString().toLowerCase(); 
                System.out.println(dirPath);
                getFiles(dirPath + "/");
            } else if (files[j].isFile() & name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".bmp") || name.endsWith(".gif") || name.endsWith(".jpeg")) {
//                System.out.println("FileName===" + files[j].getName());
            	filePathList.add(string+files[j].getName());
               
            }
        }         
    }
	@Override
	protected void onResume() {
		super.onResume();
		initSortBtn();
		initTitle();
		updateUI();
		
		m_sReceiver = new FileStorageReceiver(this);
		m_sReceiver.registerReceiver();	
	}

	@Override
	protected void onPause() {
		super.onPause();
		m_sReceiver.unregisterReceiver();	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(m_fileReceiver);
	}

	private void initSortBtn() {
		m_menuBtn = (Button) this.findViewById(R.id.btn_seacher);
		if(FileModels.FileType.Photo.equals(m_fileType)
				|| FileModels.FileType.Video.equals(m_fileType)){
			m_menuBtn.setVisibility(View.GONE);
		
		}else{
			m_menuBtn.setBackgroundResource(R.drawable.file_manager_sort_selector);
			m_menuBtn.setVisibility(View.VISIBLE);
		}		
	
		m_menuBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showSortWindow();
			}
		});
	}

	private void initTitle() {
		m_txTitle = (TextView) this.findViewById(R.id.prompt_dir);
		String strTitle;
		switch (m_fileType) {
		case Music:
			strTitle = getString(R.string.home_page) + TITLE_SEPARATOR
					+ getString(R.string.all_music);
			break;
		case Photo:
			strTitle = getString(R.string.home_page) + TITLE_SEPARATOR
					+ getString(R.string.all_photo);
			break;
		case Video:
			strTitle = getString(R.string.home_page) + TITLE_SEPARATOR
					+ getString(R.string.all_video);
			break;
		default:
			strTitle = getString(R.string.home_page) + TITLE_SEPARATOR
					+ getString(R.string.all_other_document);
			break;
		}

		m_txTitle.setText(strTitle);
	}

	private void initGridView() {
//		ScanFilesFromDatabase("2015-05",FileType.Photo);
		
		filePathList = new ArrayList<String>();
		String path = Environment.getExternalStorageDirectory()  
                + "/DCIM/"+ "/Camera/";		
		getFileItemList(path);
		
		setContentView(R.layout.homecloud_activity_file_manager_catagory);
		m_gridview = (GridView) findViewById(R.id.filesgridView);
		m_adapter = new FilesGridViewAdapter(this, m_fileType, m_photoList);
		m_gridview.setAdapter(m_adapter);
		m_gridview.setOnItemClickListener(this);
		m_gridview.requestFocus();

	}
	
	private void ScanFilesFromDatabase(String param,FileType fileType)
	{		
		SQLiteDatabase db  = null;
		SysUtils ss = new SysUtils();
		String m_rootPath = SysUtils.Instance().getBoaVirtualRootDir();
		try {
			db = SQLiteDatabase.openDatabase(m_rootPath +"/System"+"/TCloudDB.db", null,SQLiteDatabase.CREATE_IF_NECESSARY);
			String strSql = "select * from info_file2 where file_topdir = '"+fileType+"' and file_uploadtime like '%"+param+"%';";

			Cursor cursor = db.rawQuery(strSql, null);
		
			while (cursor != null && cursor.moveToNext()) {
				FileItem item = new FileItem();	
				item.path = FileUtils.combinePath(m_rootPath, cursor.getString(7));
				item.name = cursor.getString(3);
				item.size  = cursor.getLong(5);
				item.uploadtime = cursor.getString(10);	
				if(cursor.getInt(13)== 1)
				{
					item.isNew = true;	
				}		
				
				item.type = MediaFile.getFileType(item.path);			
				
				switch (item.type) {
				case Music:					
					if(cursor.getInt(13)== 1)
					{
						item.isNew = true;	
						this.m_newMusicCnt++;
					}					
					m_musicList.add(item);
					break;
				case Photo:
					if(cursor.getInt(13)== 1)
					{
						item.isNew = true;	
						this.m_newPhotoCnt++;
					}		
					m_photoList.add(item);
					break;
				case Video:
					if(cursor.getInt(13)== 1)
					{
						item.isNew = true;	
						this.m_newVideoCnt++;
					}		
					m_videoList.add(item);
					break;
				default:
					if(cursor.getInt(13)== 1)
					{
						item.isNew = true;	
						this.m_newDocCnt++;
					}		
					m_docList.add(item);
					break;
				}
				
			}
			cursor.close();

		} catch (SQLiteException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}		
	}
	//Add-by-feiping.li@tcl.com-begin.
//	private void initListView(){
//		setContentView(R.layout.activity_photo_and_video_category); 
//		musicAndVideoListView = (ListView) findViewById(R.id.listView);	
//		musicAndVideoListView.setItemsCanFocus(true);
//		musicAndVideoListViewAdapter = 
//				new PhotoAndVideoListViewAdapter(this, m_fileType, getFilesData());
//		musicAndVideoListView.setAdapter(musicAndVideoListViewAdapter);
//	}
	//Add-by-feiping.li@tcl.com-end.

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		FileItem item = getFilesData().get(position);
		openMediaFile(item);
	}

	private List<FileItem> getFilesData() {
		List<FileItem> files = null;
		switch (m_fileType) {

		case Music:
			files = FileManager.getInstance().getMusicFiles();
			break;
		case Photo:
			files = FileManager.getInstance().getPhotoFiles();
			break;
		case Video:
			files = FileManager.getInstance().getVideoFiles();
			break;
		default:
			files = FileManager.getInstance().getDocFiles();
			break;
		}

		return files;
	}

	private void openMediaFile(FileItem item) {
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

		startActivity(intent);
	}

	public void updateUI() {
		/*Hide-by-feiping.li@tcl.com
		m_adapter.notifyDataSetChanged();
		
		if (getFilesData().isEmpty()) {
			if (Locale.getDefault().getLanguage().equalsIgnoreCase("zh")) {
				m_gridview
						.setBackgroundResource(R.drawable.icon_empty_folder_zh);
			} else {
				m_gridview
						.setBackgroundResource(R.drawable.icon_empty_folder_us);
			}
		} else {
			m_gridview.setBackgroundColor(this.getResources().getColor(
					R.color.color_bg));
		}
		*/
		
		//Add-by-feiping.li@tcl.com-begin.
		if(!isMusicAndVideoCategory)
		{
			m_adapter.notifyDataSetChanged();
	
			if (getFilesData().isEmpty()) {
				if (Locale.getDefault().getLanguage().equalsIgnoreCase("zh")) {
					m_gridview
							.setBackgroundResource(R.drawable.icon_empty_folder_zh);
				} else {
					m_gridview
							.setBackgroundResource(R.drawable.icon_empty_folder_us);
				}
			} else {
				m_gridview.setBackgroundColor(this.getResources().getColor(
						R.color.homecloud_color_bg));
			}
		}else{
			musicAndVideoListViewAdapter.notifyDataSetChanged();
			if (getFilesData().isEmpty()) {
				if (Locale.getDefault().getLanguage().equalsIgnoreCase("zh")) {
					musicAndVideoListView
							.setBackgroundResource(R.drawable.icon_empty_folder_zh);
				} else {
					musicAndVideoListView
							.setBackgroundResource(R.drawable.icon_empty_folder_us);
				}
			} else {
				musicAndVideoListView.setBackgroundColor(this.getResources().getColor(
						R.color.homecloud_color_bg));
			}
		}
		//Add-by-feiping.li@tcl.com-end.
	}

	private void registerFileMsgReceiver() {
		m_fileReceiver = new FileEventReceiver(this);
		this.registerReceiver(m_fileReceiver, new IntentFilter(
				FileMessage.MSG_FILE_SCAN_DONE));
	}


	public void onFileScanDone() {
		//team solution
		
		/*Hide-by-feping.li@tcl.com.
		m_adapter = new FilesGridViewAdapter(this, m_fileType, getFilesData());
		m_gridview.setAdapter(m_adapter);	
		updateUI();
		*/

		refreshUI();//Add-by-feiping.li@tcl.com.
	}
	

	//Add-by-feiping.li@tcl.com-begin.
	private void refreshUI(){
		if(!isMusicAndVideoCategory){
			m_adapter = new FilesGridViewAdapter(this, m_fileType, getFilesData());
			m_gridview.setAdapter(m_adapter);	
		}else{
			musicAndVideoListViewAdapter = 
					new PhotoAndVideoListViewAdapter(this, m_fileType, getFilesData());
			musicAndVideoListView.setAdapter(musicAndVideoListViewAdapter);
		}
		updateUI();
	}
	//Add-by-feiping.li@tcl.com-end.


	private void initSortWindow() {
		if (m_sortTypes == null) {
			m_sortTypes = new String[4];
			m_sortTypes[SORT_TYPE_NAME] = getString(R.string.name);
			m_sortTypes[SORT_TYPE_SIZE] = getString(R.string.size);
			m_sortTypes[SORT_TYPE_DATE] = getString(R.string.date);
			m_sortTypes[SORT_TYPE_TYPE] = getString(R.string.type);
		}

		if (m_sortWindow == null) {
			View contents = LayoutInflater.from(this).inflate(
					R.layout.homecloud_menu_sort_view, null);
			m_sortTypeListview = (ListView) contents
					.findViewById(R.id.menu_sort_listview);
			m_sortTypeListview.setItemsCanFocus(true);
			m_sortAdapter = new SortListViewAdapter(this, m_sortTypes);
			m_sortTypeListview.setAdapter(m_sortAdapter);
			SortMethod sType = FileSortHelper.getSortTypeByName(Utils
					.getSortType());
			int position;
			switch (sType) {
			case name:
				position = 0;
				break;
			case size:
				position = 1;
				break;
			case date:
				position = 2;
				break;
			case type:
				position = 3;
				break;
			default:
				position = 0;
				break;

			}
			m_sortAdapter.select(position);
			m_sortTypeListview
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							m_sortAdapter.select(position);
							sortFileData(position);
							updateUI();
							m_sortWindow.dismiss();
						}
					});

			m_sortWindow = new PopupWindow(contents, DEF_WIDTH, DEF_HEIGHT);
			m_sortWindow.setFocusable(true);
			m_sortWindow.setOutsideTouchable(true);
			m_sortWindow.setBackgroundDrawable(new BitmapDrawable());
			m_sortWindow.setTouchable(true);
		}
	}

	private void showSortWindow() {
		int location[] = new int[2];
		m_menuBtn.getLocationOnScreen(location);
		int x = m_main_view.getWidth() - DEF_WIDTH;
		int y = location[1] + m_menuBtn.getHeight();
		m_sortWindow.showAtLocation(m_main_view, Gravity.TOP | Gravity.RIGHT,
				x, y);
	}

	private void sortFileData(int position) {

		if (position >= SORT_TYPE_NAME && position <= SORT_TYPE_MAX) {

			FileSortHelper sort_helper = new FileSortHelper();

			switch (position) {
			case SORT_TYPE_NAME:
				sort_helper.setSortMethod(SortMethod.name);
				Utils.setSortType(FileSortHelper.name_sort_string);
				break;

			case SORT_TYPE_SIZE:
				sort_helper.setSortMethod(SortMethod.size);
				Utils.setSortType(FileSortHelper.size_sort_string);
				break;

			case SORT_TYPE_DATE:
				sort_helper.setSortMethod(SortMethod.date);
				Utils.setSortType(FileSortHelper.date_sort_string);
				break;

			case SORT_TYPE_TYPE:
				sort_helper.setSortMethod(SortMethod.type);
				Utils.setSortType(FileSortHelper.type_sort_string);
				break;

			default:
				break;
			}

			sort_helper.sort(getFilesData());
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
