package com.tcl.launcher.ui.homecloud.FileManager.Model;
import java.util.HashMap;

public class FileModels {

	public static enum FileType {
		Music, Photo, Video, Document, Folder,
	}
	
	public static final String MusicFileTypeName = "Music";
	public static final String PhotoFileTypeName = "Photo";
	public static final String VideoFileTypeName = "Video";
	public static final String DocumentFileTypeName = "Document";
	public static final String FloderFileTypeName = "Folder";
	public static HashMap<String, FileType> FileStringType = new HashMap<String, FileType>();
	
	
	static
	{
		FileStringType.put(MusicFileTypeName, FileType.Music);
		FileStringType.put(PhotoFileTypeName, FileType.Photo);
		FileStringType.put(VideoFileTypeName, FileType.Video);
		FileStringType.put(DocumentFileTypeName, FileType.Document);	
		FileStringType.put(FloderFileTypeName, FileType.Folder);	
	}
	
	public static FileType getFileType(String typeString) {
		return FileStringType.get(typeString);		
	}

	public static class MediaFile {
		private static HashMap<String, FileType> sFileTypeMap = new HashMap<String, FileType>();

		static {
			// music
			addFileType("mp3", FileType.Music);
			addFileType("aac", FileType.Music);
			addFileType("mid", FileType.Music);
			addFileType("wav", FileType.Music);
			addFileType("m4a", FileType.Music);
			addFileType("amr", FileType.Music);
			addFileType("awb", FileType.Music);
			addFileType("wma", FileType.Music);
			addFileType("ogg", FileType.Music);
			addFileType("oga", FileType.Music);
			addFileType("mka", FileType.Music);

			// photo
			addFileType("png", FileType.Photo);
			addFileType("gif", FileType.Photo);
			addFileType("jpg", FileType.Photo);
			addFileType("jpeg", FileType.Photo);
			addFileType("bmp", FileType.Photo);
			addFileType("wbmp", FileType.Photo);
			addFileType("webp", FileType.Photo);

			// video
			addFileType("mp4", FileType.Video);
			addFileType("rmvb", FileType.Video);
			addFileType("avi", FileType.Video);
			addFileType("rmv", FileType.Video);
			addFileType("mpeg", FileType.Video);
			addFileType("mpg", FileType.Video);
			addFileType("m4v", FileType.Video);
			addFileType("3gp", FileType.Video);
			addFileType("3gpp", FileType.Video);
			addFileType("3g2", FileType.Video);
			addFileType("3gpp2", FileType.Video);
			addFileType("mkv", FileType.Video);
			addFileType("webm", FileType.Video);
			addFileType("ts", FileType.Video);
			
			//

		}

		static void addFileType(String ext, FileType type) {
			sFileTypeMap.put(ext, type);
		}

		public static FileType getFileType(String path) {
			FileType type = FileType.Document;
			int nIndex = path.lastIndexOf(".");
			if (nIndex > 0) {
				type = sFileTypeMap.get(path.substring(nIndex + 1)
						.toLowerCase());
				if(type == null )
				{
					type = FileType.Document;	
				}
				
			}		

			return type;
		}
	}

}
