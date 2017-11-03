package dotest.module.frame.debug;

import android.content.Context;
import core.helper.DoTextHelper;
import core.interfaces.DoIApp;
import core.interfaces.DoIDataFS;
import core.interfaces.DoIInitDataFS;
import core.interfaces.DoIScriptEngine;
import core.interfaces.DoISourceFS;
import core.object.DoMultitonModule;

public class DoApp implements DoIApp {

	private DoIDataFS dataFS;
	private DoISourceFS sourceFS;
	private DoIInitDataFS initDataFS;
	private String appID;

	public DoApp(Context _context) {
		this.appID = "appid_" + DoTextHelper.getTimestampStr();
		this.dataFS = new DoDataFS(_context, this);
		this.sourceFS = new DoSourceFS(_context, this);
		this.initDataFS = new DoInitDataFS(_context, this);
	}

	@Override
	public String getAppID() {
		return this.appID;
	}

	@Override
	public DoIDataFS getDataFS() {
		return dataFS;
	}

	@Override
	public DoISourceFS getSourceFS() {
		return sourceFS;
	}

	@Override
	public DoIInitDataFS getInitDataFS() {
		return initDataFS;
	}

	@Override
	public DoIScriptEngine getScriptEngine() {
		return null;
	}

	@Override
	public DoMultitonModule createMultitonModule(String _typeID, String _id) throws Exception {
		return null;
	}

	@Override
	public DoMultitonModule getMultitonModuleByAddress(String _key) {
		return null;
	}

	@Override
	public boolean deleteMultitonModule(String _address) {
		return false;
	}

	@Override
	public void dispose() {
		if (this.dataFS != null) {
			this.dataFS.dispose();
			this.dataFS = null;
		}
		if (this.initDataFS != null) {
			this.initDataFS.dispose();
			this.initDataFS = null;
		}
		if (this.sourceFS != null) {
			this.sourceFS.dispose();
			this.sourceFS = null;
		}

	}

	@Override
	public void loadApp(String _appID) throws Exception {

	}

	@Override
	public void loadScripts() throws Exception {

	}

	@Override
	public void fireEvent(String _eventName, Object _result) {

	}

}
