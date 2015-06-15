package com.tcl.launcher.ui.homecloud.Classlib.Utils;

import android.os.Environment;

import com.tcl.launcher.ui.homecloud.Classlib.Model.DeviceModel;

public class SysUtils {

	private static SysUtils _instance;
	private int port = 9421;

	// 系统包名
	private String packageName = "com.tcl.tcloudboa";

	private String serverUrl = "http://www.tcljty.com/tcloud/";
	public String sdcardPath = Environment.getExternalStorageDirectory().getPath();
	private String boaRootPath = sdcardPath + "/boa";

	public String LocalLogFilePath = sdcardPath + "/tcloudboa/System/Log";

	private String boaVirtualRootDir = sdcardPath+ "/tcloudboa";

//	private boolean isRegistor = false;
//
//	private boolean isStartedP2PServer = false;
//
//	private ServerStatus serverStatus = ServerStatus.Stoped;
//
	private DeviceModel deviceModel = null;
//
//	private String systemLanguage = Locale.getDefault().getLanguage();
//
//	private static HashMap<ModuleType, Boolean> logContorl = null;
//
	public SysUtils() {
	}

	public static SysUtils Instance() {
		if (_instance == null) {
			_instance = new SysUtils();
//			logContorl = new HashMap<ModuleType, Boolean>();
//			logContorl.put(ModuleType.sys, true);
//			logContorl.put(ModuleType.photo, true);
//			logContorl.put(ModuleType.music, true);
//			logContorl.put(ModuleType.video, true);
//			logContorl.put(ModuleType.document, true);
//			logContorl.put(ModuleType.frends, true);
//			logContorl.put(ModuleType.management, true);
//			logContorl.put(ModuleType.share, true);
//			logContorl.put(ModuleType.other, true);
		}
		return _instance;
	}
//
//	/**
//	 * 得到日志控制队列
//	 * 
//	 * @return
//	 */
//	public HashMap<ModuleType, Boolean> getLogContorl() {
//		return logContorl;
//	}

	public DeviceModel getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(DeviceModel deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getBoaVirtualRootDir() {
		return boaVirtualRootDir;
	}

	public void setBoaVirtualRootDir(String boaVirtualRootDir) {
		this.boaVirtualRootDir = boaVirtualRootDir;
	}
//
//	public boolean isStartedP2PServer() {
//		return isStartedP2PServer;
//	}
//
//	public void setStartedP2PServer(boolean isStartP2PServer) {
//		this.isStartedP2PServer = isStartP2PServer;
//	}
//
//	public ServerStatus getServerStatus() {
//		return serverStatus;
//	}
//
//	public void setServerStatus(ServerStatus serverStatus) {
//		this.serverStatus = serverStatus;
//	}

	public String getBoaRootPath() {
		return boaRootPath;
	}

	public void setBoaRootPath(String boaRootPath) {
		this.boaRootPath = boaRootPath;
	}

//	public int getPort() {
//		return port;
//	}
//
//	public void setPort(int port) {
//		this.port = port;
//	}
//
//	public boolean isRegistor() {
//		return isRegistor;
//	}
//
//	public void setRegistor(boolean isRegistor) {
//		this.isRegistor = isRegistor;
//	}
//
//	public String getPackageName() {
//		return packageName;
//	}
//
//	public void setPackageName(String packageName) {
//		this.packageName = packageName;
//	}
//
//	/**
//	 * @return 私云web服务器URL
//	 */
//	public String getServerUrl() {
//		return serverUrl;
//	}
//
//	public String getMacAddress(Context context) {
//		File file = new File(Environment.getExternalStorageDirectory()
//				+ "/boa/", "MACID");
//		String macAddress = null;
//		if (!FileUtils.Instance().IsFileExist(Environment.getExternalStorageDirectory() + "/boa/MACID")) {
//			int wiftChangeflag = 0;
//
//			ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//			NetworkInfo[] networkInfo = conn.getAllNetworkInfo();
//			if (networkInfo != null) {
//				for (int i = 0; i < networkInfo.length; i++) {
//					if (networkInfo[i].getType() == ConnectivityManager.TYPE_WIFI) {
//						WifiManager wifiManager = (WifiManager) context
//								.getSystemService(Context.WIFI_SERVICE);
//
//						int wifiState = wifiManager.getWifiState();
//						if (wifiState != 0x00000003) {
//							wifiManager.setWifiEnabled(true);
//							wiftChangeflag = 1;
//						}
//						WifiInfo info = wifiManager.getConnectionInfo();
//						macAddress = info.getMacAddress();
//						System.out.println("This is TYPE_WIFI");
//						if (wiftChangeflag == 1) {
//							wifiManager.setWifiEnabled(false);
//							wiftChangeflag = 0;
//						}
//					}else if(networkInfo[i].getType() == ConnectivityManager.TYPE_ETHERNET)
//					{
//						macAddress = networkInfo[i].getExtraInfo();
//					}
//				}
//
//				if (macAddress!=null && macAddress.length() > 1) {
//					macAddress = macAddress.replace(" ", "").replace(":", "");
//				}else
//				{
//					macAddress = CommandUtils.Instance().RootCommand("cat /sys/class/net/eth0/address");
//					macAddress = macAddress.replace(" ", "").replace(":", "");
//					LogUtils.Instance().Log("/sys/class/net/eth0/address macAddress is:"+macAddress, ModuleType.sys);
//				}
//			}
//			FileUtil.WriteConfInfo(SysUtils.Instance().getBoaRootPath()+ "/MACID", macAddress);
//		} else {
//			macAddress = FileUtil.readLog(SysUtils.Instance().getBoaRootPath()+ "/MACID");
//		}
//		
//		return MD5.getMD5(macAddress);
//	}
//
//	/** 取SD卡路径 **/
//	private String getSDPath() {
//		File sdDir = null;
//		boolean sdCardExist = Environment.getExternalStorageState().equals(
//				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
//		if (sdCardExist) {
//			sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
//		}
//		if (sdDir != null) {
//			return sdDir.toString();
//		} else {
//			return "";
//		}
//	}
//
//	/**
//	 * 自动判断服务器端的响应消息格式是否存在异常
//	 * 
//	 * @param result
//	 * @return
//	 */
//	public StatusCode AutoJudgeResult(String result) {
//		StatusCode statusCode;
//		try {
//			statusCode = Enum.valueOf(StatusCode.class, result);
//		} catch (Exception e) {
//			return StatusCode.Success;
//		}
//		return statusCode;
//	}
//
//	/**
//	 * 根据状态码得到错误原因
//	 * 
//	 * @param statusCode
//	 * @return
//	 */
//	public String GetStatusCodeInfo(StatusCode statusCode) {
//		String statusCodeInfo = "";
//		switch (statusCode) {
//		case NetException:
//			if (systemLanguage.equals("en")) {
//				statusCodeInfo = "Abnormal network, check the network connection is correct";
//			} else {
//				statusCodeInfo = "网络异常,请检查网络连接是否正确";
//			}
//
//			break;
//		default:
//			break;
//		}
//		return statusCodeInfo;
//	}
//
//	public static String getSDTotalSize(String str) {
//		StatFs stat = new StatFs(str);
//		long blockSize = stat.getBlockSize();
//		long totalBlocks = stat.getBlockCount();
//
//		long Total_temp1 = blockSize * totalBlocks;
//		String total = FileUtil.formetFileSize(Total_temp1);
//
//		System.out.println("---total---" + total);
//		return total;
//
//	}
//
//	public long ComputerSDTotalSize(String str) {
//		long result = 0;
//		try {
//			StatFs stat = new StatFs(str);
//			long blockSize = stat.getBlockSize();
//			long totalBlocks = stat.getBlockCount();
//
//			long Total_temp1 = blockSize * totalBlocks;
//			result = Total_temp1 / 1048576;
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return result;
//	}
//
//	public long ComputerSDAlreadySize(String str) {
//		StatFs stat = new StatFs(str);
//		long Already_use = 0;
//		long blockSize = stat.getBlockSize();
//		long totalBlocks = stat.getBlockCount();
//		long availCount = stat.getAvailableBlocks();
//
//		long Remain_temp2 = availCount * blockSize;
//		long Total_temp1 = blockSize * totalBlocks;
//
//		Already_use = (Total_temp1 - Remain_temp2);
//		long result = Already_use / 1048576;
//		return result;
//	}
//
//	public String getSDAlreadySize(String str) {
//		StatFs stat = new StatFs(str);
//		long Already_use = 0;
//		long blockSize = stat.getBlockSize();
//		long totalBlocks = stat.getBlockCount();
//		long availCount = stat.getAvailableBlocks();
//
//		long Remain_temp2 = availCount * blockSize;
//		long Total_temp1 = blockSize * totalBlocks;
//
//		Already_use = (Total_temp1 - Remain_temp2);
//
//		String already = FileUtil.formetFileSize(Already_use);
//
//		System.out.println("---already---" + already);
//		return already;
//	}
//
//	public String do_exec(String cmd, String filter) {
//		BufferedReader in = null;
//		String result = "";
//		String line = "";
//		try {
//			Process proc = Runtime.getRuntime().exec(cmd);
//			in = new BufferedReader(
//					new InputStreamReader(proc.getInputStream()));
//
//			while ((line = in.readLine()) != null
//					&& line.contains(filter) == false) {
//				// System.out.println(line);
//			}
//			in.close();
//			result = line;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (in != null)
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//		// System.out.println("---result---" + result);
//		if (result != null) {
//			Pattern pattern = Pattern.compile("[ ]+");
//			String[] strs = pattern.split(result);
//
//			System.out.println();
//			return strs[0];
//		}
//		return ApplicationExceptionCatch.getInstance().getMemoryDir();
//	}
//
//	public void AutoInstall(Context context, String filePath) {
//
//		// 核心是下面几句代码
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setDataAndType(Uri.fromFile(new File(filePath)),
//				"application/vnd.android.package-archive");
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(intent);
//	}
//
//    public boolean IsInstallOrUpdateApp(Context context, String packageName) {
//		boolean isInstallOrUpdateApp = true;
//		List<PackageInfo> list = context.getPackageManager()
//				.getInstalledPackages(PackageManager.GET_PERMISSIONS);
//		for (PackageInfo packageInfo : list) {
//			if (packageInfo.packageName.equals("com.tcl.nattraversalservice")) {
//				if (Const.NatTraversalServiceVerCode > packageInfo.versionCode) {
//					isInstallOrUpdateApp = true;
//					break;
//				} else {
//					isInstallOrUpdateApp = false;
//				}
//			}
//		}
//		return isInstallOrUpdateApp;
//	}
//    
//    private void getListMemory() {
//		StorageList mStorageList = new StorageList(this);
//		String[] paths = mStorageList.getVolumePaths();
//		for (int i = 0; i < paths.length; i++) {
//
//			String path = do_exec(paths[i]);
//			if (path != null) {
//				if( i == 0){
//					setMemoryDir(path);
//				}
//				else if(i == 1){
//					setSdcardDir(path);
//				}else if(i == 2){
//					setUsbDir(path);
//				}
//			}
//		}
//	}
//    private String do_exec(String filter) {
//		BufferedReader in = null;
//		String result = "";
//		String line = "";
//		try {
//			Process proc = Runtime.getRuntime().exec("df");
//			in = new BufferedReader(
//					new InputStreamReader(proc.getInputStream()));
//
//			while ((line = in.readLine()) != null) {
//				if (line.contains(filter)) {
//					result = line;
//					break;
//				}
//				// System.out.println(line);
//			}
//			in.close();
//			// Log.e(TAG, result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (in != null)
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//
//		// System.out.println("---result---" + result);
//		if (!result.equals("")) {
//			// Pattern pattern = Pattern.compile("[  ]+");
//			// String[] strs = pattern.split(result);
//			String columns[] = result.split("  ");
//			String strs[] = new String[5];
//			for (int i = 0, j = 0; i < columns.length; i++) {
//
//				if (columns != null && columns.length > 1
//						&& columns[i].length() > 0) {
//					strs[j++] = columns[i];
//				}
//			}
//
//			return strs[0];
//		}
//		return null;
//	}
	
}
