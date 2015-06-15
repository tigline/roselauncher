package com.tcl.launcher.widget;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcl.launcher.R;
import com.tcl.launcher.entry.HostItem;

public class LauncherHost extends LinearLayout {
	private static final String TAG = "LauncherHost";
	
	public static final int INDEX_MEDIA = 1;
	public static final int INDEX_GAME = 2;
	public static final int INDEX_EDUCATION = 3;
	public static final int INDEX_APP_MARKET = 4;
	public static final int INDEX_SMART_HOME = 5;
	public static final int INDEX_MY_TV = 6;

	private List<HostItem> mHostList;
	private Context mContext;
	private onChooseListener mChooseListener;
	private TextView currentSelectedView;

	public LauncherHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	private void refreshViews() {
		if (mHostList != null && mHostList.size() > 0) {
			removeAllViews();
			int size = mHostList.size();
			for (int i = 0; i < size; i++) {
				final int t = i;
				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, 1.0f);
				TextView textView = new TextView(mContext);
				textView.setTextAppearance(mContext, R.style.MainHostText);
				textView.setText(mHostList.get(i).getName());
				setGeneralPadding(textView);
				textView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						setSelectedIndex(t);
					}
				});
				addView(textView, params);
			}
		}
	}

	public void setSelectedIndex(int position) {
		if (mHostList != null && mHostList.size() > 0 && position < mHostList.size()) {
			if (currentSelectedView != null) {
				currentSelectedView.setTextAppearance(mContext, R.style.MainHostText);
				currentSelectedView.setBackgroundResource(R.color.transparent);
				setGeneralPadding(currentSelectedView);
			}
			TextView textView = (TextView) getChildAt(position);
			textView.setTextAppearance(mContext, R.style.MainHostTextSelected);
			textView.setBackgroundResource(R.drawable.home_title_1);
			setSelectedPadding(textView);
			currentSelectedView = textView;
			
			if (mChooseListener != null) {
				mChooseListener.onChoose(mHostList.get(position));
			}
		}
	}

	public void setmHostList(List<HostItem> mHostList) {
		this.mHostList = mHostList;
		refreshViews();
	}

	public List<HostItem> getmHostList() {
		return mHostList;
	}

	public void setmChooseListener(onChooseListener mChooseListener) {
		this.mChooseListener = mChooseListener;
	}

	public interface onChooseListener {
		public void onChoose(HostItem item);
	}
	
	private void setGeneralPadding(TextView text){
		text.setPadding(20, 20, 20, 0);
		LinearLayout.LayoutParams pamras = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		text.setLayoutParams(pamras);
	}
	
	private void setSelectedPadding(TextView text){
		text.setPadding(120, 20, 0, 0);
		LinearLayout.LayoutParams pamras = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		text.setLayoutParams(pamras);
	}
}
