package com.example.zry.service;

import android.os.Bundle;
import android.os.Message;

/**
 * 存放实时数据表数据
 * @author zhangruyi 3.20
 * 
 *
 */
public class DateTimeData {
	/**
	 * 运动时间
	 */
	public String date;
	/**
	 * 总时间
	 */
	public int time;
	/**
	 * 总步数
	 */
	public int step;
	/**
	 * 总热量
	 */
	public double calorimetry;
	/**
	 * 总里数
	 */
	public double kilometer;
	
	public DateTimeData()
	{
		
	}
	/**
	 * 构造方法
	 * @param date 运动时间
	 * @param time 总时间
	 * @param totalStep 步数
	 * @param totalCalorimetry 总热量
	 * @param totalKilometer 总里数
	 */
	public DateTimeData(String date, int time, int step, double calorimetry,
			double kilometer) {
		this.date = date;
		this.time = time;
		this.step = step;
		this.calorimetry = calorimetry;
		this.kilometer = kilometer;
	}
	public DateTimeData(Message msg,String date){
		Bundle data=msg.getData();
		this.date = date;
		this.step=data.getInt("step");
		this.time=data.getInt("time");
		this.calorimetry=data.getDouble("calories");
		this.kilometer=data.getDouble("kile");
		
	}
}
