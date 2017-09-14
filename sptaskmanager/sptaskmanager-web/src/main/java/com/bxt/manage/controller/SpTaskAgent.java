package com.bxt.manage.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bxt.sptask.service.SpReadTaskService;
import com.bxt.sptask.service.TaskAgentService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/HandleTaskInfo")
public class SpTaskAgent {
	
	@Autowired
    private SpReadTaskService spReadTaskService ;
	
	@Autowired
	private TaskAgentService taskAgentService;
	/*
	 * 读取任务，并且保存任务到redis
	 */
	@RequestMapping(value="/rwTaskAgent")
	public void getTaskToRedis(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type");
		String tasktype = request.getParameter("tasktype");
		String host = request.getParameter("host");
		JSONArray resulJosn = null;
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		LinkedHashMap<String,Object> lkmap = new LinkedHashMap<String,Object>();
		String taskJson = "";
		try{
			
			//spReadTaskService.readTastFromDB();
			
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
					System.out.println(taskJson);
					System.out.println("任务获取用时：["+(lendTime -lstartTime + "]"));
				}
			}else{
				lkmap.put("result",false);
				lkmap.put("msg", "爬虫访问 typy为null,请调整访问类型再继续");
			}
			out = response.getWriter();
			out.write(taskJson);
		}catch(Exception e){
			lkmap.put("result",false);
			lkmap.put("msg", "爬虫读取任务报错，请检查任务数据或联系相关人员！");
			resulJosn = JSONArray.fromObject(lkmap);
			e.printStackTrace();
		}
		
	}

}
