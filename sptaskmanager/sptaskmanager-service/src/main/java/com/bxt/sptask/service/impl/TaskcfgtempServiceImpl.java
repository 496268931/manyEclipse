package com.bxt.sptask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bxt.sptask.dao.TaskcfgtempDao;
import com.bxt.sptask.service.TaskcfgtempService;
import com.bxt.sptask.sptmpconfig.vo.TaskcfgtempVO;

import net.sf.json.JSONObject;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TaskcfgtempServiceImpl implements TaskcfgtempService {
	
	@Autowired
	private TaskcfgtempDao taskcfgtempDao;

	@Override
	public String getTaskcfgtempInfo() {
		String taskcfgrempstr = "";
		JSONObject jsonobjTaskcfgtempVO = new JSONObject();
		List<TaskcfgtempVO> taskcfgtemp_list = taskcfgtempDao.getTaskcfgtempInfo();
		
		if(taskcfgtemp_list != null && taskcfgtemp_list.size()> 0){
			jsonobjTaskcfgtempVO.put("taskcfgtemplist", taskcfgtemp_list);
			taskcfgrempstr = jsonobjTaskcfgtempVO.toString();
		}
		
		return taskcfgrempstr;
	}

}
