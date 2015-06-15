package com.tcl.launcher.ui.fragment;


import com.tcl.launcher.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MediaFragment extends Fragment {
	public MediaFragment() {
	}

	public static MediaFragment getInstance() {
		MediaFragment fragment = new MediaFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_media, container, false);
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	
}
