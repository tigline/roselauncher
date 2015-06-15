package com.tcl.launcher.ui.homecloud.FileManager.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tcl.launcher.ui.homecloud.FileManager.Model.FileMessage;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.IFileEventListener;

public class FileEventReceiver extends BroadcastReceiver {

	private IFileEventListener m_event;

	public FileEventReceiver(IFileEventListener event) {
		m_event = event;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(FileMessage.MSG_FILE_SCAN_DONE)) {
			m_event.onFileScanDone();
		}		

	}
}
