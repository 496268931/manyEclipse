package com.bxt.sptask.taskmanager.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bxt.sptask.common.vo.AjaxSimpleResult;
import com.bxt.sptask.sptmpconfig.service.TaskcfgtempService;
import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.taskmanager.service.SpTaskService;
import com.bxt.sptask.taskmanager.vo.SchTaskVO;

import net.sf.json.JSONObject;

/**
 * Description: TaskTestController 任务管理CONTROLLER控制器 

 * All Rights Reserved.
 *
 * @version V1.0  2016-12-13 下午 12:23:52 星期二
 * @author wxd(wxd@inter3i.com)
 */
@Controller
@RequestMapping(value = "/SpTaskController")
public class SpTaskController {
    private static Logger LOGGER = LoggerFactory.getLogger(SpTaskController.class);

	private static final String DETAIL_VIEW = "taskmanager/taskview";
	private static final String LIST_VIEW = "taskmanager/taskList";
	private static final String CREATE_VIEW = "taskmanager/createsptask";
	private static final String ADD_VIEW = "taskmanager/addsptask";

    @Autowired
    private SpTaskService spTaskService;
    
    @Autowired
    private TaskcfgtempService taskcfgtempService;

    /**
     * Description: 删除任务管理VO实体信息
     *
     * @version V1.0  2016-12-13 下午 12:23:52 星期二
     * @author wxd(wxd@inter3i.com)
     * @param taskTestVO
     * @param request
     * @return
     */
	@RequestMapping(value = "/deleteTaskTest")
	@ResponseBody
    public Object deleteTask(TaskVo taskVo, HttpServletRequest request) {
        AjaxSimpleResult result = new AjaxSimpleResult();
        try {
	        //taskVo.setModifyEmp(SessionUtils.getSessionUserId(request));
            this.spTaskService.deleteTaskTest(taskVo);
            result.setMessage("删除成功！");
        } catch (Exception e) {
            String errMsg = "系统异常，删除失败！";
            LOGGER.error(errMsg, e);
            result.setStatus(AjaxSimpleResult.ERROR);
            result.setMessage(errMsg);
        }
        return result;
    }@RequestMapping({"/saveChildTask"})
    @ResponseBody
    public Object saveChildTaskNode(HttpServletRequest request)
    {
      AjaxSimpleResult result = new AjaxSimpleResult();
      String sresult_json = "";
      String taskid = request.getParameter("taskid");
      String childtaskid = request.getParameter("childtaskid");
      try
      {
        if (taskid != null) {
          taskid = "2017020612372106316";

          sresult_json = this.spTaskService.saveChildTaskNode(taskid, childtaskid);

          result.setStatus(1);
          result.setMessage("修改成功");
        } else {
          result.setStatus(-1);
          result.setMessage("父任务ID为空错误！");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return sresult_json;
    }
	
	
	
	 @RequestMapping(value = "/saveTask")
	    @ResponseBody
	    public Object saveOrUpdateTask(@ModelAttribute TaskVo taskVo, HttpServletRequest request) {
		    AjaxSimpleResult result = new AjaxSimpleResult();
		    HashMap<String,String> result_hm = new HashMap<String,String>();
		    JSONObject node_json = new JSONObject();
		    String taskid = ""; String task_def = ""; String childtaskid = "";
		    try {
		      task_def = request.getParameter("task_def");
		      taskid = request.getParameter("taskid");
		      childtaskid = request.getParameter("childtaskid");

		      if ((taskid != null) && (!taskid.equals("")))
		      {
		        if (childtaskid != null) childtaskid.equals("");

		      }
		      else
		      {
		        taskid = this.spTaskService.insertTaskParam(task_def);
		      }
		      result_hm.put("taskid", taskid);
		      result_hm.put("childtaskid", childtaskid);

		      System.out.println("taskid=" + taskid);
		      result.setMessage("保存成功！");
		    } catch (Exception e) {
		      String errMsg = "系统异常，保存失败！";
		      LOGGER.error(errMsg, e);
		      result.setStatus(-1);
		      result.setMessage(errMsg);
		    }
		    return result_hm;
	    }

	

    /**
     * Description: 新增或修改任务管理信息
     *
     * @version V1.0  2016-12-13 下午 12:23:52 星期二
     * @author wxd(wxd@inter3i.com)
     * @param taskTestVO
     * @return
     */
    @RequestMapping(value = "/saveTaskInfo")
    @ResponseBody
    public Object saveOrUpdateTaskInfo(@ModelAttribute TaskVo taskVo, HttpServletRequest request) {
	    AjaxSimpleResult result = new AjaxSimpleResult();
	    try {
	    	String tasktype = request.getParameter("tasktype");
	    	String task = request.getParameter("task");
	    	String tasklevel = request.getParameter("tasklevel");
	    	String local = request.getParameter("");
	    	String remote = request.getParameter("remote");
	    	String activatetime = request.getParameter("activatetime");
	    	String conflictdelay = request.getParameter("conflictdelay");
	    	String remarks = request.getParameter("remarks");
	    	String taskparams = request.getParameter("taskparams");
	    	
	    	System.out.println(taskparams);

            //Long userId = SessionUtils.getSessionUserId(request);
           // taskVo.setModifyEmp(userId);
	        if (taskVo.getId() == null) {
	        	//taskVo.setCreateEmp(userId);
	            //spTaskService.insertTaskTest(taskVo);
	        } else {
	            //spTaskService.updateTaskTest(taskVo);
	        }
		    result.setMessage("保存成功！");
	    } catch (Exception e) {
		    String errMsg = "系统异常，保存失败！";
		    LOGGER.error(errMsg, e);
		    result.setStatus(AjaxSimpleResult.ERROR);
		    result.setMessage(errMsg);
	    }
	    return result;
    }

    /**
     * Description: 任务管理更新和新建跳转
     *
     * @version V1.0  2016-12-13 下午 12:23:52 星期二
     * @author wxd(wxd@inter3i.com)
	 * @param taskTestVO
	 * @param model
     * @return
     */
	@RequestMapping(value = "/toTaskDetail")
    public String toTaskTestDetail(TaskVo taskVo, Model model, HttpServletRequest request) {
        try {
        	if (taskVo.getId() != null) {
        		taskVo = spTaskService.searchTaskTestById(taskVo.getId());
                model.addAttribute("isView", request.getParameter("isView"));
        	}
			model.addAttribute("taskTestVO", taskVo);
        } catch (Exception e) {
            LOGGER.error("任务管理新建修改跳转异常", e);
        }
        return DETAIL_VIEW;
    }

    /**
     * Description: 查找任务管理信息列表
     *
     * @version V1.0  2016-12-13 下午 12:23:52 星期二
     * @author wxd(wxd@inter3i.com)
     * @param request HttpServletRequest
	 * @param taskTestVO
     * @return
     */
    @RequestMapping(value = "/searchTaskTestList")
    public String searchTaskTestList(@ModelAttribute TaskVo taskVo, HttpServletRequest request, Model model) {
    	try {
    		// 设置 TABLE_ID 标示唯一的JMESA表格组件
			final String TABLE_ID = "taskTestList";
			// 查询列表数据及其记录数
			//Collection<?> items = JmesaQueryUtil.getItems(request, TABLE_ID, taskTestVO, taskTestService, "searchTaskTestList", "searchTaskTestCount");
			//model.addAttribute("taskTestList", items);
			model.addAttribute("taskTestVO", taskVo);
		} catch (Exception e) {
			LOGGER.error("任务管理查询异常", e);
		}
		return LIST_VIEW;
    }
    
    /**
     * 显示任务管理首页
     */
    @RequestMapping(value = "/showTaskPage")
    public String showTaskPage(){
    	return DETAIL_VIEW;
    }
    
    /**
     * 显示任务管理首页
     */
    @RequestMapping(value = "/showTaskCreatePage")
    public String showCreatePage(HttpServletRequest request){
    	String myclassifyid = request.getParameter("myclassifyid");
    	String sptmpid = request.getParameter("sptmpid");
    	System.out.println("我的自定义分类为：" + myclassifyid + "|我的模板为：" + sptmpid);
    	
    	return CREATE_VIEW;
    }
    
    /**
     * 显示任务管理首页
     */
    @RequestMapping(value = "/showTaskAddPage")
    public String showAddTaskPage(HttpServletRequest request){
    	String myclassifyid = request.getParameter("myclassifyid");
    	String sptmpid = request.getParameter("sptmpid");
    	System.out.println("我的自定义分类为：" + myclassifyid + "|我的模板为：" + sptmpid);
    	String staskcfgtemp = "";
    	try{
    		//通过模板获取参数配置
    		staskcfgtemp = taskcfgtempService.getTaskcfgtempInfo();
    		System.out.println(staskcfgtemp);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	
    	
    	
    	
    	return ADD_VIEW;
    }
    
    
    /**
     * 根据条件检索任务
     */
    @RequestMapping(value = "/schTaskInfo")
    public void schTaskInfo(HttpServletRequest request,HttpServletResponse response,SchTaskVO schTaskVo){
    	response.setContentType("application/json;charset=utf-8");
    	int icount = 0;
		PrintWriter out = null;
		
    	List<SchTaskVO> listTaskVo = null;
    	Integer itaskCount = null;
    	Integer itaskCurrentPage = null;
    	Integer itaskTotalPage = null;
    	
    	listTaskVo = spTaskService.searchSpTask(schTaskVo);
    	//命中数
    	itaskCount = spTaskService.searchSpTaskCount(schTaskVo);
    	schTaskVo.getPageHandle().setTotalResult(itaskCount);
    	//当前页
    	itaskCurrentPage = schTaskVo.getPageHandle().getCurrentPage();
    	//总页数
    	itaskTotalPage = schTaskVo.getPageHandle().getTotalPage();
    	
    	JSONObject jsonobjTaskVo = new JSONObject();
    	jsonobjTaskVo.put("totalResult", itaskCount);
    	jsonobjTaskVo.put("currentPage", itaskCurrentPage);
    	jsonobjTaskVo.put("totalPage", itaskTotalPage);
    	jsonobjTaskVo.put("tasklist", listTaskVo);
    	
    	try{
			out = response.getWriter();
			out.write(jsonobjTaskVo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
}