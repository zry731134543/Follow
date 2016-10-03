package com.example.zry.service;
/**
 * 用于回调GetSetpService的接口
 * @author zhangruyi
 *
 */
public interface OnprogressListener {
	void onprogress(int step,long time,double velocity);
}
