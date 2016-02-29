package com.itboye.guangda.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.widget.Toast;

public class ToastTime extends TimerTask {
	 Toast toast = null;
	 Timer timer=null;
	 public ToastTime(Toast toast,Timer timer) {
	  // TODO Auto-generated constructor stub
	  this.toast = toast;
	  this.timer=timer;
	 }
	 @Override
	 public void run() {
	  // TODO Auto-generated method stub
	  //获取当前时间
	  long firstTime = System.currentTimeMillis();
	  while(System.currentTimeMillis()-firstTime < 5000){//显示十秒
	   toast.show();
	  }
	  timer.cancel();
	 }
	}
