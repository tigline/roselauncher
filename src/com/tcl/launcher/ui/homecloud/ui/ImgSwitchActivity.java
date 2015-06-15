package com.tcl.launcher.ui.homecloud.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tcl.launcher.R;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.core.VoiceCommandCallback;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.util.TLog;


public class ImgSwitchActivity extends VoiceBarActivity {
	private Gallery myGallery;
	private RadioGroup gallery_points;
	private RadioButton[] gallery_point;
	private LinearLayout layout;
	private LayoutInflater inflater;
	private Context context;
	public static ImgSwitchActivity m_self = null;
	private ArrayList<String> filePathList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homecloud_imgswitch);
		context = getApplicationContext();
		inflater = LayoutInflater.from(context);
		Bundle b = this.getIntent().getExtras();
		filePathList = b.getStringArrayList("filePathList");
//		filePathList = this.getIntent().getStringArrayListExtra("filePathList");
		init();
		addEvn();
		m_self = this;
	}
	//��ʼ��
	void init(){

		myGallery = (DetailGallery)findViewById(R.id.myGallery);
		gallery_points = (RadioGroup) this.findViewById(R.id.galleryRaidoGroup);
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		list.add(R.drawable.banner1);
//		list.add(R.drawable.banner2);
//		list.add(R.drawable.banner3);
		GalleryIndexAdapter adapter = new GalleryIndexAdapter(filePathList, context);
		myGallery.setAdapter(adapter);
		//����С��ť
		gallery_point = new RadioButton[filePathList.size()];
		for (int i = 0; i < gallery_point.length; i++) {
			layout = (LinearLayout) inflater.inflate(R.layout.homecloud_gallery_icon, null);
			gallery_point[i] = (RadioButton) layout.findViewById(R.id.gallery_radiobutton);
			gallery_point[i].setId(i);/* ����ָʾͼ��ťID */
			int wh = Tool.dp2px(context, 10);
			RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(wh, wh); // ����ָʾͼ��С
			gallery_point[i].setLayoutParams(layoutParams);
			layoutParams.setMargins(4, 0, 4, 0);// ����ָʾͼmarginֵ
			gallery_point[i].setClickable(false);/* ����ָʾͼ��ť���ܵ�� */
			layout.removeView(gallery_point[i]);//һ������ͼ����ָ���˶������ͼ
			gallery_points.addView(gallery_point[i]);/* ���Ѿ���ʼ����ָʾͼ��̬��ӵ�ָʾͼ��RadioGroup�� */
		}
	}

	//����¼�
	void addEvn(){
		myGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				gallery_points.check(gallery_point[arg2%gallery_point.length].getId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	/** չʾͼ��������ʵ��չʾͼ�л� */
	final Handler handler_gallery = new Handler() {
		public void handleMessage(Message msg) {
			/* �Զ�����Ļ���µĶ��� */
			MotionEvent e1 = MotionEvent.obtain(SystemClock.uptimeMillis(),
					SystemClock.uptimeMillis(), MotionEvent.ACTION_UP,
					89.333336f, 265.33334f, 0);
			/* �Զ�����Ļ�ſ��Ķ��� */
			MotionEvent e2 = MotionEvent.obtain(SystemClock.uptimeMillis(),
					SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN,
					300.0f, 238.00003f, 0);
			
			myGallery.onFling(e2, e1, -800, 0);
			/* ��gallery��Ӱ��ºͷſ��Ķ�����ʵ���Զ����� */
			super.handleMessage(msg);
		}
	};

	protected void onResume() {
		autogallery();
		super.onResume();
	};
	private void autogallery() {
		/* ���ö�ʱ����ÿ5���Զ��л�չʾͼ */
		Timer time = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Message m = new Message();
				handler_gallery.sendMessage(m);
			}
		};
		time.schedule(task, 3000, 3000);
	}
	public class GalleryIndexAdapter extends BaseAdapter {
//		List<Integer> imagList;
		List<String> imagList;
		Context context;
//		public GalleryIndexAdapter(List<Integer> list,Context cx){
		public GalleryIndexAdapter(List<String> list,Context cx){
			imagList = list;
			context = cx;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView imageView = new ImageView(context);
//			imageView.setBackgroundResource(imagList.get(position%imagList.size()));
//			File file = new File(Environment.getExternalStorageDirectory()  
//	                + "/DCIM/"+ "/Camera/" + imagList.get((imagList.size() -1)- position %imagList.size()));
			
			File file = new File(imagList.get((imagList.size() -1)- position %imagList.size())); 
			if (file.exists()) {
				TLog.i("file.getPath()", file.getPath());
//				BitmapFactory.Options option = new BitmapFactory.Options();
//				option.inSampleSize = getImageScale(file.getPath());
				Bitmap bm = BitmapFactory.decodeFile(file.getPath());
				imageView.setImageBitmap(bm);
          }
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT
					, Gallery.LayoutParams.WRAP_CONTENT));
			return imageView;
		}
		private int getImageScale(String imagePath) {
			BitmapFactory.Options option = new BitmapFactory.Options();
			// set inJustDecodeBounds to true, allowing the caller to query the bitmap info without having to allocate the
			// memory for its pixels.
			option.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imagePath, option);

			int scale = 1;
			while (option.outWidth / scale >= IMAGE_MAX_WIDTH || option.outHeight / scale >= IMAGE_MAX_HEIGHT) {
				scale *= 2;
			}
			return scale;
		}
	}
	private static int   IMAGE_MAX_WIDTH  = 1024;
	private static int   IMAGE_MAX_HEIGHT = 960;
}