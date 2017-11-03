package dotest.module.frame.debug;

import android.app.Activity;
import android.app.Application;
import core.interfaces.DoIPageViewFactory;

public class DoPageViewFactory implements DoIPageViewFactory {

	private Activity currentActivity;
	private Application application;

	@Override
	public void openPage(String _pageID, String _appID, String _uiPath, String _scriptFile, String _animationType, String _data, String _isFullScreen, String _keyboardMode, String _callbackFuncName) {

	}

	@Override
	public void closePage(String _animationType, String _data, int _layer) {

	}

	@Override
	public void closePage(String _animationType, String _data, String _id) {

	}

	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}

	@Override
	public Activity getAppContext() {
		return currentActivity;
	}

	@Override
	public Application getApplicationContext() {
		return this.application;
	}

	@Override
	public void setApplicationContext(Application _application) {
		this.application = _application;
	}

	@Override
	public void exitApp() {

	}

}
