/**
 * @description doudou com.uniits.doudou.service
 */
package com.uniits.doudou.service;

import java.util.List;

import org.nutz.dao.QueryResult;
import org.nutz.lang.util.NutMap;

import com.uniits.doudou.model.User;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午6:19:42
 * @description 
 */
public interface UserService {

    public void addUser(User user);
    
    public User fetchUser(long id);
    
    public User fetchUser(String username);
    
    public User fetchUser(String username,String pwd);
    
    public void updateUser(User user);
    
    public void deleteUser(long id);
    
    public List<User> find(NutMap map);
    
    public QueryResult findPage(int pageNo,int pageSize,NutMap map);
    
}
