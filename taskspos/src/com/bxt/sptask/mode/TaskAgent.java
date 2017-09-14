package com.bxt.sptask.mode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bxt.sptask.taskhandle.service.TaskAgentService;
import com.bxt.sptask.taskhandle.service.impl.TaskAgentServiceImpl;

public class TaskAgent extends HttpServlet { 
	private SqlMapClientTemplate sqlMapClientTemplate;
	private static final String	 USERVO_SQL_NAMESPACE_	= "com.bxt.sptask.admin.login.login.vo.";
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}


	@SuppressWarnings("unused")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		
		
		String type = request.getParameter("type");
		String tasktype = request.getParameter("tasktype");
		String host = request.getParameter("host");
		
		JSONArray resulJosn = null;
		LinkedHashMap<String,Object> lkmap = new LinkedHashMap<String,Object>();
		
		try{
			if(type != null){
				type = type.toUpperCase();
				if(type.equals("GET")){
					TaskAgentService itaskAgent = new TaskAgentServiceImpl();
					//lkmap = itaskAgent.getAgentTask(lkmap,sqlMapClientTemplate);
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
		out = response.getWriter();
		out.write(resulJosn.toString());
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		super .init();
        ServletContext servletContext = this .getServletContext();    
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        sqlMapClientTemplate = (SqlMapClientTemplate)ctx.getBean("sqlMapClientTemplate");
	}

}
