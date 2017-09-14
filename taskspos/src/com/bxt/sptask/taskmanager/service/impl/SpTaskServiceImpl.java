package com.bxt.sptask.taskmanager.service.impl;

import com.bxt.sptask.sptmpconfig.vo.TaskcfgtempVO;
import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.taskmanager.dao.SpTaskDao;
import com.bxt.sptask.taskmanager.service.SpTaskService;
import com.bxt.sptask.taskmanager.vo.SchTaskVO;
import com.bxt.sptask.utils.HandleDefParamUtil;
import com.bxt.sptask.utils.IDUtils;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public String insertTaskParam(String taskparam)
  {
    HashMap hm_taskid = new HashMap();
    JSONObject task_json = new JSONObject();
    String taskid = String.valueOf(IDUtils.genItemId());
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

    HandleDefParamUtil hdpu = new HandleDefParamUtil();
    String task_paramdef_json = hdpu.analyRootTreeParam(taskparam);

    task_json.put("root", task_paramdef_json);
    taskvo.setTaskparams(task_json.toString());

    hm_taskid.put("childtaskid", "");
    hm_taskid.put("taskid", taskid);
    return taskid;
  }

  public String saveChildTaskNode(String taskid, String childid)
  {
    String task_params = ""; String skey = ""; String child_key = ""; String strnode = "";
    int ikey = 0;
    JSONObject pc_task_json = new JSONObject();
    TaskVo taskvo = this.spTaskDao.searchById(taskid);
    if (taskvo != null) {
      task_params = taskvo.getTaskparams();
      if ((task_params != null) && (!task_params.trim().equals(""))) {
        JSONObject json_obj = JSONObject.fromObject(task_params);
        ikey = json_obj.size();
        if (ikey > 0) {
          ikey = ikey -1;
          String childtask_params;
          if (ikey == 0) {
            childtask_params = json_obj.getString("root");
          } else {
            child_key = Integer.toString(ikey);
            childtask_params = json_obj.getString(child_key);
          }

          String parid = taskid;
          List cp_list = new ArrayList();
          for (int i = 0; i < json_obj.size(); i++) {
            TaskcfgtempVO cp_tcgvo = new TaskcfgtempVO();
            if (i == 0) {
              cp_tcgvo.setNodeid(taskid);
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
          TaskcfgtempVO cp_tcgvo = new TaskcfgtempVO();
          cp_tcgvo.setNodeid(skey);
          cp_tcgvo.setParnodeid(parid);
          cp_tcgvo.setNodename("子任务" + skey);
          cp_list.add(cp_tcgvo);
          pc_task_json.put("cp_task", cp_list);

          /*for (int i = 0; i < json_obj.size(); i++)
            if (i == 0) {
              strnode = "root";
            } else {
              skey = Integer.toString(ikey);
              strnode = i;
            }*/
        }
        else
        {
          System.out.println("不可以创建子任务");
        }
      } else {
        System.out.println("不可以创建子任务");
      }
    } else {
      System.out.println("不可以创建子任务");
    }
    System.out.println(taskvo.getTaskparams());
    return pc_task_json.toString();
  }

}