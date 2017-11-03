package dotest.module.frame.debug;

import java.io.File;

import android.content.Context;
import core.helper.DoIOHelper;
import core.interfaces.DoIApp;
import core.interfaces.DoIDataFS;
import core.interfaces.DoISourceFS;

public class DoDataFS implements DoIDataFS {

	public DoDataFS(Context _context, DoIApp _doApp) {
		this.currentApp = _doApp;
		this.onInit(_context);
	}

	private void onInit(Context _context) {
		this.rootPath = DoUtils.getDataRootPath(_context, this.currentApp.getAppID());
		if (!DoIOHelper.existDirectory(this.rootPath)) {
			DoIOHelper.createDirectory(this.rootPath);
		}
		this.pathSys = this.rootPath + File.separator + "sys";
		this.pathSysCache = this.pathSys + File.separator + "cache";
		this.pathPublic = this.rootPath + File.separator + "public";
		this.pathSecurity = this.rootPath + File.separator + "security";
	}

	public void dispose() {
		this.rootPath = null;
		this.pathSys = null;
		this.pathSysCache = null;
		this.pathPublic = null;
		this.pathSecurity = null;
	}

	private DoIApp currentApp;
	private String rootPath;

	// 应用数据目录-系统
	private String pathSys;

	// 应用数据目录-系统-缓存
	private String pathSysCache;

	// 应用数据目录-公共
	private String pathPublic;

	// 应用数据目录-安全
	private String pathSecurity;

	@Override
	public DoIApp getCurrentApp() {
		return this.currentApp;
	}

	@Override
	public String getRootPath() {
		return this.rootPath;
	}

	@Override
	public String getPathSys() {
		return this.pathSys;
	}

	@Override
	public String getPathPublic() {
		return this.pathPublic;
	}

	@Override
	public String getPathSysCache() {
		return this.pathSysCache;
	}

	@Override
	public String getPathSecurity() {
		return this.pathSecurity;
	}

	// 获取完整的路径
	@Override
	public String getFileFullPathByName(String _fileName) throws Exception {
		if (_fileName.indexOf("..") >= 0 || _fileName.indexOf("~") >= 0)
			throw new Exception("..~等符号在路径中被禁止!");
		if (_fileName.startsWith(DoISourceFS.DATA_PREFIX)) {
			String _related_url = _fileName.substring(DoISourceFS.DATA_PREFIX.length());
			return this.rootPath + "/" + _related_url;
		} else {
			return null;
		}
	}
}
