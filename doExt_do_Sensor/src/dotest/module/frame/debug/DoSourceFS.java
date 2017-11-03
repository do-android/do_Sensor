package dotest.module.frame.debug;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import core.helper.DoIOHelper;
import core.interfaces.DoIApp;
import core.interfaces.DoISourceFS;
import core.object.DoSourceFile;

public class DoSourceFS implements DoISourceFS {

	private DoIApp currentApp;
	private String defaultRootPath;
	private String mappingSourceRootPath;
	private String dataRootPath;
	private String initDataRootPath;
	private Map<String, DoSourceFile> dictSourceFiles;

	public DoSourceFS(Context _context, DoIApp _app) {
		this.currentApp = _app;
		this.onInit(_context);
	}

	private void onInit(Context _context) {
		this.defaultRootPath = DoUtils.getSourceRootPath(_context, this.currentApp.getAppID());
		this.initDataRootPath = DoUtils.getInitDataRootPath(_context, this.currentApp.getAppID());
		this.dataRootPath = DoUtils.getDataRootPath(_context, this.currentApp.getAppID());
		this.mappingSourceRootPath = DoUtils.getSourceRootPath(_context, this.currentApp.getAppID());
		this.dictSourceFiles = new HashMap<String, DoSourceFile>();
	}

	public void dispose() {
		this.currentApp = null;
		this.defaultRootPath = null;
		this.mappingSourceRootPath = null;
		this.initDataRootPath = null;
		this.clear();
		this.dictSourceFiles = null;
	}

	@Override
	public void clear() {
		if (this.dictSourceFiles != null) {
			for (String _key : this.dictSourceFiles.keySet()) {
				this.dictSourceFiles.get(_key).dispose();
			}
			this.dictSourceFiles.clear();
		}
	}

	@Override
	public DoIApp getCurrentApp() {
		return this.currentApp;
	}

	@Override
	public String getDefaultRootPath() {
		return this.defaultRootPath;
	}

	@Override
	public String getMappingSourceRootPath() {
		return this.mappingSourceRootPath;
	}

	@Override
	public DoSourceFile getSourceByFileName(String _fileName) throws Exception {
		String _fileFullName = this.getFileFullPathByName(_fileName);
		return this.getSourceByFileFullName(_fileName, _fileFullName);
	}

	@Override
	public DoSourceFile getSourceByFileFullName(String _fileName, String _fileFullName) {
		if (!dictSourceFiles.containsKey(_fileFullName)) {
			if (_fileFullName != null && DoIOHelper.existFile(_fileFullName)) {
				DoSourceFile _newAppFile = new DoSourceFile(this, _fileName, _fileFullName);
				this.dictSourceFiles.put(_fileFullName, _newAppFile);
				return _newAppFile;
			}
		}
		return this.dictSourceFiles.get(_fileFullName);
	}

	// 获取完整的路径
	@Override
	public String getFileFullPathByName(String _fileName) throws Exception {
		if (_fileName.indexOf("..") >= 0 || _fileName.indexOf("~") >= 0)
			throw new Exception("..~等符号在路径中被禁止!");

		if (_fileName.startsWith(SOURCE_PREFIX)) {
			String _related_url = _fileName.substring(SOURCE_PREFIX.length());
			//先判断mappingSource 目录中存不存在该文件，如果存在，返回，不存在，找默认的RootPath
			File _mFile = new File(this.mappingSourceRootPath, _related_url);
			if (_mFile.exists()) {
				return _mFile.getAbsolutePath();
			}
			return this.defaultRootPath + new File("", _related_url).getAbsolutePath();
		} else if (_fileName.startsWith(DATA_PREFIX)) {
			String _related_url = _fileName.substring(DATA_PREFIX.length());
			return this.dataRootPath + File.separator + _related_url;
		} else if (_fileName.startsWith(INIT_DATA_PREFIX)) {
			String _related_url = _fileName.substring(INIT_DATA_PREFIX.length());
			return this.initDataRootPath + new File("", _related_url).getAbsolutePath();
		} else {
			return null;
		}
	}
}
