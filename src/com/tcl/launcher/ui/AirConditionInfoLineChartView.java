package com.tcl.launcher.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

import com.tcl.launcher.R;
import com.tcl.launcher.ui.AirConditionInfoActivity.DataType;


@SuppressLint("DrawAllocation")
public class AirConditionInfoLineChartView extends View
{
	//====显示数据====
	private float []dataFloat;
	
	//====数据最大值====
	private float yMaxValue;
	
	//====数据类型====
	private DataType dataType;
	
	//====图表显示宽度、高度====
	private int mDisplayWidth;
	private int mDisplayHeight;	
	
	private ImageView ivCharBg;
	
	public AirConditionInfoLineChartView(Context context)  
	{  
		super(context);
	}
	
	
	//====设置图表数据====
	public void setData(int width,int height,float []data,DataType datatype,ImageView charBg)
	{
		mDisplayWidth 	= width;
		mDisplayHeight 	= height;
		dataFloat 		= data;
		dataType  		= datatype;
		
		yMaxValue = dataMax(dataFloat);
		
		ivCharBg = charBg;
	}
	
	
	//====求数据最大值====
	private float dataMax(float []data)
	{
		float maxData = 0;
		
		for(int i=0;i<data.length;i++)
		{
			maxData = (maxData>data[i]?maxData:data[i]);
		}
		
		return maxData;
	}
	
	
	
	//====求数据和====
	private float dataSum(float []data)
	{
		float sumData = 0f;
		
		for(int i=0;i<data.length;i++)
		{
			sumData = sumData + data[i];
		}
		
		return (float)(Math.round(sumData *100))/100;
	}
	
	
	
	//====求数据平均值====
	private float dataAve(float []data)
	{
		float aveData = 0;
		
		for(int i=0;i<data.length;i++)
		{
			aveData = aveData + data[i];
		}
		
		aveData = aveData / data.length;
		
		aveData = (float)(((int)(aveData*10))/10.0);		
		
		return aveData;
	}
	
	
	
	//====画图标====
	public void onDraw (Canvas canvas)  
	{  
		super.onDraw(canvas);
		
		
		//====画笔-柱形图====
		Paint paintColumn = new Paint();		
		paintColumn.setStrokeWidth(4); 
		paintColumn.setColor(Color.WHITE);
		paintColumn.setAntiAlias(true); 
		
		//====画笔-折线====
		Paint paintLineChart = new Paint();		
		paintLineChart.setStrokeWidth(4);    		
		paintLineChart.setColor(Color.WHITE);
		paintLineChart.setAntiAlias(true);
		
		//====画笔-点====
		Paint paintPoint = new Paint();		
		paintPoint.setStrokeWidth(4); 
		paintPoint.setColor(Color.WHITE);
		paintPoint.setAntiAlias(true); 
		
		//====画笔-字====
		Paint paintText = new Paint();
		paintText.setTextSize(32);
		paintText.setColor(Color.WHITE);
		
		//====画笔-大字====
		Paint paintTextBig = new Paint();
		paintTextBig.setTextSize(40);
		paintTextBig.setColor(Color.WHITE);
		
		//====画笔-中字====
		Paint paintTextMiddle = new Paint();
		paintTextMiddle.setTextSize(20);
		paintTextMiddle.setColor(Color.BLACK);
		
		//====画笔-小字====
		Paint paintTextSmall = new Paint();
		paintTextSmall.setTextSize(16);
		paintTextSmall.setColor(Color.BLACK);
		
		

		//====坐标图====
		if(dataType != DataType.EFFICIENCY)
		{
			int MARGIN_LEFT 	= 70;	//坐标图左偏移量
			int MARGIN_TOP		= 120;	//坐标图上偏移量			
			
			int xGap;
			int yGap;
			int gapValue;
			int tempValue;
			int maxValue 	= 0;
			int maxDistance = 0;
			
			
			//====画Y标号====
			if(dataType==DataType.CONSUME)
			{		
				if(yMaxValue<50)
				{
					gapValue = ((int)yMaxValue)/7+1;
				}
				else
				{
					gapValue = (((int)yMaxValue)/7/10+1)*10;
				}
				
				tempValue = 0;
								
				for(int i=0;i<=7;i++)
				{							
					canvas.drawText(String.valueOf(tempValue),MARGIN_LEFT + 25,MARGIN_TOP+ivCharBg.getHeight()-37-74*i,paintText);
					
					tempValue = tempValue + gapValue;
				}
				//canvas.drawText("(度)",MARGIN_LEFT+15,MARGIN_TOP+35,paintText);
				
				String chartName = "功耗（kW·h）";
				canvas.drawText(chartName,mDisplayWidth/2 - chartName.length()/3*paintTextBig.getTextSize() , 100, paintTextBig);
				
				maxValue 	= tempValue - gapValue;
				maxDistance	= 518;
			}
			else if(dataType==DataType.ON_TIME)
			{						
				gapValue = 5;
				
				tempValue = 0;
								
				for(int i=0;i<=5;i++)
				{							
					canvas.drawText(String.valueOf(tempValue),MARGIN_LEFT + 25,MARGIN_TOP+ivCharBg.getHeight()-37-100*i,paintText);
					
					tempValue = tempValue + gapValue;
				}
				//canvas.drawText("(小时)",MARGIN_LEFT+15,MARGIN_TOP+50,paintText);
				
				String chartName = "运行时间（h）";
				canvas.drawText(chartName,mDisplayWidth/2 - chartName.length()/2*paintTextBig.getTextSize() , 100, paintTextBig);
				
				maxValue 	= tempValue - gapValue;
				maxDistance	= 500;
			}
			else if(dataType==DataType.TEMPERATURE)
			{						
				gapValue = 10;
				
				tempValue = 0;
								
				for(int i=0;i<=4;i++)
				{							
					canvas.drawText(String.valueOf(tempValue),MARGIN_LEFT + 25,MARGIN_TOP+ivCharBg.getHeight()-37-130*i,paintText);
					
					tempValue = tempValue + gapValue;
				}
				//canvas.drawText("(℃)",MARGIN_LEFT+15,MARGIN_TOP+40,paintText);
				String chartName = "温度（℃）";
				canvas.drawText(chartName,mDisplayWidth/2 - chartName.length()/2*paintTextBig.getTextSize() , 100, paintTextBig);
				
				maxValue 	= tempValue - gapValue;
				maxDistance	= 520;
			}
			
			
			//====画X标号====
			xGap = MARGIN_LEFT + 80;
			for(int i=1;i<=31;i++)
			{				
				canvas.drawText(String.valueOf(i),xGap,MARGIN_TOP+ivCharBg.getHeight()-20,paintTextMiddle);
				
				xGap = xGap + (ivCharBg.getWidth()-90)/31;
			}
			canvas.drawText("(天)",MARGIN_LEFT+ivCharBg.getWidth()-40,MARGIN_TOP+ivCharBg.getHeight()-20,paintTextMiddle);
			
			
			//====画柱形图====
			int pointX=0;   //当前坐标点X
			int pointY=0;   //当前坐标点Y
			int dePointX=0;	//前坐标点X
			int dePointY=0;	//前坐标点Y
			
			if(dataType==DataType.CONSUME||dataType==DataType.ON_TIME)
			{
				xGap = MARGIN_LEFT + 80;
				
				for(int i=0;i<31;i++)
				{
					pointX = xGap;
					pointY = (int)(maxDistance*1.0/maxValue*dataFloat[i]);
					canvas.drawRect(pointX, MARGIN_TOP+ivCharBg.getHeight()-50-pointY, pointX+20, MARGIN_TOP+ivCharBg.getHeight()-50, paintColumn);
					//canvas.drawText(String.valueOf(dataFloat[i]), pointX, MARGIN_TOP+tempBitmapBg.getHeight()-50-pointY-6, paintTextSmall);
					xGap = xGap + (ivCharBg.getWidth()-90)/31;
				}
			}
			else if(dataType==DataType.TEMPERATURE)
			{
				pointX = 0;
				pointY = 0;
				
				xGap = MARGIN_LEFT + 80;
				
				for(int i=0;i<dataFloat.length;i++)
				{
					dePointX = pointX;
					dePointY = pointY;
					pointX 	 = xGap;					
					pointY = (int)(MARGIN_TOP+ivCharBg.getHeight()-50 - maxDistance*1.0/maxValue*dataFloat[i]);
					
					if(i!=0)
					{
						canvas.drawLine(dePointX, dePointY, pointX, pointY, paintLineChart);
					}
					canvas.drawCircle(pointX , pointY , 8 , paintPoint);
					
					xGap = xGap + (ivCharBg.getWidth()-90)/31;
				}				
			}
			
			
			
		}
		else//====画饼图====
		{					
			//====比例对应的评价====
			String levelStr[] = new String[]{"优","良","中","差"};
			
			float size = 600;	//饼图直径
			float centerX = mDisplayWidth  / 2;	//饼图圆心X轴 
			float centerY = mDisplayHeight / 2;	//饼图圆心Y轴
						
			//====饼图颜色====
			Paint[] circlePaint = new Paint[4];
			for(int i=0;i<circlePaint.length;i++)
			{
				circlePaint[i]= new Paint();
				circlePaint[i].setAntiAlias(true); 
			}				   		
			circlePaint[0].setColor(getResources().getColor(R.color.Level1));
			circlePaint[1].setColor(getResources().getColor(R.color.Level2));
			circlePaint[2].setColor(getResources().getColor(R.color.Level3));
			circlePaint[3].setColor(getResources().getColor(R.color.Level4));
			
					
			//====饼图圆外接正方形====
			RectF rect = new RectF(centerX-size/2,centerY-size/2,centerX+size/2,centerY+size/2);
			
			
			//====图标矩形====
			RectF iconRectF[] = new RectF[4];
			
			int tempHeight = mDisplayHeight - 100 - 200 - 150;
			
			for(int i=0;i<iconRectF.length;i++)
			{				
				iconRectF[i] = new RectF(100,tempHeight,150,tempHeight+50);
				tempHeight = tempHeight + 100;
			}
			
			//====根据比例画扇形图====
			float CurrPer = 0f; 	//偏移角度  
			float Percentage =  0f; //当前所占比例
			for(int i=0; i<4; i++)
	        {  
	            //将百分比转换为饼图显示角度  
	            Percentage = 360 * (dataFloat[i]/ 100);  
	            Percentage = (float)(Math.round(Percentage *100))/100;  
	               
	            //在饼图中画所占比例  
	            canvas.drawArc(rect, CurrPer, Percentage, true, circlePaint[i]); 
	            
	            canvas.drawRect(iconRectF[i], circlePaint[i]);
	            
	            canvas.drawText(levelStr[i], iconRectF[i].left-paintText.getTextSize()-10,iconRectF[i].bottom - 12, paintText);
	            
	            canvas.drawText(String.valueOf(dataFloat[i]) +"%", iconRectF[i].right+10,iconRectF[i].bottom - 12, paintText);
	            
	            //下次的起始角度  
	            CurrPer += Percentage;  
	        } 
			
			//====写饼图标题====
			String chartName = "效率分析";
			canvas.drawText(chartName,mDisplayWidth/2 - chartName.length()/2*paintTextBig.getTextSize() , 100, paintTextBig);
		}		
   	}
}
