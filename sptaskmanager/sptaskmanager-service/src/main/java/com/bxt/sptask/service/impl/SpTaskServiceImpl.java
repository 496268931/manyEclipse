package com.bxt.sptask.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bxt.sptask.dao.SpTaskDao;
import com.bxt.sptask.service.SpTaskService;
import com.bxt.sptask.sptmpconfig.vo.TaskcfgtempVO;
import com.bxt.sptask.taskhandle.vo.SchTaskVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.utils.IDUtils;

import net.sf.json.JSONObject;
import util.HandleDefParamUtil;

@Service
@Transactional(rollbackFor={Throwable.class})
public class SpTaskServiceImpl implements SpTaskService{
  private static final long serialVersionUID = 723262909707371969L;
  private static Logger LOGGER = LoggerFactory.getLogger(SpTaskServiceImpl.class);

  @Autowired
  private SpTaskDao spTaskDao;

  public void deleteTaskTest(Integer id)
  {
  }

  public void deleteTaskTest(TaskVo taskVo)
  {
  }

  public Integer insertTaskTest(TaskVo taskVo)
  {
    return null;
  }

  public TaskVo searchTaskTestById(String id)
  {
    return this.spTaskDao.searchById(id);
  }

  public List<SchTaskVO> searchSpTask(SchTaskVO schTaskVo)
  {
    return this.spTaskDao.searchSpTask(schTaskVo);
  }

  public List<TaskVo> searchTaskTestList(TaskVo taskVo)
  {
    return null;
  }

  public Integer searchTaskTestCount(TaskVo taskVo)
  {
    return null;
  }

  public void updateTaskTest(TaskVo taskVo)
  {
  }

  public Integer searchSpTaskCount(SchTaskVO schTaskVo)
  {
    Integer itaskCount = this.spTaskDao.searchSpTaskCount(schTaskVo);

    return itaskCount;
  }

  public HashMap<String,String> insertTaskParam(String taskparam,String taskid,String childtaskid){
	  HashMap<String,String> hm_taskid = new HashMap<String,String>();
	  try{
		  String staskParams = "";
		  HandleDefParamUtil hdpu = new HandleDefParamUtil();
		  if ((taskid != null) && (!taskid.equals(""))){
	    	  //根据taskid检索任务信息，然后要判断childid在任务参数中是否存在，存在则修改，不存在则新建该子任务 
			  TaskVo taskvo = spTaskDao.searchById(taskid);
			  staskParams = taskvo.getTaskparams();
			  JSONObject  taskParamJson = JSONObject.fromObject(staskParams);
			  String task_paramdef_json = hdpu.analyRootTreeParam(taskparam);
	          if (childtaskid != null && !childtaskid.equals("root") && !childtaskid.equals("") && !childtaskid.equals("0")){
	        	  //修改childtaskid任务
	    		  taskParamJson.put(childtaskid, task_paramdef_json);
	          }else{
	        	  //修改root任务
	        	  taskParamJson.put("root", task_paramdef_json);
	          }
	          taskvo.setTaskparams(taskParamJson.toString());
	          spTaskDao.updateTaskInfo(taskvo);
	         // spTaskDao.insertTaskInfo(taskvo);
	      }else{
	    	//taskid为null时，新增任务信息到数据库中
	        //taskid = this.spTaskService.insertTaskParam(task_def,taskid,childtaskid);
	    	JSONObject task_json = new JSONObject();
		    taskid = IDUtils.genItemId();
		    TaskVo taskvo = new TaskVo();
		    taskvo.setId(taskid);
		    taskvo.setTasktype(Integer.valueOf(2));
		    taskvo.setTask(Integer.valueOf(20));
		    taskvo.setTasklevel(Integer.valueOf(1));
		    taskvo.setLocal(Integer.valueOf(0));
		    taskvo.setRemote(Integer.valueOf(1));
		    taskvo.setStarttime(Integer.valueOf((int)(System.currentTimeMillis() / 1000L)));
		    taskvo.setActivatetime(Integer.valueOf(0));
		    taskvo.setDatastatus(Integer.valueOf(60));
		    taskvo.setRemarks("任务:[446000]###的子任务 ###Index:[81] ###处理第[81]条-第[81]条###");
		    taskvo.setTaskstatus(Integer.valueOf(0));
		    taskvo.setDatastatus(Integer.valueOf(0));
		    taskvo.setUserid(Integer.valueOf(40));
		  
		    String task_paramdef_json = hdpu.analyRootTreeParam(taskparam);
		    task_json.put("root", task_paramdef_json);
		    taskvo.setTaskparams(task_json.toString());
	    	spTaskDao.insertTaskInfo(taskvo);
	      }
	  }catch(Exception e){
		  e.printStackTrace();
	  }
    hm_taskid.put("childtaskid", childtaskid);
    hm_taskid.put("taskid", taskid);
    return hm_taskid;
  }

  public String saveChildTaskNode(String taskid, String childtaskid)
  {
    String task_params = "",skey = "",child_key = "",skey_size = ""; 
    String strnode = "",childtask_params = "",skey_json = "",parid = "";
    int ikey = 0;
    List<TaskcfgtempVO> dvf_tasktemp_list = null;
    JSONObject pc_task_json = new JSONObject();
    TaskVo taskvo = spTaskDao.searchById(taskid);
    if (taskvo != null) {
      task_params = taskvo.getTaskparams();
      if ((task_params != null) && (!task_params.trim().equals(""))) {
        JSONObject json_obj = JSONObject.fromObject(task_params);
        if(json_obj != null && json_obj.size() > 0){
        	ikey = json_obj.size();
            skey_size = Integer.toString(ikey);
            if (ikey > 0 && !childtaskid.equals(skey_size)) {
              List<TaskcfgtempVO> cp_list = new ArrayList<TaskcfgtempVO>();
              for (int i = 0; i <= json_obj.size(); i++) {
                TaskcfgtempVO cp_tcgvo = new TaskcfgtempVO();
                if (i == 0) {
                  parid = "root";
                  cp_tcgvo.setNodeid("root");
                  cp_tcgvo.setParnodeid("0");
                  cp_tcgvo.setNodename("父任务");
                } else {
                  skey = Integer.toString(i);
                  cp_tcgvo.setNodeid(skey);
                  cp_tcgvo.setParnodeid(parid);
                  cp_tcgvo.setNodename("子任务" + i);
                  parid = skey;
                }
                cp_list.add(cp_tcgvo);
              }
              if(skey != null && !skey.equals("")){
            	  childtaskid = skey;
            	  pc_task_json.put("cp_task", cp_list); //任务父子关系树list
                  pc_task_json.put("taskid", taskid);   //任务树根id
                  pc_task_json.put("childtaskid", childtaskid); //任务树子ID
                  
                  HandleDefParamUtil hdpu = new HandleDefParamUtil();
                  ikey = ikey -1;
                  if (ikey == 0) {
                	  dvf_tasktemp_list = hdpu.analyJsonToTree(task_params, "root");
                    //childtask_params = json_obj.getString("root");
                  } else {
                     child_key = Integer.toString(ikey);
                     dvf_tasktemp_list = hdpu.analyJsonToTree(task_params, child_key);
                    //childtask_params = json_obj.getString(child_key);
                  }
                  pc_task_json.put("def_task", dvf_tasktemp_list); //子任务定义参数
                  
                  
                  //解析当前任务childtask_params，并生成子任务树
                  
                 
                  
                  pc_task_json.put("status", "1");
              	  pc_task_json.put("message", "创建子任务成功!"); //任务父子关系树list
              }else{
            	  //当前任务格式错误，请确认修改并存盘后再生成子任务
            	  pc_task_json.put("status", "-1");
                  pc_task_json.put("message", "请保存当前任务，然后再创建其子任务！！"); //任务父子关系树list
              }

            }else{
            	pc_task_json.put("status", "-1");
            	pc_task_json.put("message", "请保存当前任务，然后再创建其子任务！！"); //任务父子关系树list
              System.out.println("不可以创建子任务");
            }
        }else{
        	//当前任务格式错误，请确认修改并存盘后再生成子任务
      	    pc_task_json.put("status", "-1");
            pc_task_json.put("message", "父任务不存在，请保存当前任务，然后再创建该子任务！！"); //任务父子关系树list
        }
      } else {
    	  pc_task_json.put("status", "-1");
      	  pc_task_json.put("message", "请保存当前任务，然后再创建其子任务！！"); //任务父子关系树list
          System.out.println("不可以创建子任务");
      }
    } else {
    	pc_task_json.put("status", "-1");
    	pc_task_json.put("message", "请保存当前任务，然后再创建其子任务！！"); //任务父子关系树list
      System.out.println("不可以创建子任务");
    }
    System.out.println(taskvo.getTaskparams());
    return pc_task_json.toString();
  }

@Override
public void insertTaskParam(TaskVo taskvo) {
	
	String taskid  = taskvo.getId();
	if(taskid == null || taskid.equals("")){
		 taskid = IDUtils.genItemId();
		 taskvo.setId(taskid);
	}

	Integer llocal = taskvo.getLocal();
	if(llocal == null){
		taskvo.setLocal(0);
	}else{
		taskvo.setLocal(1);
	}
	Integer lremote = taskvo.getRemote();
	if(lremote == null){
		taskvo.setRemote(0);
	}else{
		taskvo.setRemote(1);
	}
	
	Integer lactivatetime = taskvo.getActivatetime();
	if(lactivatetime == null){
		taskvo.setActivatetime(0);
	}
	
	Integer lconflictdelay = taskvo.getConflictdelay();
	if(lconflictdelay == null){
		taskvo.setConflictdelay(60);
	}
	
	Integer lstartime = taskvo.getStarttime();
	if(lstartime == null){
		taskvo.setStarttime(0);
	}
	
	taskvo.setTaskstatus(0);
	taskvo.setDatastatus(0);
	
	
	spTaskDao.insertTaskInfo(taskvo);
}

}