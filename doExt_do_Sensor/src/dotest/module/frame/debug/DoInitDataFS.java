package dotest.module.frame.debug;

import android.content.Context;
import core.helper.DoIOHelper;
import core.interfaces.DoIApp;
import core.interfaces.DoIInitDataFS;

public class DoInitDataFS implements DoIInitDataFS {

	public DoInitDataFS(Context _context, DoIApp _doApp) {
		this.currentApp = _doApp;
		this.onInit(_context);
	}

	private void onInit(Context _context) {
		this.rootPath = DoUtils.getInitDataRootPath(_context, this.currentApp.getAppID());
		if (!DoIOHelper.existDirectory(this.rootPath)) {
			DoIOHelper.createDirectory(this.rootPath);
		}
	}

	public void dispose() {
		this.rootPath = null;
	}

	private DoIApp currentApp;
	private String rootPath;

	@Override
	public DoIApp getCurrentApp() {
		return this.currentApp;
	}

	@Override
	public String getRootPath() {
		return this.rootPath;
	}
}
