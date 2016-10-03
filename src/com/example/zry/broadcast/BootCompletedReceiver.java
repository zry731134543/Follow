package com.example.zry.broadcast;

import com.example.zry.service.GetSetpService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 开机后自动启动 StepService
 * @author zhangruyi
 * 
 */
public class BootCompletedReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			Intent newintent=new Intent(context,GetSetpService.class);
			context.startService(newintent);
		}
	}

}
