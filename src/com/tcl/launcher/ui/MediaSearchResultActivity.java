package com.tcl.launcher.ui;

import java.util.List;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.Test;
import com.tcl.launcher.adapter.MovieListAdapter;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.core.VoiceControl;
import com.tcl.launcher.json.entry.CmdList;
import com.tcl.launcher.json.entry.VodBrief;

public class MediaSearchResultActivity extends VoiceBarActivity {
	private static final String TAG = "MediaSearchResultActivity";

	private GridView mMovieGridView;
	private GridView mTagGridView;
	private GridView mTimeGridView;

	private TextView mTitle;
	private Button mNext;
	private Button mPre;
	private Button mBack;

	private CmdList mCmdList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_result_list);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mCmdList = (CmdList) getIntent().getExtras().getSerializable(VoiceControl.CMD_PAGE);
		}
		findViews();
		setViews();
	}

	private void findViews() {
		mMovieGridView = (GridView) findViewById(R.id.media_result_gridview);
		mTagGridView = (GridView) findViewById(R.id.media_result_tag_grid);
		mTimeGridView = (GridView) findViewById(R.id.media_result_time_grid);

		mTitle = (TextView) findViewById(R.id.media_result_title);

		// mNext = (Button) findViewById(R.id.next);
		// mPre = (Button) findViewById(R.id.pre);
		// mBack = (Button) findViewById(R.id.back);
//		mMovieGridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				ImageView imgview = (ImageView) view.findViewById(R.id.media_result_gridview_list_item_img);
//				if(imgview != null){
//					imgview.setVisibility(View.INVISIBLE);
//				}
//			}
//		});
	}

	private void setViews() {
		if (mCmdList != null) {
			mTitle.setText(mCmdList.getQuestion());

			List<VodBrief> list = mCmdList.getVods();
			MovieListAdapter<VodBrief> adapter = new MovieListAdapter<>(list, this);
			mMovieGridView.setAdapter(adapter);
		}

		List<String> tagList = Test.getTagList();
		ArrayAdapter<String> tagAdapter = new ArrayAdapter<>(this, R.layout.media_tag_list_item,
				R.id.media_result_tag_list_text, tagList);
		mTagGridView.setAdapter(tagAdapter);

		List<String> timeList = Test.getTimeList();
		ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, R.layout.media_tag_list_item,
				R.id.media_result_tag_list_text, timeList);
		mTimeGridView.setAdapter(timeAdapter);
	}
}
