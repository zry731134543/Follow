package com.example.jun;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

public class StepService extends Service {
	
	public static Boolean flag = false;
	private SensorManager sensorManager;
	private StepDetector stepDetector;
	 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate();
	
	    //这里开启了一个线程，因为后台服务也是在主线程中进行，这样可以安全点，防止主线程阻塞
	    new Thread(new Runnable() {
		public void run() 
		{
		    startStepDetector();
		}
	}).start();
}
	private void startStepDetector()
	{
		 flag = true;
	     stepDetector = new StepDetector(this);
	     sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);//获取传感器管理器的实例
	     Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//获得传感器的类型，这里获得的类型是加速度传感器
	     //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
	     sensorManager.registerListener(stepDetector, sensor,SensorManager.SENSOR_DELAY_FASTEST);
	          
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		super.onDestroy();
	        flag = false;
	        if (stepDetector != null) {
	            sensorManager.unregisterListener(stepDetector);
	        }
	}
}
	

