package com.bxt.sptask.taskhandle.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bxt.sptask.taskhandle.service.TaskAgentService;

@Controller
@RequestMapping(value = "/TaskAgentHandle")
public class TaskAgentHandle{
	public static final Logger	LOGGER	= LoggerFactory.getLogger(TaskAgentHandle.class);
	
	@Autowired
	private TaskAgentService taskAgentService;
	
	@RequestMapping(value="/taskAgent")
	public void taskAgent(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		
		String type = request.getParameter("type");
		String tasktype = request.getParameter("tasktype");
		String host = request.getParameter("host");
		
		String staskJson = "";
		
		JSONObject jsonObj = new JSONObject();
		try{
			String specifiedtype = request.getParameter("specifiedtype");
			String specifiedMac = request.getParameter("mac");
			String stoken = request.getParameter("token");
			
			//传递参数
			HashMap<String,String> hmobj = new HashMap<String,String>();
			
			hmobj.put("host",host);
			hmobj.put("tasktype", tasktype);
			hmobj.put("specifiedtype",specifiedtype);
			hmobj.put("specifiedMac", specifiedMac);
			hmobj.put("stoken", stoken);
			
			if(type != null){
				type = type.toUpperCase();
				if(type.equals("GET")){
					staskJson = taskAgentService.getPhpAgentTask(hmobj);
					if(staskJson != null && !staskJson.equals("")){
						out = response.getWriter();
						out.write(staskJson);
					}else{
						jsonObj.put("result",false);
						jsonObj.put("msg", "正在加载任务，请等待！");
						out = response.getWriter();
						out.write(jsonObj.toString());
					}
				}else{
					jsonObj.put("result",false);
					jsonObj.put("msg", "爬虫访问 typy为["+ type +"],请确认type类型");
					out = response.getWriter();
					out.write(jsonObj.toString());
				}
			}else{
				jsonObj.put("result",false);
				jsonObj.put("msg", "爬虫访问 typy为null,请调整访问类型再继续");
				out = response.getWriter();
				out.write(jsonObj.toString());
			}
		}catch(Exception e){
			jsonObj.put("result",false);
			jsonObj.put("msg", "爬虫读取任务报错，请检查任务数据或联系相关人员！");
			try {
				out = response.getWriter();
				out.write(jsonObj.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	
	
	@RequestMapping(value="/taskJAgent")
	public void taskJAgent(HttpServletRequest request, HttpServletResponse response){
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		
		String type = request.getParameter("type");
		String tasktype = request.getParameter("tasktype");
		String host = request.getParameter("host");
		
		JSONArray resulJosn = null;
		LinkedHashMap<String,Object> lkmap = new LinkedHashMap<String,Object>();
		String taskJson = "";
		try{
			String specifiedtype = request.getParameter("specifiedtype");
			String specifiedMac = request.getParameter("mac");
			//传递参数
			HashMap<String,String> hmobj = new HashMap<String,String>();
			hmobj.put("host",host);
			hmobj.put("tasktype", tasktype);
			hmobj.put("specifiedtype",specifiedtype);
			hmobj.put("specifiedMac", specifiedMac);
			if(type != null){
				type = type.toUpperCase();
				if(type.equals("GET")){
					long lstartTime = System.currentTimeMillis(); // 记录起始时间
					
					taskJson = taskAgentService.getAgentTask(hmobj);
					
					long lendTime = System.currentTimeMillis(); // 记录起始时间
					System.out.println("任务获取用时：["+(lendTime -lstartTime + "]"));
				}
			}else{
				lkmap.put("result",false);
				lkmap.put("msg", "爬虫访问 typy为null,请调整访问类型再继续");
			}
		}catch(Exception e){
			lkmap.put("result",false);
			lkmap.put("msg", "爬虫读取任务报错，请检查任务数据或联系相关人员！");
			resulJosn = JSONArray.fromObject(lkmap);
			e.printStackTrace();
		}
		
		if(lkmap == null){
			lkmap = new LinkedHashMap<String,Object>();
			lkmap.put("result",false);
			lkmap.put("msg", "没有对应任务，请确认！");
		}
		resulJosn = JSONArray.fromObject(lkmap);
		try {
			out = response.getWriter();
			out.write(resulJosn.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
