package com.uniits.doudou.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.service.IdEntityService;

import com.uniits.doudou.constants.ConstantRole;
import com.uniits.doudou.model.Producecar;
import com.uniits.doudou.model.UserLog;
import com.uniits.doudou.service.LoginUserLogService;
import com.uniits.doudou.service.RightsService;
import com.uniits.doudou.vo.UserListVo;

@IocBean(name = "loginUserLogService", args = {"refer:dao"})
public class LoginUserLogServiceImpl extends IdEntityService<UserLog> implements LoginUserLogService {

	@Inject
	private RightsService rigistService ; 
	
	public LoginUserLogServiceImpl() {
        super();
    }

    public LoginUserLogServiceImpl(Dao dao, Class<UserLog> entityType) {
        super(dao, entityType);
    }

    public LoginUserLogServiceImpl(Dao dao) {
        super(dao);
    }
    
    public void addUserLog(UserLog userLog){
    	dao().insert(userLog);
    }
    
    public UserLog fetchUserLog(Long id){
    	return dao().fetch(UserLog.class, id);
    }
    
    public List<UserLog> findUserLogList(){
    	Cnd cnd = Cnd.where("1", "=", "");
    	List<UserLog> userLogList = dao().query(UserLog.class, cnd);
    	return userLogList;
    }
    
    private Map<String, String> getProduceName(){
    	Map<String, String> map = new HashMap<String, String>();
    	
    	List<Producecar> list = rigistService.findAll();
    	for(Producecar p :list){
    		map.put(""+p.getId(), p.getName());
    	}  	
    	return map ;
    	
    }
    
    public QueryResult findPageList(int pageNo,int pageSize,NutMap map){

        Cnd cnd = Cnd.where("1","=","1");
  //      Map<String, String> prods = getProduceName();
        
        if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("loginName"))) {
				cnd.and("userName","LIKE","%" + map.getString("loginName") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("startDate"))) {
				cnd.and("left(login_date,20)",">=", map.getString("startDate"));
			}
			
			if(!Lang.isEmpty(map.getString("endDate"))) {
				cnd.and("left(login_date,20)","<=", map.getString("endDate"));
			}
			
		}
        cnd.desc("loginDate");
        
        Pager pager = dao().createPager(pageNo, pageSize);
        List<UserLog> list = dao().query(UserLog.class, cnd, pager);
        
        List<UserListVo> voList = null;
        if(!Lang.isEmpty(list)) {
        	voList = new ArrayList<UserListVo>();
        	int i=(pageNo-1)*pageSize;
			for (UserLog u : list) {
				i++;
				voList.add(new UserListVo(u.getId(), u.getUserName(), ConstantRole.roleMap.get(u.getRoleId()), 
						u.getCreateDate(), u.getLoginDate(), (long)i));
			}
        }else{
        	pager.setPageNumber(0);
        }
        pager.setRecordCount(dao().count(UserLog.class,cnd));
        
        return new QueryResult(voList, pager);
    
    }
	
}
