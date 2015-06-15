package com.tcl.launcher.ui.homecloud.FileManager.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;

import com.tcl.launcher.R;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileItem;
import com.tcl.launcher.ui.homecloud.FileManager.Model.FileModels.FileType;
import com.tcl.launcher.ui.homecloud.FileManager.Utils.FileSortHelper.SortMethod;

public class DateUtils {
	public static final String DATE_FORMAT_PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_PATTERN_YMD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_PATTERN_YM = "yyyy-MM";
	public static final String DATE_FORMAT_PATTERN_MD = "MM-dd";
	public static final String DATE_FORMAT_PATTERN_M = "MMMM";
	public static final String DATE_FORMAT_PATTERN_D = "dd";
	public static final String DATE_FORMAT_PATTERN_E = "EEEE";

	public static final String DIVIDE_VISIBLE = "divide_visible";
	public static final String DIVIDE_INVISIBLE  = "divide_invisible";

	public static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;
	
	public static long convertDateFromStingToLong(String time){
		long ret = 0l;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN_YMDHMS);
			ret = sdf.parse(time).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static String forString(String time, String pattern){
		return formatDate(convertDateFromStingToLong(time), pattern);
	}

	public static String formatDate(long time, String pattern){
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String des = dateFormat.format(new Date(time));

		return des;
	}
	
	public static boolean isSameDay(String day1, String day2){
		return isSameDay(convertDateFromStingToLong(day1),
				convertDateFromStingToLong(day2));
	}

	public static boolean isSameDay(long day1, long day2){
		if((day1 / ONE_DAY_MS) == (day2 / ONE_DAY_MS)){
			return true;
		}

		return false;
	}
	
	public static boolean isToday(String time){
		return isToday(convertDateFromStingToLong(time));
	}

	public static boolean isToday(long time){
		long now = new Date().getTime();
		long offset = now % ONE_DAY_MS;
		long startTimeOfToday = now - offset;
		long endTimeOfToday = startTimeOfToday + ONE_DAY_MS;

		if(time > startTimeOfToday && time <= endTimeOfToday){
			return true;
		}

		return false;
	}
	
	public static boolean isYesterday(String time){
		return isYesterday(convertDateFromStingToLong(time));
	}

	public static boolean isYesterday(long time){
		long now = new Date().getTime();
		long offset = now % ONE_DAY_MS;
		long startTimeOfYesterday = now - offset - ONE_DAY_MS;
		long endTimeOfYesterday = startTimeOfYesterday + ONE_DAY_MS;

		if(time > startTimeOfYesterday && time <= endTimeOfYesterday){
			return true;
		}

		return false; 
	}
	
	public static boolean isThisWeek(String time){
		return isThisWeek(convertDateFromStingToLong(time));
	}
	
	public static boolean isThisWeek(long time){
		long now = new Date().getTime();
		long offset = now % ONE_DAY_MS;
		long startTimeOfYesterday = now - offset - 6 * ONE_DAY_MS;
		long endTimeOfYesterday = startTimeOfYesterday + 6 * ONE_DAY_MS;

		if(time > startTimeOfYesterday && time <= endTimeOfYesterday){
			return true;
		}
		
		return false;
	}
	
	public static boolean isThisMonth(String time){
		return isThisMonth(convertDateFromStingToLong(time));
	}
	
	public static boolean isThisMonth(long time){
		long now = new Date().getTime();
		long offset = now % ONE_DAY_MS;
		long startTimeOfYesterday = now - offset - 29 * ONE_DAY_MS;
		long endTimeOfYesterday = startTimeOfYesterday + 29 * ONE_DAY_MS;

		if(time > startTimeOfYesterday && time <= endTimeOfYesterday){
			return true;
		}
		
		return false;
	}

	public static boolean isSameYearWithToday(long time){
		Calendar calendar = Calendar.getInstance();
		Calendar rightNow = Calendar.getInstance();
		calendar.setTime(new Date(time));
		if(calendar.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR)){
			return true;
		}

		return false;
	}
	
	public static String generateDate(String time){
		return generateDate(convertDateFromStingToLong(time));
	}

	/*
	 * When the upload time of files is today and yesterday,
	 * it shall be showed in DATE_FORMAT_PATTERN_MD format,
	 * 
	 * When upload time is same year with today, it shall be 
	 * showed in DATE_FORMAT_PATTERN_M format, do not show the 
	 * year field.
	 * 
	 * When upload time is not same year with today, it shall be
	 * showed in DATE_FORMAT_PATTERN_YM, do not show the day field.
	 * 
	 * This style is copied from lewa gallery.
	 */
	public static String generateDate(long time){
		String ret = "";

		if(isToday(time) || isYesterday(time)){
			ret = formatDate(time, DATE_FORMAT_PATTERN_MD);
		}else if(isSameYearWithToday(time)){
			ret = formatDate(time, DATE_FORMAT_PATTERN_M);
		}else{
			ret = formatDate(time, DATE_FORMAT_PATTERN_YM);
		}

		return ret;
	}
	
	public static String generateDetailHead(String time, 
			Context context){
		return generateDetailHead(convertDateFromStingToLong(time), 
				context);
	}

	public static String generateDetailHead(long time, Context context){
		String ret = "";

		if(isToday(time)){
			ret = context.getResources().getString(R.string.today);
		}else if(isYesterday(time)){
			ret = context.getResources().getString(R.string.yesterday);
		}else{
			ret = formatDate(time, DATE_FORMAT_PATTERN_D);
		}

		return ret;
	}
	

	public static String generateDetailTail(String time, String count, 
			Context context, FileType type){
		return generateDetailTail(convertDateFromStingToLong(time), 
				count, context, type);
	}

	public static String generateDetailTail(long time, String count, 
			Context context, FileType type){
		String unit = context.getResources().getString(R.string.photo);
		String tail = "";

		if(type == FileType.Video){
			unit = context.getResources().getString(R.string.video);
		}

		tail = formatDate(time, DATE_FORMAT_PATTERN_E) + "  |  " + count + " " + unit;

		return tail;
	}

	/*
	 * Classify files into different list, files belong to same day will 
	 * be grouped into the same list. 
	 */
	public static LinkedList<List<FileItem>> classifyByDate(List<FileItem> list){
		LinkedList<List<FileItem>> listCollection = new LinkedList<List<FileItem>>();

		if(list != null && !list.isEmpty()){
			//Resort the list by date before using.
			FileSortHelper sort_helper = new FileSortHelper();
			sort_helper.setSortMethod(SortMethod.date);
			sort_helper.sort(list);

			//Classify files into different group by date.
			String key = list.get(0).uploadtime;
			List<FileItem> tempList = new ArrayList<FileItem>();
			for(FileItem item : list){
				if(!isSameDay(key, item.uploadtime)){
					key = item.uploadtime;
					listCollection.add(tempList);
					tempList = new ArrayList<FileItem>();
				}
				tempList.add(item);
			}
			listCollection.add(tempList);
		}
		
		return listCollection;

	}

	/*
	 *  classifyByDate(LinkedList<List<FileItem>> linkedList) will classify files into different list,
	 *  files belong to same day will be grouped into the same list.
	 * 
	 *  classifyByDate2 will do re-classification on the list returned by classifyByDate.
	 *  Cut the a list to several small lists, the small list consist of one divide and 
	 *  less than 5 file items, the first item of the small list must be a divide.
	 * 
	 * 	Below words describe why I do this operation.
	 * 
	 * 	There are two ways to implement the UI, (1)ListView + GridView, (2)ListView + GridLayout.
	 * 	But both of them will meet problem, when one ListView item contains lots of file items, it will
	 * 	cost too much time to generate the View, it will cause nonfluency when sliding. So cut the list
	 *  to several small list to avoid this issue
	 * 
	 */
	public static LinkedList<List<FileItem>> classifyByDate2(LinkedList<List<FileItem>> linkedList){
		LinkedList<List<FileItem>> listCollection = new LinkedList<List<FileItem>>();

		for(List<FileItem> list : linkedList){
			//Classify files into different group by date.
			List<FileItem> tempList = new ArrayList<FileItem>();
			FileItem divideVisible = generateDivide(DIVIDE_VISIBLE);
			divideVisible.path = Integer.toString(list.size());
			tempList.add(divideVisible);
			for(FileItem item : list){
				if(tempList.size() >= 5){
					listCollection.add(tempList);
					tempList = new ArrayList<FileItem>();
					FileItem divideInvisible = generateDivide(DIVIDE_INVISIBLE);
					tempList.add(divideInvisible);
				}
				tempList.add(item);
			}
			listCollection.add(tempList);
		}

		return listCollection;

	}

	/*
	 * Divide is a special FileItem, It does not represent a file.
	 * 
	 * The name and author of a divide shall be DIVIDE_VISIBLE or 
	 * DIVIDE_INVISIBLE, shall not be other value.
	 * 
	 * The path will store the count of files belong to same day. 
	 */
	private static FileItem generateDivide(String s){
		FileItem divide = new FileItem();
		divide.name = s;
		divide.author = s;
		
		return divide;
	}
	
}
