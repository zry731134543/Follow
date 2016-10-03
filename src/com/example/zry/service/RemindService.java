package com.example.zry.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.data.db.PedometerDB;
import com.example.wo.AlarmReceiver;
import com.example.wo.SaveTime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class RemindService extends Service {
	private PedometerDB db;
	List<SaveTime> list=new ArrayList<SaveTime>();	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		Log.d("GetStepService", "创建RemindService");
		db=PedometerDB.getInstance(RemindService.this);
		list=db.queryRemind(list);
		Log.d("GetStepService", "获取到"+list.size()+"条数据");
//		Intent intent = new Intent(RemindService.this, AlarmReceiver.class);  
//		PendingIntent pi = PendingIntent.getBroadcast(RemindService.this, 0, intent, 0);  
////		 过5s 执行这个闹铃  
//		Calendar calendar = Calendar.getInstance();  
//		calendar.setTimeInMillis(System.currentTimeMillis());  
////		calendar.add(Calendar.MINUTE, 1);  
//		calendar.add(Calendar.SECOND, 0);  
////		进行闹铃注册
//		AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);  
//		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);  
		new Thread(){
			@Override
			public void run(){
				Log.d("GetStepService", "开启线程");
				addCaledar(filterList());
			}
		}.start();
		super.onCreate();
	}
	
	private void addCaledar(List<HoursAndMunite> li){
		Log.d("GetStepService", "设置提醒的时间");
		for(int i=0;i<li.size();i++){
			Calendar calendar = Calendar.getInstance();  
			calendar.setTimeInMillis(System.currentTimeMillis());  
			Intent intent = new Intent(RemindService.this, AlarmReceiver.class);  
			PendingIntent pi = PendingIntent.getBroadcast(RemindService.this, 0, intent, 0);  
			calendar.add(Calendar.HOUR, li.get(i).hours);  
			calendar.add(Calendar.MINUTE, li.get(i).minute); 
			Log.d("GetStepService",i+"------------"+li.get(i).hours+":"+li.get(i).minute);
			//进行闹铃注册
			AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);  
			manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi); 
		}
		
	}
	
	private List<HoursAndMunite> filterList(){
		Log.d("GetStepService", "过滤器");
		List<HoursAndMunite> li=new ArrayList<HoursAndMunite>();
		for(int i=0;i<list.size();i++){
			Calendar c = Calendar.getInstance();  
			c.setTimeInMillis(System.currentTimeMillis()); 
			String[] times=list.get(i).time.split(":");
			int hours=Integer.parseInt(times[0]);
			int minute=Integer.parseInt(times[1]);
			int curr_hours=c.get(Calendar.HOUR_OF_DAY);
			int curr_minute=c.get(Calendar.MINUTE);
			Log.d("GetStepService", "当前时间"+curr_hours+":"+curr_minute);
			Log.d("GetStepService", "提醒时间"+hours+":"+minute);
			if(hours>=curr_hours){
				int m=minute-curr_minute;
				int h=m<0?hours-curr_hours-1:hours-curr_hours;
				li.add(new HoursAndMunite(h,m>0?m:60+m));
				Log.d("GetStepService", h+":"+(m>0?m:m+60));
			}else{
				//判断是否是只响一次（是，删除该数据）
			}
		}
		Log.d("GetStepService", "过滤后数据个数"+li.size());
		return li;
	}
	
	private class HoursAndMunite{
		public int hours;
		public int minute;
		public HoursAndMunite(int hours,int minute){
			this.hours=hours;
			this.minute=minute;
		}
	}
}
