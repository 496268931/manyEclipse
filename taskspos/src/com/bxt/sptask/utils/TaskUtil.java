package com.bxt.sptask.utils;

import com.bxt.sptask.taskhandle.vo.TaskVo;

public class TaskUtil {
	
	public static String getTaskKey(String stasktype,String spcfdmac,
			String taskclassify,String stask){
		String staskKey = "";
		staskKey = getTaskKeyInfo(staskKey,stasktype);
		staskKey = getTaskKeyInfo(staskKey,spcfdmac);
		staskKey = getTaskKeyInfo(staskKey,taskclassify);
		staskKey = getTaskKeyInfo(staskKey,stask);
		return staskKey;
	}
	
	public static String getTaskKeyInfo(String staskkey,String skeyValue){
		if(skeyValue != null && !skeyValue.equals("")){
			if(staskkey != null && !staskkey.equals("")){
				staskkey = staskkey + "&&" + skeyValue;
			}else{
				staskkey = staskkey + skeyValue;
			}
		}else{
			if(staskkey != null && !staskkey.equals("")){
				staskkey = staskkey + "&&" + "null";
			}else{
				staskkey = staskkey + "null";
			}
		}
		return staskkey;
	}
	
	//处理任务KEY
	public static TaskVo taskKeyToTaskVo(String skey,String sflag){
		String stasktype = "",spacfdmac = "",staskcalssify = "",stask = "";
		Integer itasktype = null,itask = null;
		TaskVo  taskvo = new TaskVo();
		try{
			String[] sarKey = null;
			if(skey != null && !skey.equals("")){
				 sarKey = skey.split(sflag);
				 if(sarKey != null){
					 stasktype = sarKey[0];
					 if(stasktype != null && !stasktype.equals("") && !stasktype.equals("null")){
						 itasktype = Integer.parseInt(stasktype);
						 taskvo.setTasktype(itasktype);
					 }
					 spacfdmac = sarKey[1];
					 if(spacfdmac != null && !spacfdmac.equals("null")){
						 taskvo.setSpcfdmac(spacfdmac);
					 }
					 staskcalssify = sarKey[2];
					 if(staskcalssify != null && !staskcalssify.equals("null")){
						 taskvo.setTaskclassify(staskcalssify);
					 }
					 stask = sarKey[3];
					 if(stask != null && !stask.equals("") && !stask.equals("null")){
						 itask = Integer.parseInt(stask);
						 taskvo.setTask(itask);
					 }
				 }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return taskvo;
	}
	
	public static void main(String[] args) {
		TaskUtil tu = new TaskUtil();
		tu.taskKeyToTaskVo("2&&wangcc-fa-ad-cd&&null&&15","&&");
		System.out.println("处理完成");
	}

}
