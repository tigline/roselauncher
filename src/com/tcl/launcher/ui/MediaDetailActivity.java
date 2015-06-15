package com.tcl.launcher.ui;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.adapter.MovieDetailRecommandListAdapter;
import com.tcl.launcher.adapter.TvEpisodeAdapter;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.launcher.core.VoiceCommandCallback;
import com.tcl.launcher.core.VoiceControl;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.json.entry.CmdSingle;
import com.tcl.launcher.json.entry.Episode;
import com.tcl.launcher.json.entry.Movie;
import com.tcl.launcher.json.entry.TvPlay;
import com.tcl.launcher.json.entry.VodBrief;
import com.tcl.launcher.player.VideoPlayerActivity;
import com.tcl.launcher.util.ImgLoader;
import com.tcl.launcher.util.TLog;

public class MediaDetailActivity extends VoiceBarActivity {
	private static final String TAG = "MediaDetailActivity";
	
	public static final String COMMAND_MOVIE_PLAY = "MOVIE_PLAY_CURRENT";
	public static final String COMMAND_TVPLAY_PLAY = "TVPLAY_PLAY_CURRENT";
	public static final String COMMAND_TVPLAY_PLAY_EPISODE = "TVPLAY_PLAY_EPISODE";
	
	public static final String EPISODE = "episode";
	
	private ImageView mPost;
	private TextView mTitle;
	private Button mPlay;
	private Button mFavorite;

	private TextView mName;
	private TextView mActors;
	private TextView mTime;
	private TextView mArea;
	private TextView mCategory;
	private TextView mProfile;

	private ListView mListView;
	private Button mBack;

	private CmdSingle mCmdSingle;
	private ImgLoader mImgLoader;
	
	private GridView mEpisodesView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_result_detail);

		mImgLoader = ImgLoader.getInstance(mApplication);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mCmdSingle = (CmdSingle) getIntent().getExtras().getSerializable(VoiceControl.CMD_PAGE);
		}
		findViews();
		setViews();
	}
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		mApplication.setmVoiceCommandCallback(mCommandCallback);
	}



	@Override
	protected void onStop() {
		super.onStop();
	}



	private void setViews() {
		if (mCmdSingle != null) {
			String command = mCmdSingle.getCommand();
			if (command.startsWith("MOVIE")) {
				Movie movie = mCmdSingle.getMovie();
				mName.setText(movie.getCnTitle());
				mActors.setText(getString(R.string.info_actors) + movie.getActors());
				mTime.setText(getString(R.string.info_times) + movie.getReleasedate());
				mArea.setText(getString(R.string.info_area) + movie.getAreas());
				mCategory.setText(getString(R.string.info_type) + movie.getTypes());
				mProfile.setText(movie.getIntro());
				mImgLoader.loadImage(movie.getImgvUrl(), mPost, R.drawable.detail_post_default,
						R.drawable.detail_post_default);
				List<VodBrief> recommandList = movie.getRecommends();
				MovieDetailRecommandListAdapter<VodBrief> adapter = new MovieDetailRecommandListAdapter<>(
						recommandList, this);
				mListView.setAdapter(adapter);
				mEpisodesView.setVisibility(View.GONE);
			} else {
				TvPlay tvPlay = mCmdSingle.getTvPlay();
				mName.setText(tvPlay.getCnTitle());
				mActors.setText(getString(R.string.info_actors) + tvPlay.getActors());
				mTime.setText(getString(R.string.info_times) + tvPlay.getReleasedate());
				mArea.setText(getString(R.string.info_area) + tvPlay.getAreas());
				mCategory.setText(getString(R.string.info_type) + tvPlay.getTypes());
				mProfile.setText(tvPlay.getIntro());
				mImgLoader.loadImage(tvPlay.getImgvUrl(), mPost, R.drawable.detail_post_default,
						R.drawable.detail_post_default);
				List<VodBrief> recommandList = tvPlay.getRecommends();
				MovieDetailRecommandListAdapter<VodBrief> adapter = new MovieDetailRecommandListAdapter<>(
						recommandList, this);
				mListView.setAdapter(adapter);
//				tvPlay.getEpisodes()
				mEpisodesView.setVisibility(View.VISIBLE);
				TvEpisodeAdapter<Episode> tvAdapter = new TvEpisodeAdapter<>(tvPlay.getEpisodes(), this);
				mEpisodesView.setAdapter(tvAdapter);
			}

		}
		mPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TLog.i(TAG, "play onclick : "
						+ mCmdSingle.getMovie().getVideoSource().get(0).getPlayUrl());
				new Thread(new Runnable() {
					@Override
					public void run() {
						Bundle b = new Bundle();
						b.putSerializable(VoiceControl.CMD_PAGE, mCmdSingle);
						openActivity(VideoPlayerActivity.class, b);
					}
				}).start();
			}
		});
	}

	private void findViews() {
		mPost = (ImageView) findViewById(R.id.media_detail_post);

		mTitle = (TextView) findViewById(R.id.media_result_title);
		mPlay = (Button) findViewById(R.id.media_detail_play);
		mBack = (Button) findViewById(R.id.back);
		mFavorite = (Button) findViewById(R.id.media_detail_favorite);

		mName = (TextView) findViewById(R.id.media_detail_name);
		mActors = (TextView) findViewById(R.id.media_detail_actors);
		mTime = (TextView) findViewById(R.id.media_detail_time);
		mArea = (TextView) findViewById(R.id.media_detail_area);
		mCategory = (TextView) findViewById(R.id.media_detail_category);
		mProfile = (TextView) findViewById(R.id.media_detail_profile);

		mListView = (ListView) findViewById(R.id.media_detail_recommand_list);
		mEpisodesView = (GridView) findViewById(R.id.media_detail_tv_serial);
		// List<Movie> list = Test.getTestList();
		// MovieDetailRecommandListAdapter<Movie> adatper = new
		// MovieDetailRecommandListAdapter<>(list, this);
		// mListView.setAdapter(adatper);
		mTitle.setText(R.string.movie_recommand);
	}
	
	VoiceCommandCallback mCommandCallback = new VoiceCommandCallback() {
		
		@Override
		public boolean onLocalCommandDeal(CmdCtrl cmdCtrl) {
			if(COMMAND_MOVIE_PLAY.equals(cmdCtrl.getCommand())){
				Bundle b = new Bundle();
				CmdSingle newSingle = CmdSingle.Copy(mCmdSingle);
				newSingle.setCommand(CommandConstants.MOVIE_PLAY);
				b.putSerializable(VoiceControl.CMD_PAGE, newSingle);
				openActivity(VideoPlayerActivity.class, b);
				return false;
			}else if(COMMAND_TVPLAY_PLAY.equals(cmdCtrl.getCommand()) || COMMAND_TVPLAY_PLAY_EPISODE.equals(cmdCtrl.getCommand())){
				Bundle b = new Bundle();
				CmdSingle newSingle = CmdSingle.Copy(mCmdSingle);
				
				if(COMMAND_TVPLAY_PLAY_EPISODE.equals(cmdCtrl.getCommand())){
					b.putString(EPISODE, cmdCtrl.getPars().get(EPISODE));
				}
				
				newSingle.setCommand(CommandConstants.TVPLAY_PLAY);
				b.putSerializable(VoiceControl.CMD_PAGE, newSingle);
				openActivity(VideoPlayerActivity.class, b);
				return false;
			}
			return true;
		}
	};
}
