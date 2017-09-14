package com.bxt.sptask.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.bxt.sptask.dao.TaskInitDao;
import com.bxt.sptask.dao.impl.TaskInitDaoImpl;
import com.bxt.sptask.service.TaskInitService;
import com.bxt.sptask.staichm.StaticMapParams;
import com.bxt.sptask.taskhandle.vo.TaskVo;

import util.TaskUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TaskInitServiceImpl implements TaskInitService {
	
	//public static HashMap<String,HashMap<Integer ,String>> hmTaskIdAndParams = new HashMap<String,HashMap<Integer ,String>>();
	
	@Autowired
	TaskInitDao taskDao;
	
	/**
	 * 工程启动时初始化任务ID到缓存
	 */
	@Override
	public void initTaskHashmap() {
		//初始化任务
//		TaskInitDao taskDao = new TaskInitDaoImpl();
		//获取任务模板,使用java代码时使用
//		List<SpiderconfigVO> listTmplate = taskDao.getTaskTemplateConfig(wact);
//		if(listTmplate != null){
//			for(SpiderconfigVO spvo:listTmplate){
//				StaticMapParams.hmTemplate.put(spvo.getId(), JSONObject.fromObject(spvo).toString());
//			}
//		}
		List<TaskVo> taskgroup = taskDao.getTaskGroupInfo();
		int xh = 0;
		String spcfdmac = "",taskclassify = "",stasktype = "",stask = "";
		Integer itasktype = null,itask = null;
		String staskKey = "";
		List<TaskVo> taskEveryGroup = null;
		if(taskgroup != null){
			for(TaskVo taskvo:taskgroup){
				taskEveryGroup = taskDao.getEveryGroupTaskID(taskvo);
				if(taskEveryGroup != null){
					//取任务ID
					List<String> listTaskID = new ArrayList<String>();
					for(TaskVo taskevery:taskEveryGroup){
						listTaskID.add(taskevery.getId());
					}
					//取任务ID和taskparams内容
//					HashMap<Integer,String> hmTidAndParam = new HashMap<Integer,String>();
//					for(TaskVo taskevery:taskEveryGroup){
//						hmTidAndParam.put(taskevery.getId(), taskevery.getTaskparams());
//					}
					itasktype = taskvo.getTasktype();
					if(itasktype != null){
						stasktype = itasktype.toString();
					}else{
						stasktype = "null";
					}
					spcfdmac = taskvo.getSpcfdmac();
					taskclassify = taskvo.getTaskclassify();
					itask = taskvo.getTask();
					
					if(itask != null){
						stask = itask.toString();
					}else{
						stask = "null";
					}
					staskKey = TaskUtil.getTaskKey(stasktype,spcfdmac,taskclassify,stask);
					//hmTaskIdAndParams.put(staskKey, hmTidAndParam);
					StaticMapParams.hmTaskSchID.put(staskKey, listTaskID);
					System.out.println("任务Key:"+staskKey);
					staskKey = "";
				}
				taskEveryGroup = null;
				System.out.println("任务类型：" + taskvo.getTask());
			}
		}
		System.out.println("任务初始化完成！");
	}
	
	public TaskVo handleTaskTemp(TaskVo taskevery){
		return null;
	}

	/**
	 * 读取任务ID到缓存中
	 */
	@Override
	public void waitTaskKeyHandle() {
		//应该先判断StaticMapParams.breadKeyflag是否为false，是则执行
		synchronized(this){
			if(!StaticMapParams.breadKeyflag){
				try{
					//读取任务ID信息HashMap中
					TaskVo  taskvo = new TaskVo();
					//TaskInitDao taskDao = new TaskInitDaoImpl();
					if(StaticMapParams.listWaitHandleKey.size() > 0){
						for(String sTaskKey : StaticMapParams.listWaitHandleKey){
							taskvo = TaskUtil.taskKeyToTaskVo(sTaskKey,"&&");
							List<String> listTaskID = new ArrayList<String>();
							List<TaskVo> taskEveryGroup = taskDao.getEveryGroupTaskID(taskvo);
							for(TaskVo taskevery:taskEveryGroup){
								listTaskID.add(taskevery.getId());
							}
							synchronized(StaticMapParams.hmTaskSchID){
								StaticMapParams.hmTaskSchID.put(sTaskKey, listTaskID); //需要同步，添加任务到缓存中
							}
							synchronized(StaticMapParams.hmTaskWaitKey){
								StaticMapParams.hmTaskWaitKey.remove(sTaskKey);//需要同步，从任务等待处理的Key中删除该任务Key值。
								StaticMapParams.hmTempTaskWaitKey.remove(sTaskKey);//需要同步
							}
							System.out.println("任务Key:"+sTaskKey);
						}
						StaticMapParams.listWaitHandleKey.clear();
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					StaticMapParams.breadKeyflag = true;
				}
			}
		}
	}

	/**
	 * 从等待处理的hashmap中读取任务key到当前处理任务的list中，breadKeyflag中为真是读取。
	 */
	@Override
	public void waitTaskKeyRead() {
		//TaskAgentServiceImpl.hmTaskWaitKey.put("sss", "2");
			synchronized(this){
				try{
					if(StaticMapParams.breadKeyflag){
						if(StaticMapParams.hmTaskWaitKey.size()>0){
							for(Iterator iter = StaticMapParams.hmTaskWaitKey.entrySet().iterator();iter.hasNext();){  
							    Map.Entry element = (Map.Entry)iter.next();  
							    Object strKey = element.getKey();
							    synchronized(StaticMapParams.listWaitHandleKey){
							    	try{
							    		StaticMapParams.listWaitHandleKey.add(strKey.toString());
							    	}catch(Exception e){
							    		e.printStackTrace();
							    	}
							    }
							} 
							if(StaticMapParams.listWaitHandleKey.size()>0){
								StaticMapParams.breadKeyflag = false;
							}else{
								StaticMapParams.breadKeyflag = true;
							}
						}
					}
				}catch(Exception e){
					//StaticMapParams.breadKeyflag = true;
					//StaticMapParams.listWaitHandleKey.clear();//需不需要清除再进行判断
					e.printStackTrace();
				}
			}

	}

	@Override
	public void handleOutTimeTask() {
		//TaskInitDao taskDao = new TaskInitDaoImpl();
		taskDao.handleOutTimeTask();
		
	}
	

}
