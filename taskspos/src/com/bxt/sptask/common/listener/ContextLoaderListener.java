package com.bxt.sptask.common.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bxt.sptask.common.holder.ApplicationContextHolder;
import com.bxt.sptask.taskinit.service.TaskInitService;
import com.bxt.sptask.taskinit.service.impl.TaskInitServiceImpl;

public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {
	private static final Logger	LOGGER = LoggerFactory.getLogger(ContextLoaderListener.class);
	//private static final String	START_CONFIG_FILE = "startInitialization";
	WebApplicationContext webApplicationContext =null;
	Timer timer = new Timer();
	
	public void contextInitialized(ServletContextEvent contextEvent) {
		super.contextInitialized(contextEvent);
		try {
			webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(contextEvent.getServletContext());
			ApplicationContextHolder.getInstance().setContext(webApplicationContext);
			LOGGER.info("加载Spring 配置成功");
			executeStartInitialization(contextEvent);
			//initTask();
			LOGGER.info("执行初始化配置成功");
			LOGGER.info("部署目录:"+ contextEvent.getServletContext().getRealPath(""));
		} catch (Throwable e) {
			LOGGER.error("加载Spring 配置失败: " + e.getMessage());
		}
	}
	
	private void executeStartInitialization(ServletContextEvent sce) {
		String msg = "系统启动失败，";
		try {
//			Properties properties = PropertiesReader.getProperties(START_CONFIG_FILE);
//			for (Object o : properties.values()) {
//				String className = (String) o;
//				if (StringUtils.isNotEmpty(className)) {
//					Class<?> c = Class.forName(className);
//					Object obj = c.newInstance();
//					if (obj instanceof StartInitialization) {
//						((StartInitialization) obj).execute(sce);
//					} else {
//						throw new RuntimeException(msg + className + "类型错误，需要实现com.inter3i.task.common.start.StartInitialization");
//					}
//				}
//			}
		} catch (Exception e) {
			LOGGER.error(msg, e);
			throw new RuntimeException(msg + e.getMessage(), e);
		}
	}
	
	
	private void initTask(){
		// //初始化任务
	       /*TimerTask taskInit = new TimerTask(){
	           @Override
	           public void run(){
	        	   TaskInitService tiservInit = new TaskInitServiceImpl();
	       		   tiservInit.initTaskHashmap(webApplicationContext);
	           }
	       };
	       Calendar nowInit=Calendar.getInstance();
	       timer.schedule(taskInit, nowInit.getInstance ().getTime(), 3600*5 * 1000); 
	       
	       //读取爬虫等待的任务的Key到临时HashMap中
	       TimerTask taskWaintKey = new TimerTask(){
	           @Override
	           public void run(){
	        	   try{
		        	   TaskInitService tiservWait = new TaskInitServiceImpl();
		       		   tiservWait.waitTaskKeyRead(webApplicationContext);
	        	   }catch(Exception e){
	        		   e.printStackTrace();
	        	   }
	           }
	       };
	       timer.schedule(taskWaintKey,(60 * 1000),(60 * 1000)); 
	       
	       //读取任务到缓存
	       TimerTask handleTaskWaintKey = new TimerTask(){
	           @Override
	           public void run(){
	        	   try{
	        		   TaskInitService tiservWait = new TaskInitServiceImpl();
	        		   tiservWait.waitTaskKeyHandle(webApplicationContext);
	        	   }catch(Exception e){
	        		   e.printStackTrace();
	        	   }
	           }
	       };
	       timer.schedule(handleTaskWaintKey,(80 * 1000),(38 * 1000)); 
	       
	       //超时处理
	       TimerTask handleOutTime = new TimerTask(){
	           @Override
	           public void run(){
	        	   try{
	        		   TaskInitService tiservWait = new TaskInitServiceImpl();
	        		   tiservWait.handleOutTimeTask(webApplicationContext);
	        	   }catch(Exception e){
	        		   e.printStackTrace();
	        	   }
	           }
	       };
	       timer.schedule(handleOutTime,(120 * 1000),(900 * 1000)); 
		*/
	}

}
