package com.uniits.doudou.service;

import java.util.List;

import org.nutz.dao.QueryResult;
import org.nutz.lang.util.NutMap;

import com.uniits.doudou.model.UserLog;

public interface LoginUserLogService {
	
	public void addUserLog(UserLog userLog);
	
	public UserLog fetchUserLog(Long id);
	
	public List<UserLog> findUserLogList();
	
	public QueryResult findPageList(int pageNo,int pageSize,NutMap map);

}
