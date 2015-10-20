/**
 * @description doudou com.uniits.doudou.service.impl
 */
package com.uniits.doudou.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
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
import com.uniits.doudou.model.User;
import com.uniits.doudou.service.RightsService;
import com.uniits.doudou.service.UpgradeService;
import com.uniits.doudou.service.UserService;
import com.uniits.doudou.vo.UserListVo;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午6:24:45
 * @description
 */
@IocBean(name = "userService", args = {"refer:dao"})
public class UserServiceImpl extends IdEntityService<User> implements
        UserService {
	
	@Inject
	private RightsService rigistService ; 

    public UserServiceImpl() {
        super();
    }

    public UserServiceImpl(Dao dao, Class<User> entityType) {
        super(dao, entityType);
    }

    public UserServiceImpl(Dao dao) {
        super(dao);
    }

    @Override
    public void addUser(User user) {
        dao().insert(user);
    }

    @Override
    public User fetchUser(long id) {
        return fetch(id);
    }

    @Override
    public User fetchUser(String username) {
        return dao().fetch(User.class, Cnd.where("loginName", "=", username).and("status","=","0"));
    }

    @Override
    public User fetchUser(String username, String pwd) {
        return dao().fetch(User.class, Cnd.where("loginName", "=", username).and("pwd","=",pwd).and("status","=","0"));
    }

    @Override
    public void updateUser(User user) {
        dao().update(user);
    }

    @Override
    public void deleteUser(long id) {
        delete(id);
    }

    @Override
    public List<User> find(NutMap map) {
        Pager pager = null;
        Cnd cnd = Cnd.where("1", "=", "");
        //TODO
        List<User> userList = dao().query(User.class, cnd, pager);
        return userList;
    }
    
    private Map<String, String> getProduceName(){
    	Map<String, String> map = new HashMap<String, String>();
    	
    	List<Producecar> list = rigistService.findAll();
    	for(Producecar p :list){
    		map.put(""+p.getId(), p.getName());
    	}  	
    	return map ;
    	
    }
    
    
    public QueryResult findPage(int pageNo,int pageSize,NutMap map) {
        Cnd cnd = Cnd.where("1","=","1");
        Map<String, String> prods = getProduceName();
        if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("loginName"))) {
				cnd.and("loginName","LIKE","%" + map.getString("loginName") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("roleId"))) {
				cnd.and("roleId","=", map.getString("roleId"));
			}
			
		}
        cnd.desc("roleId");
        
        Pager pager = dao().createPager(pageNo, pageSize);
        LinkedList<User> list = (LinkedList<User>) dao().query(User.class, cnd, pager);
        
        List<UserListVo> voList = null;
        if(!Lang.isEmpty(list)) {
        	voList = new ArrayList<UserListVo>();
        	int i=(pageNo-1)*pageSize;
			for (User u : list) {
				i++;
				voList.add(new UserListVo(u.getId(), u.getLoginName(), ConstantRole.roleMap.get(u.getRoleId()), 
						u.getCreateDate(), u.getLastLoginDate(), (long)i,prods.get("" + u.getProduceid())));
			}
        }else{
        	pager.setPageNumber(0);
        }
        pager.setRecordCount(dao().count(User.class,cnd));
        
        return new QueryResult(voList, pager);
    }

}
