package dotest.module.frame.debug;

import org.json.JSONObject;

import core.interfaces.DoIApp;
import core.interfaces.DoIPage;
import core.interfaces.DoIPageView;
import core.interfaces.DoIScriptEngine;
import core.object.DoMultitonModule;
import core.object.DoSourceFile;
import core.object.DoUIContainer;
import core.object.DoUIModule;

public class DoPage implements DoIPage {

	private DoIPageView pageView;
	private DoApp currentApp;

	public DoPage(DoApp doApp, DoIPageView doIPageView) {
		this.currentApp = doApp;
		this.pageView = doIPageView;
	}

	@Override
	public DoIApp getCurrentApp() {
		return currentApp;
	}

	@Override
	public DoIPageView getPageView() {
		return pageView;
	}

	@Override
	public DoSourceFile getUIFile() {
		return null;
	}

	@Override
	public DoIScriptEngine getScriptEngine() {
		return null;
	}

	@Override
	public DoUIModule getRootView() {
		return null;
	}

	@Override
	public String getData() {
		return null;
	}

	@Override
	public void setData(String data) {

	}

	@Override
	public DoUIModule createUIModule(DoUIContainer _uiContainer, JSONObject _uiModuleNode) throws Exception {
		return null;
	}

	@Override
	public void removeUIModule(DoUIModule _uiModule) {

	}

	@Override
	public DoUIModule getUIModuleByAddress(String _key) {
		return null;
	}

	@Override
	public void loadRootUiContainer() throws Exception {

	}

	@Override
	public void loadScriptEngine(String _scriptFile, String _scriptType, String _fileName) throws Exception {

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

	}

	@Override
	public void setFullScreen(boolean isFullScreen) {

	}

	@Override
	public boolean isFullScreen() {
		return false;
	}

	@Override
	public void setTransparent(boolean transparent) {

	}

	@Override
	public boolean isTransparent() {
		return false;
	}

	@Override
	public void setDesignScreenResolution(int screenWidth, int screenHeight) {

	}

	@Override
	public int getDesignScreenWidth() {
		return 0;
	}

	@Override
	public int getDesignScreenHeight() {
		return 0;
	}

	@Override
	public void fireEvent(String _eventName, Object _result) {

	}

}
