package dotest.module.frame.debug;

import java.util.HashMap;
import java.util.Map;

import core.interfaces.DoIApp;
import core.interfaces.DoIPage;
import core.interfaces.DoIScriptEngine;
import core.object.DoCallBackTask;
import core.object.DoInvokeResult;
import dotest.module.frame.debug.DoService.EventCallBack;

public class DoScriptEngine implements DoIScriptEngine {

	private Map<String, EventCallBack> dictCallBack;
	private DoIApp currentApp;
	private DoIPage currentPage;

	public DoScriptEngine() {
		dictCallBack = new HashMap<String, DoService.EventCallBack>();
	}

	@Override
	public void callback(String _methodName, DoInvokeResult _invokeResult) {
		if (!this.dictCallBack.containsKey(_methodName)) {
			return;
		}
		try {
			this.dictCallBack.get(_methodName).eventCallBack(_invokeResult.getResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {

	}

	@Override
	public DoIApp getCurrentApp() {
		return currentApp;
	}

	@Override
	public DoIPage getCurrentPage() {
		return currentPage;
	}

	@Override
	public void init() {

	}

	@Override
	public void loadScripts(String arg0) throws Exception {

	}

	@Override
	public void setCurrentApp(DoIApp currentApp) {
		this.currentApp = currentApp;
	}

	@Override
	public void setCurrentPage(DoIPage currentPage) {
		this.currentPage = currentPage;
	}

	public DoInvokeResult CreateInvokeResult(String _uniqueKey) {
		DoInvokeResult _invokeResult = new DoInvokeResult(_uniqueKey);
		return _invokeResult;
	}

	public void addCallBack(String _eventID, EventCallBack _eventCallBack) {
		this.dictCallBack.put(_eventID, _eventCallBack);
	}

	@Override
	public DoCallBackTask createCallBackTask(String _callbackMethodName, String _moduleAddess) {
		DoCallBackTask _callbackTask = new DoCallBackTask(this, _callbackMethodName, _moduleAddess);
		return _callbackTask;
	}

	@Override
	public void callLoadScriptsAsModel(String _scripts, String _libName, String _fileName) {

	}

	@Override
	public void callLoadScriptsAsModelWithPreDefine(String _uiRootViewAddress, String _scripts, String _fileName) {

	}

	@Override
	public void callLoadScriptsAsModel() {

	}

}
