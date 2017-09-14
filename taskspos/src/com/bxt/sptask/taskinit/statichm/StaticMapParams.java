package com.bxt.sptask.taskinit.statichm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StaticMapParams {
	public static HashMap<Integer,String> hmTemplate = new HashMap<Integer,String>(); //任务模板
	public static HashMap<String,List<String>> hmTaskSchID = new HashMap<String,List<String>>(); //任务ID
	public static HashMap<String,String> hmTaskWaitKey = new HashMap<String,String>();//没有取到任务的KEY
	public static HashMap<String,String> hmTempTaskWaitKey = new HashMap<String,String>();//没有取到任务的KEY
	public static HashMap<String,String> hmTaskWaitKeyHandle = new HashMap<String,String>();//
	
	public static List<String> listWaitHandleKey = new ArrayList<String>();
	
	//使用AtomicBoolean做同步
	public static AtomicBoolean abreadKeyflag = new AtomicBoolean(true);
	
	public static boolean breadKeyflag = true;
	

}
