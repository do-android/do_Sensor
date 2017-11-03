package dotest.module.frame.debug;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

public class DoUtils {
	
	public static String getInitDataRootPath(Context _context, String _appID) {
		return DoUtils.getFilesDir(_context) + File.separator + "doTest/initdata" + File.separator + _appID;
	}

	public static String getSourceRootPath(Context _context, String _appID) {
		return DoUtils.getFilesDir(_context) + File.separator + "doTest/apps" + File.separator + _appID;
	}

	private static String getFilesDir(Context _ctx) {
		return _ctx.getFilesDir().getAbsolutePath();
	}

	public static String getDataRootPath(Context _context, String _appID) {
		return DoUtils.getSDCard(_context) + File.separator + "doTest/data" + File.separator + _appID;
	}

	private static String getSDCard(Context _ctx) {
		if (existSDCard() && getSDFreeSize() > 5) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return _ctx.getFilesDir().getAbsolutePath();
	}

	private static boolean existSDCard() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		} else
			return false;
	}

	private static long getSDFreeSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
	}

}
