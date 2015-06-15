package com.tcl.launcher.ui.homecloud.FileManager.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;

public class FileThumbUtils {	
	
	private static final int  DEF_WIDTH = 200;
	private static final int  DEF_HEIGHT = 200; 

	public static Bitmap getPhotoThumb(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);		
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, DEF_WIDTH, DEF_HEIGHT);		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	
	public static Bitmap getVideoThumbnail(String filePath) {
		Bitmap res = null;
		Bitmap org = ThumbnailUtils.createVideoThumbnail(filePath, 0);
		if(org != null)
		{
			res = ThumbnailUtils.extractThumbnail(org, DEF_WIDTH, DEF_HEIGHT);
		}
		
		return res;     
    }
	
	public static Bitmap getMusicThumbnail(String filePath) {
        Bitmap res = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {        
            retriever.setDataSource(filePath);       
            res  = retriever.getFrameAtTime();         
            
        } catch(IllegalArgumentException ex) {
        } catch (RuntimeException ex) {
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                
            }
        }
        return res; 
    }	
}