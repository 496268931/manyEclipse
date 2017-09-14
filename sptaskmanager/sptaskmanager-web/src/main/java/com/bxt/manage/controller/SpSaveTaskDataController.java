package com.bxt.manage.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bxt.sptask.service.SpSaveTaskDataService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/addweibo")
public class SpSaveTaskDataController {
	
	@Autowired
	SpSaveTaskDataService spSaveTaskDataService;
	
	@RequestMapping(value = "/addTaskGrabInfo")
    @ResponseBody
	public void saveTaskDataInfo(HttpServletRequest request, HttpServletResponse response){
		String taskid = "";
		JSONObject result_json = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		
		String stype = request.getParameter("type"); //remotecommtask_cache
		String stask = request.getParameter("task");
		String stoken = request.getParameter("token");
		String sdata = request.getParameter("data");
		try{
			 //读取请求内容
	        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String line = null;
	        StringBuilder str_sb = new StringBuilder();
	        while((line = br.readLine())!=null){
	        	str_sb.append(line);
	        }

	      
	        String resultData = str_sb.toString();
	        URLDecoder.decode(resultData, "UTF-8"); //对读取的内容进行UTF-8解码
			
			HashMap<String,String> hmobj = new HashMap<String,String>();
			hmobj.put("stask", stask);
			hmobj.put("stoken", stoken);
			hmobj.put("sdata", resultData);
			if(stype.equals("remotecommtask_cache")){
				//处理提交数据
				spSaveTaskDataService.saveSpGrabInfo(hmobj);
			}
			
			result_json.put("result", "true");
			result_json.put("result", "任务提交成功");
		}catch(Exception e){
			// return array("errorcode" => $error_code, "error" => $error_str);
			result_json.put("result", "false");
			result_json.put("result", "任务提交失败");//对于提交失败的任务，需要把爬取的数据保存到临时处理缓存中，避免二次爬取.
			try {
				out = response.getWriter();
				out.write(result_json.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	

}
