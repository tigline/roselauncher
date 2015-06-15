package com.tcl.launcher.ui.fragment;

import java.util.Timer;
import java.util.TimerTask;

import com.tcl.common.define.SmartHomeConst;
import com.tcl.launcher.LauncherApplication;
import com.tcl.launcher.R;
import com.tcl.launcher.constants.CommandConstants;
import com.tcl.power.control.SmartPowerControlInterface;
import com.tcl.smart_appliances.phone_airCondition.SmartAirConditioner;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SmartHomeFragment extends Fragment
{
	private ToggleButton tbtnLigh;
	private ToggleButton tbtnMusic;
	private TextView tvAirTemperature;
	
	private final int MESSAGE_WHAT_UPDATE_STATE = 1;
	
	private boolean isLightOn = false;
	private boolean isMusicOn = false;
	private String  airSetTemp;
	
	private Timer timerStateDetect = null;
	private boolean isStopStateDetect = false;
	
	private Message mMessageStateDetect;
	
	private static LauncherApplication mApplication;
	
	private Handler localHandler = new Handler()
	{
		@Override
		public void handleMessage(Message message)
		{
			switch (message.what)
			{
				case MESSAGE_WHAT_UPDATE_STATE:
					isLightOn = mApplication.mVoiceControl.mSmartHome.getSmartPowerStatus(0, SmartHomeConst.POWER_PORT_1);
					tbtnLigh.setChecked(isLightOn);
					
					isMusicOn = mApplication.mVoiceControl.mSmartHome.getSmartPowerStatus(0, SmartHomeConst.POWER_PORT_4);
					tbtnMusic.setChecked(isMusicOn);

					airSetTemp = mApplication.mVoiceControl.mSmartHome.getAirconditionerStatus(0,SmartHomeConst.AIR_REQ_ACTUAL_TEMP_SET);
					if(airSetTemp!=null&&airSetTemp.length()>0)
					{
						tvAirTemperature.setText(airSetTemp+"℃");
					}
				break;
			}
		}
	};
	
	public SmartHomeFragment() 
	{
		
	}

	public static SmartHomeFragment getInstance(LauncherApplication application) 
	{
		mApplication = application;
		SmartHomeFragment fragment = new SmartHomeFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		Log.i("====zgx====","====zgx====SmartHomeFragment onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.i("====zgx====","====zgx====SmartHomeFragment onCreateView()");
		
		View rootView = inflater.inflate(R.layout.frag_smart_home, container, false);
		
		tbtnLigh 			= (ToggleButton)rootView.findViewById(R.id.btn_light_set);
		tbtnMusic			= (ToggleButton)rootView.findViewById(R.id.btn_music_set);
		tvAirTemperature 	= (TextView)rootView.findViewById(R.id.tv_air_set_temperature);
		
		tvAirTemperature.setText("");
		
		tbtnLigh.setOnClickListener(listener);
		tbtnMusic.setOnClickListener(listener);
		
		return rootView;
	}
	
	private OnClickListener listener= new OnClickListener() 
	{   
		public void onClick(View v) 
		{
			switch (v.getId())
			{
				case R.id.btn_light_set:
				     if(tbtnLigh.isChecked()==true)
				     {
				    	 mApplication.mVoiceControl.mSmartHome.setSmartHomeCommand(CommandConstants.DEVICE_ONE_POWER_OPEN,0);
				     }
				     else
				     {
				    	 mApplication.mVoiceControl.mSmartHome.setSmartHomeCommand(CommandConstants.DEVICE_ONE_POWER_CLOSE,0);
					 }
				     break;
				case R.id.btn_music_set:
				     if(tbtnMusic.isChecked()==true)
				     {
				    	 mApplication.mVoiceControl.mSmartHome.setSmartHomeCommand(CommandConstants.DEVICE_FOUR_POWER_OPEN,0);
				     }
				     else
				     {
				    	 mApplication.mVoiceControl.mSmartHome.setSmartHomeCommand(CommandConstants.DEVICE_FOUR_POWER_CLOSE,0);
					 }
				     break;
			}
		}
	};

	@Override
	public void onDestroy()
	{
		isStopStateDetect = true;
		super.onDestroy();		
	}

	
	@Override
	public void onPause()
	{
		isStopStateDetect = true;
		super.onPause();
	}

	
	@Override
	public void onResume()
	{
		isStopStateDetect = false;
		auto_power_detect();
		super.onResume();
	}
	
	private void auto_power_detect()
	{		
		if(timerStateDetect!=null)
			return;

		isStopStateDetect = false;
		timerStateDetect = new Timer();
		timerStateDetect.schedule(new TimerTask() 
		{
			@Override
			public void run() 
			{				
				if(isStopStateDetect == true)
				{
					timerStateDetect.cancel();
					timerStateDetect.purge();
					timerStateDetect=null;
					Log.i("====zgx=====", "====auto_power_detect cancel====");
				}
				else
				{					
					mMessageStateDetect = new Message();
					mMessageStateDetect = localHandler.obtainMessage();
					mMessageStateDetect.what = MESSAGE_WHAT_UPDATE_STATE;
					localHandler.sendMessage(mMessageStateDetect);
				}			
			}
		},0,3000);
	}
	
}
