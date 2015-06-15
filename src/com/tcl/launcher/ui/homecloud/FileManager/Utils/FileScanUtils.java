package com.tcl.launcher.ui.homecloud.FileManager.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.tcl.launcher.ui.homecloud.FileManager.Model.FileItem;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels.FileType;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels.MediaFile;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileSortHelper.SortMethod;

public class FileScanUtils extends Thread {

	public List<FileItem> m_photoList = new ArrayList<FileItem>();
	public List<FileItem> m_musicList = new ArrayList<FileItem>();
	public List<FileItem> m_videoList = new ArrayList<FileItem>();
	public List<FileItem> m_docList = new ArrayList<FileItem>();
	public int m_newMusicCnt;
	public int m_newPhotoCnt;
	public int m_newVideoCnt;
	public int m_newDocCnt;
	
	private String m_rootPath;
	private IFileEventListener m_fileEvent;
	

	public FileScanUtils(String path, IFileEventListener listener) {		
		m_rootPath = path;
		m_fileEvent = listener;	
	}
	
	private void scanAllFiles() {	
		ScanFilesFromDatabase();
		sortFileList(FileSortHelper.getSortTypeByName(Utils.getSortType()));
	}

	private void clearData() {
		m_photoList.clear();
		m_musicList.clear();
		m_videoList.clear();
		m_docList.clear();
		m_newMusicCnt = 0;
		m_newPhotoCnt = 0;
		m_newVideoCnt = 0;
		m_newDocCnt = 0;
	}

	@Override
	public void run() {
		clearData();	
		scanAllFiles();
		m_fileEvent.onFileScanDone();
	}
	
	private void sortFileList(SortMethod s)
	{
		FileSortHelper sort_helper = new FileSortHelper();
		sort_helper.setSortMethod(s);
		sort_helper.sort(this.m_musicList);
		sort_helper.sort(this.m_photoList);		
		sort_helper.sort(this.m_videoList);		
		sort_helper.sort(this.m_docList);		
	}
	
	private void ScanFilesFromDatabase()
	{		
		SQLiteDatabase db  = null;
		try {
			db = SQLiteDatabase.openDatabase(m_rootPath +"/System"+"/TCloudDB.db", null,SQLiteDatabase.CREATE_IF_NECESSARY);
			String strSql = "select * from  info_file2";
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
	
	
	public Map<String, FileItem> getFileListByFolder(String path) {
		 Map<String, FileItem> fileMaps = new HashMap<String, FileItem>();
		if (path.isEmpty())			
		{
			String defaultFolders[] = {"Music", "Document", "Photo", "Video"};
			for(int i = 0; i< defaultFolders.length; i++)
			{
				FileItem item = new FileItem();			
				item.type = FileType.Folder;
				item.name = defaultFolders[i];
				item.path =  FileUtils.combinePath(m_rootPath, item.name);
				fileMaps.put(item.path, item);
			}			
		}
		else
		{		
			if(path.startsWith(m_rootPath))
			{
				String pathTemp = path.substring(m_rootPath.length()+1);			
				SQLiteDatabase db = null;
				try {
					db = SQLiteDatabase.openDatabase(m_rootPath +"/System"+"/TCloudDB.db", null,SQLiteDatabase.CREATE_IF_NECESSARY);
					String strSql = "select * from  info_file2";
					Cursor cursor = db.rawQuery(strSql, null);
					while (cursor != null && cursor.moveToNext()) {
						
						String subPath = cursor.getString(7);
						if(subPath.contains(pathTemp))
						{
							int nIndex = subPath.lastIndexOf(File.separator);
							if(nIndex > 0)
							{
								FileItem item = new FileItem();			
							
								String subFolderPath = subPath.substring(0, nIndex);
								if(subFolderPath.equalsIgnoreCase(pathTemp))
								{
									
									//find item
									String fileName = subPath.substring(subFolderPath.length()+1);															
									item.type = MediaFile.getFileType(fileName);
									item.name = fileName;
									item.path =  FileUtils.combinePath(path, item.name);
									fileMaps.put(item.path, item);							
								
								}
								else
								{	
									//find folder
									subFolderPath = subPath.substring(pathTemp.length()+1);	
									nIndex = subFolderPath.indexOf(File.separator);
									if(nIndex >0)
									{
										subFolderPath = subFolderPath.substring(0, nIndex);
										
									}								
									item.type = FileType.Folder;
									item.name = subFolderPath;
									item.path =  FileUtils.combinePath(path, item.name);
									fileMaps.put(item.path, item);	
								}								
								
							}
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
		
		}
		return fileMaps;
	}
}
