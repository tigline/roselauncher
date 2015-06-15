package com.tcl.launcher.interaction.impl;

import android.os.Handler;

import com.tcl.launcher.core.VoiceControl;
import com.tcl.launcher.interaction.IInteractionRequest;
import com.tcl.launcher.util.ClientRequestUtil;
import com.tcl.launcher.util.Utils;

public class IInteractionRequestImpl implements IInteractionRequest {

	@Override
	public void question(final String server_url, final VoiceControl control) {
		if (Utils.isNotEmpty(server_url)) {
			new Thread() {
				@Override
				public void run() {
					ClientRequestUtil.executeHttpGet(server_url, control);
				}
			}.start();
		}
	}
}
