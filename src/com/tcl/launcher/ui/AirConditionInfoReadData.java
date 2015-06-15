package com.tcl.launcher.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.os.Environment;
import android.util.Log;

import com.tcl.launcher.ui.AirConditionInfoActivity.DataType;


public class AirConditionInfoReadData
{	
	private static final AirConditionInfoReadData mAirInfoReadData = new AirConditionInfoReadData();   
	
	public static AirConditionInfoReadData getInstance()
	{
		return mAirInfoReadData;
	}
	
	public float [] readSdCardeData(float [] valueData,DataType tyep)
	{
		float []result_data = valueData;
		
		//====读取SD卡中air_info_data.txt文件的数据====		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)==true)//SD卡是否存在
		{
			try 
			{
				File file = new File(Environment.getExternalStorageDirectory(),"air_info_data.txt");
			    if (file.exists()==true)
			    {
			    	Log.i("====zgx====", "====文件存在====");
			    	int index=0;
			    	int start;
					int end;
					int length;
					float value;
					String tempstr;
					String typeString =	null;
					String typeDataString =	null;
			    			
			    	String line_data_str;
			    	FileReader reader = new FileReader(file);
			    	BufferedReader bufferedReader = new BufferedReader(reader);	    			
			    	
			    	switch (tyep)
					{
						case CONSUME:		typeString = "CONSUME_NO:"; 		typeDataString = "A:";		break;
						case TEMPERATURE:	typeString = "TEMPERATURE_NO:"; 	typeDataString = "B:";		break;
						case ON_TIME:		typeString = "ON_TIME_NO:"; 		typeDataString = "C:";		break;
						case EFFICIENCY:	typeString = "EFFICIENCY_NO:"; 		typeDataString = "D:";		break;
						default: break;
					}
			    		
			    	while((line_data_str = bufferedReader.readLine()) != null)
					{
				    	if(line_data_str.indexOf(typeString)>=0)
				    	{
				    		tempstr = typeString;
				    		start = line_data_str.indexOf(tempstr)+tempstr.length();
				    		end   = line_data_str.indexOf(";");
				    		length = Integer.valueOf(line_data_str.substring(start, end));
				    		result_data = new float[length];
				    		Log.i("====zgx====", "========result_data="+length);
				    	 }
				    				
				    	if(line_data_str.indexOf(typeDataString)>=0)
				    	{
				    		tempstr = typeDataString;
				    		start = line_data_str.indexOf(tempstr)+tempstr.length();
				    		end   = line_data_str.indexOf("=");
				    		index = Integer.valueOf(line_data_str.substring(start, end))-1;
				    					
				    		tempstr = "=";
				    		start = line_data_str.indexOf(tempstr)+tempstr.length();
				    		end   = line_data_str.indexOf(";");
				    		value = Float.valueOf(line_data_str.substring(start, end));
				    					
				    		result_data[index] = value;
				    					
				    		Log.i("====zgx====", "====result_data["+index+"]="+result_data[index]);
				    	 }		    				
					}						
			    	
			    	bufferedReader.close();
			    }
			    else
			    {
			    	Log.i("====zgx====", "====没有文件====");
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			Log.i("====zgx====", "====没有SD卡====");
		}
		
		return result_data;
	}
}
