package com.bxt.sptask.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bxt.sptask.utils.vo.EntityParams;

public class TaskJsonAnaly {
	
	public static EntityParams getTaskAndType(String strjson){
		EntityParams ep = new EntityParams();
		try{
			if(strjson != null){
				JSONArray json_obj = JSONArray.fromObject(strjson);
				for(int i = 0;i<json_obj.size();i++){
					JSONObject obj = (JSONObject)json_obj.get(i);
					ep.setStask(obj.getString("task"));
					ep.setStaskType(obj.getString("tasktype"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ep;
	}
	

}
