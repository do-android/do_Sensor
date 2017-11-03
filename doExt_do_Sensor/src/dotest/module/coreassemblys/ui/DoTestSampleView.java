package dotest.module.coreassemblys.ui;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import core.interfaces.DoIScriptEngine;
import core.interfaces.DoIUIModuleView;
import core.object.DoInvokeResult;
import core.object.DoUIModule;

public class DoTestSampleView extends LinearLayout implements DoIUIModuleView {

	private static int number = 1;

	public DoTestSampleView(Context context) {
		super(context);
		TextView tv = new TextView(context);
		tv.setText("Sample Page" + number);
		tv.setTextSize(20);
		tv.setGravity(Gravity.CENTER_VERTICAL);
		addView(tv, new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)));
		number++;
	}

	@Override
	public void loadView(DoUIModule _uiModule) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRedraw() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onPropertiesChanging(Map<String, String> _changedValues) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPropertiesChanged(Map<String, String> _changedValues) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean invokeSyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invokeAsyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, String _callbackFuncName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DoUIModule getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
