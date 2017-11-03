package doext.implement;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import core.DoServiceContainer;
import core.helper.DoJsonHelper;
import core.interfaces.DoIScriptEngine;
import core.object.DoInvokeResult;
import core.object.DoSingletonModule;
import doext.define.do_Sensor_IMethod;

/**
 * 自定义扩展SM组件Model实现，继承DoSingletonModule抽象类，并实现do_Sensor_IMethod接口方法；
 * #如何调用组件自定义事件？可以通过如下方法触发事件：
 * this.model.getEventCenter().fireEvent(_messageName, jsonResult);
 * 参数解释：@_messageName字符串事件名称，@jsonResult传递事件参数对象； 获取DoInvokeResult对象方式new
 * DoInvokeResult(this.getUniqueKey());
 */
public class do_Sensor_Model extends DoSingletonModule implements do_Sensor_IMethod, SensorEventListener {
	/**
	 * 传感器类型： 1、加速度传感器； 2、罗盘； 3、转角； 4、陀螺仪； 5、距离传感器
	 * 
	 */
	private Context mContext;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	private PowerManager localPowerManager = null;// 电源管理对象
	private WakeLock localWakeLock = null;// 电源锁

	// 转角
	private Sensor accelerometer; // 加速度传感器
	private Sensor magnetic; // 地磁场传感器
	private float[] accelerometerValues = new float[3];
	private float[] magneticFieldValues = new float[3];
	private boolean isAngle = false;

	public do_Sensor_Model() throws Exception {
		super();
		mContext = DoServiceContainer.getPageViewFactory().getAppContext();
		sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

	}

	/**
	 * 同步方法，JS脚本调用该组件对象方法时会被调用，可以根据_methodName调用相应的接口实现方法；
	 * 
	 * @_methodName 方法名称
	 * @_dictParas 参数（K,V），获取参数值使用API提供DoJsonHelper类；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public boolean invokeSyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		// ...do something
		if ("start".equals(_methodName)) {
			start(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("stop".equals(_methodName)) {
			stop(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		if ("getSensorData".equals(_methodName)) {
			getSensorData(_dictParas, _scriptEngine, _invokeResult);
			return true;
		}
		return super.invokeSyncMethod(_methodName, _dictParas, _scriptEngine, _invokeResult);
	}

	/**
	 * 异步方法（通常都处理些耗时操作，避免UI线程阻塞），JS脚本调用该组件对象方法时会被调用， 可以根据_methodName调用相应的接口实现方法；
	 * 
	 * @_methodName 方法名称
	 * @_dictParas 参数（K,V），获取参数值使用API提供DoJsonHelper类；
	 * @_scriptEngine 当前page JS上下文环境
	 * @_callbackFuncName 回调函数名 #如何执行异步方法回调？可以通过如下方法：
	 *                    _scriptEngine.callback(_callbackFuncName,
	 *                    _invokeResult);
	 *                    参数解释：@_callbackFuncName回调函数名，@_invokeResult传递回调函数参数对象；
	 *                    获取DoInvokeResult对象方式new
	 *                    DoInvokeResult(this.getUniqueKey());
	 */
	@Override
	public boolean invokeAsyncMethod(String _methodName, JSONObject _dictParas, DoIScriptEngine _scriptEngine, String _callbackFuncName) throws Exception {
		// ...do something
		return super.invokeAsyncMethod(_methodName, _dictParas, _scriptEngine, _callbackFuncName);
	}

	/**
	 * 获取传感器数值；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void getSensorData(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		int _sensorType = DoJsonHelper.getInt(_dictParas, "sensorType", -1);

		switch (_sensorType) {
		case 1:
			getSensorData(_invokeResult, f_accelerometer[0], f_accelerometer[1], f_accelerometer[2], 1);
			break;
		case 2:
			getSensorData(_invokeResult, magnetic_field[0], magnetic_field[1], magnetic_field[2], 2);
			break;
		case 3:
			getSensorData(_invokeResult, values[1], values[2], values[0], 3);
			break;
		case 4:
			getSensorData(_invokeResult, type_gyroscope[0], type_gyroscope[1], type_gyroscope[2], 4);
			break;
		case 5:
			if (type_proximity[0] == 3.0 || type_proximity[0] == 0.0) {
				getSensorData(_invokeResult, 0, 0, 0, 5);
			} else {
				getSensorData(_invokeResult, 1, 0, 0, 5);
			}
			break;
		default:
			throw new Exception("sensorType 参数错误！");
		}
	}

	private void getSensorData(DoInvokeResult _invokeResult, float x, float y, float z, int type) {
		try {
			JSONObject _result = new JSONObject();
			_result.put("sensorType", type);
			JSONObject _objData = new JSONObject();
			_objData.put("x", x);
			_objData.put("y", y);
			_objData.put("z", z);
			_result.putOpt("data", _objData);
			_invokeResult.setResultNode(_result);
		} catch (Exception e) {
			DoServiceContainer.getLogEngine().writeError("do_GyroSensor_Model  \n\t", e);
		}
	}

	/**
	 * 开始从传感器采集数据；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void start(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {

		int _sensorType = DoJsonHelper.getInt(_dictParas, "sensorType", -1);
		switch (_sensorType) {
		case 1:
			isAngle = false;
			getSensorType(Sensor.TYPE_ACCELEROMETER);
			break;
		case 2:
			isAngle = false;
			getSensorType(Sensor.TYPE_MAGNETIC_FIELD);
			break;
		case 3:
			isAngle = true;
			if (sensorManager != null) {
				// 初始化加速度传感器
				accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
				// 初始化地磁场传感器
				magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
			}
			if (accelerometer != null && magnetic != null) {
				// 注册监听
				sensorManager.registerListener(this, accelerometer, Sensor.TYPE_ACCELEROMETER);
				sensorManager.registerListener(this, magnetic, Sensor.TYPE_MAGNETIC_FIELD);
			}

			break;
		case 4:
			isAngle = false;
			getSensorType(Sensor.TYPE_GYROSCOPE);
			break;
		case 5:
			isAngle = false;
			localPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
			// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
			localWakeLock = this.localPowerManager.newWakeLock(32, "距离传感器");// 第一个参数为电源锁级别，第二个是日志tag
			getSensorType(Sensor.TYPE_PROXIMITY);
			break;
		default:
			throw new Exception("sensorType 参数错误！");
		}
	}

	private void getSensorType(int senSorType) {
		if (sensorManager != null) {
			sensor = sensorManager.getDefaultSensor(senSorType);
		}
		if (sensor != null) {
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	/**
	 * 停止从传感器采集数据；
	 * 
	 * @_dictParas 参数（K,V），可以通过此对象提供相关方法来获取参数值（Key：为参数名称）；
	 * @_scriptEngine 当前Page JS上下文环境对象
	 * @_invokeResult 用于返回方法结果对象
	 */
	@Override
	public void stop(JSONObject _dictParas, DoIScriptEngine _scriptEngine, DoInvokeResult _invokeResult) throws Exception {
		int _sensorType = DoJsonHelper.getInt(_dictParas, "sensorType", -1);
		switch (_sensorType) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			if (sensorManager != null) {
				sensorManager.unregisterListener(this);
			}
			if (localWakeLock != null) {
				localWakeLock.release();
			}
			break;
		default:
			throw new Exception("sensorType 参数错误！");
		}
	}

	private float f_accelerometer[] = new float[3];
	private float magnetic_field[] = new float[3];
	private float type_gyroscope[] = new float[3];
	private float type_proximity[] = new float[1];

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			if (isAngle) {
				accelerometerValues = event.values;
			} else {
				f_accelerometer[0] = event.values[0];
				f_accelerometer[1] = event.values[1];
				f_accelerometer[2] = event.values[2];
				changeEvent(1, f_accelerometer[0], f_accelerometer[1], f_accelerometer[2]);
			}

		} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			if (isAngle) {
				magneticFieldValues = event.values;
			} else {
				magnetic_field[0] = event.values[0];
				magnetic_field[1] = event.values[1];
				magnetic_field[2] = event.values[2];
				changeEvent(2, magnetic_field[0], magnetic_field[1], magnetic_field[2]);
			}
		} else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
			// 魅蓝[0.0&1.0] 联想[0.0&8.0] mx2[3.0&7.0]
			type_proximity[0] = event.values[0];
			if (event.values[0] == 0.0 || event.values[0] == 3.0) {
				changeEvent(5, 0, 0, 0);// 0 表示靠近 1表示远离
				if (localWakeLock.isHeld()) {
					return;
				} else {
					localWakeLock.acquire();// 申请设备电源锁
				}
			} else {
				changeEvent(5, 1, 0, 0);// 0 表示靠近 1表示远离
				if (localWakeLock.isHeld()) {
					return;
				} else {
					localWakeLock.setReferenceCounted(false);
					localWakeLock.release(); // 释放设备电源锁
				}

			}

		} else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			type_gyroscope[0] = event.values[0];
			type_gyroscope[1] = event.values[1];
			type_gyroscope[2] = event.values[2];
			changeEvent(4, type_gyroscope[0], type_gyroscope[1], type_gyroscope[2]);
		}
		if (isAngle) {
			calculateOrientation();
		}
	}

	/**
	 * 
	 * @param type
	 *            传感器类型
	 * @param x
	 * @param y
	 * @param z
	 */
	private void changeEvent(int type, float x, float y, float z) {
		try {
			DoInvokeResult _invokeResult = new DoInvokeResult(getUniqueKey());
			JSONObject _obj = new JSONObject();

			_obj.put("sensorType", type);
			JSONObject _objData = new JSONObject();
			_objData.put("x", x);
			_objData.put("y", y);
			_objData.put("z", z);
			_obj.putOpt("data", _objData);
			_invokeResult.setResultNode(_obj);
			getEventCenter().fireEvent("change", _invokeResult);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void dispose() {
		sensorManager.unregisterListener(this);
		localWakeLock.release();
		super.dispose();
	}

	float[] values;

	private void calculateOrientation() {
		values = new float[3];
		float[] R = new float[9];
		SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
		SensorManager.getOrientation(R, values);
		values[0] = (float) Math.toDegrees(values[0]);
		values[1] = (float) Math.toDegrees(values[1]) * -1;
		values[2] = (float) Math.toDegrees(values[2]);
		if (values[0] > 0) {
			values[0] = 180 - values[0];
		} else {
			values[0] = -values[0] - 180;
		}
		changeEvent(3, values[1], values[2], values[0]);
	}
}