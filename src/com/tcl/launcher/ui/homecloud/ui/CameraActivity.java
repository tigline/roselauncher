package com.tcl.launcher.ui.homecloud.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.tcl.launcher.LauncherApplication;
import com.tcl.launcher.R;
import com.tcl.launcher.base.VoiceBarActivity;
import com.tcl.launcher.core.VoiceCommandCallback;
import com.tcl.launcher.json.entry.CmdCtrl;
import com.tcl.launcher.util.TLog;
public class CameraActivity extends VoiceBarActivity implements      
Callback, OnClickListener, AutoFocusCallback{      
	private static final String TAG = "CameraActivity";
	public static final String BROADCAST_ACTION = "com.example.corn";
	private MediaPlayer mediaPlayer = null;
	private SharedPreferences lastPhoto = null;
	private ArrayList<String> filePathList = null;
	//private RoseCommandEventListener roseCommandListener = null;
	SurfaceView mySurfaceView;//surfaceView声明      
    SurfaceHolder holder;//surfaceHolder声明      
    Camera myCamera;//相机声明      
    String filePath="/sdcard/wjh.jpg";//照片保存路径      
    boolean isClicked = false;//是否点击标识      
    //创建jpeg图片回调数据对象      
    PictureCallback jpeg = null;
    /** Called when the activity is first created. */      
    @Override      
    public void onCreate(Bundle savedInstanceState) {      
        super.onCreate(savedInstanceState);      
        requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题                 
        //设置拍摄方向      
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);      
        setContentView(R.layout.homecloud_activity_camera);      
        //获得控件      
        mySurfaceView = (SurfaceView)findViewById(R.id.surfaceView1);      
        //获得句柄      
        holder = mySurfaceView.getHolder();      
        //添加回调      
        holder.addCallback(this);      
        //设置类型      
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);      
        //设置监听      
        mySurfaceView.setOnClickListener(this);      
        mediaPlayer = new MediaPlayer();
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
				if(localCmd.equals("HOME_CLOUD_TAKE_PHOTO"))
				{
					TLog.i(TAG, "accept HOME_CLOUD_TAKE_PHOTO!!!");
					myCamera.autoFocus(CameraActivity.this);//自动对焦     
					return false;
				}
				if(localCmd.equals("HOME_CLOUD_PLAY_LAST_PHOTO")||(localCmd.equals("HOME_CLOUD_PLAY_SELECT_PHOTO")))
				{
					TLog.i(TAG, "accept HOME_CLOUD_PLAY_LAST_PHOTO!!!");
					filePathList = new ArrayList<String>();
					String path = Environment.getExternalStorageDirectory()  
			                + "/DCIM/"+ "/Camera/";		
					getFiles(path);
					
					
//					Bundle b = new Bundle();
//					b.putSerializable("CmdPage", cmdCtrl);
//					b.putStringArrayList("filePathList", filePathList);
//					CameraActivity.this.mApplication.mVoiceControl.openActivity(CameraActivity.this.mApplication.getLastActivity(),
//							ImgSwitchActivity.class, b);
					
					
					Intent intent = new Intent(CameraActivity.this, ImgSwitchActivity.class);
					intent.putStringArrayListExtra("filePathList", filePathList);
					startActivity(intent);
					return false;
				}
				if(localCmd.equals("HOME_CLOUD_PHOTO_QUIT"))
				{
					TLog.i(TAG, "accept HOME_CLOUD_PHOTO_QUIT");
					if(ImgSwitchActivity.m_self != null)
					{
						ImgSwitchActivity.m_self.finish();
					}
					CameraActivity.this.finish();
					return false;
				}
				return true;
			}
		});
    	jpeg = new PictureCallback() {      
            
            @Override      
            public void onPictureTaken(byte[] data, Camera camera) {      
                // TODO Auto-generated method stub      
                try      
                {// 获得图片      
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);      
                
                Date date = new Date();  
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间  
                String filename = format.format(date) + ".jpg";  
                File fileFolder = new File(Environment.getExternalStorageDirectory()  
                        + "/DCIM/"+ "/Camera/");  
                lastPhoto = getSharedPreferences("userPhoto", MODE_PRIVATE);   
                SharedPreferences.Editor edit = lastPhoto.edit(); //编辑文件                    
                edit.putString("lastPhoto", filename);  
                edit.commit();  //保存数据信息   

                if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"Camera"的目录  
                    fileFolder.mkdir();  
                }  
                File jpgFile = new File(fileFolder, filename);  
                FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流  
                outputStream.write(data); // 写入sd卡中  
                outputStream.close(); // 关闭输出流 
                Uri contentUri = Uri.fromFile(jpgFile);
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(contentUri);
                CameraActivity.this.sendBroadcast(mediaScanIntent);//拍完照片发送扫描图库的广播

                }catch(Exception e)      
                {      
                    e.printStackTrace();      
                }      
                myCamera.startPreview();      
            }
            
    	};
//    	roseCommandListener = new RoseCommandEventListener() {
//			
//			@Override
//			public void EventActivated(RoseCommandEvent me) {
//				// TODO Auto-generated method stub
//				if(me.getCommand().equals("HOME_CLOUD_TAKE_PHOTO"))
//				{
//					myCamera.autoFocus(CameraActivity.this);//自动对焦     
//				}
//				if(me.getCommand().equals("HOME_CLOUD_PLAY_LAST_PHOTO"))
//				{
//					filePathList = new ArrayList<String>();
//					String path = Environment.getExternalStorageDirectory()  
//			                + "/DCIM/"+ "/Camera/";		
//					getFiles(path);
//					Intent intent = new Intent(CameraActivity.this, ImgSwitchActivity.class);
//					intent.putStringArrayListExtra("filePathList", filePathList);
//					startActivity(intent);
//				}
//				if(me.getCommand().equals("HOME_CLOUD_PHOTO_QUIT"))
//				{
//					CameraActivity.this.finish();
//				}
//			}
//		};
//		
//        RoseCommandListenerUtils.Instance().AddEventListener(roseCommandListener);
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
    public void surfaceChanged(SurfaceHolder holder, int format, int width,      
            int height) {      
        // TODO Auto-generated method stub      
        //设置参数并开始预览      
        Camera.Parameters params = myCamera.getParameters();      
        params.setPictureFormat(PixelFormat.JPEG);      
        params.setPreviewSize(640,480);      
        myCamera.setParameters(params);      
        myCamera.startPreview();      
              
    }      
    @Override      
    public void surfaceCreated(SurfaceHolder holder) {      
        // TODO Auto-generated method stub      
        //开启相机      
        if(myCamera == null)      
        {      
                 
            try {
            	myCamera = Camera.open(); 
                myCamera.setPreviewDisplay(holder);      
            } catch (IOException e) {      
                // TODO Auto-generated catch block      
                e.printStackTrace();      
            }      
        }      
              
    }      
    @Override      
    public void surfaceDestroyed(SurfaceHolder holder) {      
        // TODO Auto-generated method stub      
        //关闭预览并释放资源      
        myCamera.stopPreview();      
        myCamera.release();      
        myCamera = null;      
              
    }      
    @Override      
    public void onClick(View v) {      
        // TODO Auto-generated method stub      
        if(!isClicked)      
        {      
            myCamera.autoFocus(this);//自动对焦      
            isClicked = true;      
        }else      
        {      
            myCamera.startPreview();//开启预览      
            isClicked = false;      
        }      
              
    }      
    @Override      
    public void onAutoFocus(boolean success, Camera camera) {      
        // TODO Auto-generated method stub      
        if(success)      
        {      
            //设置参数,并拍照      
            Camera.Parameters params = myCamera.getParameters();      
            params.setPictureFormat(PixelFormat.JPEG);      
            params.setPreviewSize(640,480);      
            myCamera.setParameters(params);      
            myCamera.takePicture(null, null, jpeg);   
//            mediaPlayer = MediaPlayer.create(this, Notification.PRIORITY_HIGH);
            shootSound();
            Toast.makeText(CameraActivity.this, "拍照成功", Toast.LENGTH_SHORT).show();
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }      
              
    }
    public void shootSound()  
   {  
       MediaPlayer shootMP = null;
       AudioManager meng = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);  
       int volume = meng.getStreamVolume( AudioManager.STREAM_NOTIFICATION);        
       if (volume != 0)  
       {  
    	   if (shootMP == null)  
               shootMP = MediaPlayer.create(this, Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));  
            if (shootMP != null)  
               shootMP.start();  
        }  
    }  


    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	//RoseCommandListenerUtils.Instance().RemoveEventListener(roseCommandListener);
    }
}     