package com.tcl.launcher.ui.homecloud.FileManager.Utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import com.tcl.launcher.ui.homecloud.Classlib.Utils.SysUtils;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileItem;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileMessage;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileSortHelper.SortMethod;

public class FileManager implements IFileEventListener {

	private static FileManager m_instance = null;
	private FileScanUtils m_fileScan;
	private List<FileItem> m_musicList = new ArrayList<FileItem>();
	private List<FileItem> m_photoList = new ArrayList<FileItem>();
	private List<FileItem> m_videoList = new ArrayList<FileItem>();
	private List<FileItem> m_docList = new ArrayList<FileItem>();

	private long m_totalMusicSize;
	private long m_totalPhotoSize;
	private long m_totalVideoSize;
	private long m_totalDocSize;

	private int m_newMusicCnt;
	private int m_newPhotoCnt;
	private int m_newVideoCnt;
	private int m_newDocCnt;

	private String m_rootPath;

	private RemoteFileReceiver m_receiver;

	public FileManager() {
		registerFileMsgReceiver();
	}

	public static FileManager getInstance() {
		if (m_instance == null) {
			m_instance = new FileManager();
		}
		return m_instance;
	}

	public void init() {
		m_rootPath = SysUtils.Instance().getBoaVirtualRootDir();
		m_fileScan = new FileScanUtils(m_rootPath, this);
		ScanAllFiles();

	}

	public void reInit() {
		clearDatAndFilemon();
		init();
	}

	private void clearDatAndFilemon() {
		m_musicList.clear();
		m_photoList.clear();
		m_videoList.clear();
		m_docList.clear();

		m_totalMusicSize = 0;
		m_totalPhotoSize = 0;
		m_totalVideoSize = 0;
		m_totalDocSize = 0;

		m_newMusicCnt = 0;
		m_newPhotoCnt = 0;
		m_newVideoCnt = 0;
		m_newDocCnt = 0;

	}

	@Override
	public void onFileScanDone() {
		m_musicList = m_fileScan.m_musicList;
		m_photoList = m_fileScan.m_photoList;
		m_videoList = m_fileScan.m_videoList;
		m_docList = m_fileScan.m_docList;		

		m_newMusicCnt = m_fileScan.m_newMusicCnt;
		m_newPhotoCnt = m_fileScan.m_newPhotoCnt;
		m_newVideoCnt = m_fileScan.m_newVideoCnt;
		m_newDocCnt = m_fileScan.m_newDocCnt;

		m_totalMusicSize = calcTotalFileSize(m_musicList);
		m_totalPhotoSize = calcTotalFileSize(m_photoList);
		m_totalVideoSize = calcTotalFileSize(m_videoList);
		m_totalDocSize = calcTotalFileSize(m_docList);

		Utils.sendBroadcastMessage(FileMessage.MSG_FILE_SCAN_DONE);
	}
	
	public String getRootPath()
	{
		return m_rootPath;
	}

	public List<FileItem> getMusicFiles() {
		return m_musicList;
	}

	public List<FileItem> getPhotoFiles() {
		return m_photoList;
	}

	public List<FileItem> getVideoFiles() {
		return m_videoList;
	}

	public List<FileItem> getDocFiles() {
		return m_docList;
	}

	public long getTotalMusicSize() {
		return m_totalMusicSize;
	}

	public long getTotalPhotoSize() {
		return m_totalPhotoSize;
	}

	public long getTotalVideoSize() {
		return m_totalVideoSize;
	}

	public long getTotalDocSize() {
		return m_totalDocSize;
	}

	public int getNewMusicCnt() {
		return m_newMusicCnt;
	}

	public int getNewPhotoCnt() {
		return m_newPhotoCnt;
	}

	public int getNewVideoCnt() {
		return m_newVideoCnt;
	}

	public int getNewDocCnt() {
		return m_newDocCnt;
	}

	public void decreaseNewMusicCnt() {
		m_newMusicCnt--;
	}

	public void decreaseNewPhotoCnt() {
		m_newPhotoCnt--;
	}

	public void decreaseNewVideoCnt() {
		m_newVideoCnt--;
	}

	public void decreaseNewDocCnt() {
		m_newDocCnt--;
	}

	public List<FileItem> getFileListByFolder(String path) {
		List<FileItem> fileList = new ArrayList<FileItem>();
		Map<String, FileItem> fileMaps = m_fileScan.getFileListByFolder(path);
		Iterator<String> it = fileMaps.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			fileList.add(fileMaps.get(key));

		}

		if (fileList != null && fileList.size() > 0) {
			FileSortHelper sort_helper = new FileSortHelper();
			sort_helper.setSortMethod(SortMethod.type);
			sort_helper.sort(fileList);
		}
		return fileList;
	}

	private void ScanAllFiles() {
		m_fileScan.start();
	}

	private long calcTotalFileSize(List<FileItem> files) {
		long totalSize = 0;
		for (int nIndex = 0; nIndex < files.size(); nIndex++) {
			totalSize += files.get(nIndex).size;
		}
		return totalSize;
	}

	private class RemoteFileReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(FileMessage.MSG_REMOTE_FILE_CHANGED)) {
				Bundle bd = intent.getExtras();

				boolean isFile = bd.getString("isfile").equalsIgnoreCase("1");
				String path = bd.getString("filePath");
				String op = bd.getString("operaType");

				if (isFile && op.equalsIgnoreCase("add")) {
					updateDatabase(path);
				}

				reInit();

			}

		}
	}

	private void registerFileMsgReceiver() {
		m_receiver = new RemoteFileReceiver();
//		ApplicationExceptionCatch
//				.getInstance()
//				.getApplicationContext()
//				.registerReceiver(m_receiver,
//						new IntentFilter(FileMessage.MSG_REMOTE_FILE_CHANGED));

	}

	private void updateDatabase(String path) {
		
		SQLiteDatabase db  = null;
		try {
			db = SQLiteDatabase.openDatabase(m_rootPath +"/System"+"/TCloudDB.db", null, SQLiteDatabase.CREATE_IF_NECESSARY);
			String subPath = path.substring(m_rootPath.length() + 1);

			String sql = String
					.format("UPDATE info_file2 SET file_new = '%d'  WHERE file_path = '%s'; ",
							1, subPath);
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
