package com.bxt.sptask.user.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.user.dao.LoginDao;
import com.bxt.sptask.user.vo.UserVO;

@Repository
@SuppressWarnings("deprecation")
public class LoginDaoImpl implements LoginDao{
	private static final String	 SQL_NAMESPACE_	= "com.bxt.sptask.user.vo.";

	@Autowired
	private SqlMapClientTemplate	sqlMapClientTemplate;
	
	
	@Override
	@SuppressWarnings("unchecked")
	public UserVO getUserLoginInfo(UserVO user) {
		List<UserVO> userList = sqlMapClientTemplate.queryForList(SQL_NAMESPACE_+ "searchUsers",user);
		if(userList!=null&&userList.size()!=0){
			user=userList.get(0);
			return user;
		}
		return null;
	}
}
