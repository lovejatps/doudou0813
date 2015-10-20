/**
 * @description doudou com.uniits.doudou.action
 */
package com.uniits.doudou.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.mvc.view.ServerRedirectView;

import com.uniits.doudou.action.base.BaseAction;
import com.uniits.doudou.constants.ConstantRole;
import com.uniits.doudou.model.Producecar;
import com.uniits.doudou.model.User;
import com.uniits.doudou.model.UserLog;
import com.uniits.doudou.model.Vin;
import com.uniits.doudou.service.LoginUserLogService;
import com.uniits.doudou.service.RegistService;
import com.uniits.doudou.service.RightsService;
import com.uniits.doudou.service.UserService;

/**
 * @author liuchengwei
 * @since 2015-3-16 下午1:57:04
 * @description
 */
@IocBean
@At("/buzuser")
public class UserAction extends BaseAction{

    
    @Inject
    private RegistService registService;
    
    @Inject
    private UserService userService;
    
    @Inject
    private LoginUserLogService loginUserLogService;
    
	@Inject
	private RightsService rigistService ; 
    
    @At("/login")
    @Ok("json")
    @POST
    @Filters
    public NutMap login(@Param("VIN")String username, @Param("ID")String pwd, HttpSession session) {
        return registService.addRegist(username,pwd);
    }
    
//    @At("/showPage")
//    @Ok("json")
//    public QueryResult findPage(@Param("pageNo")int pageNo,@Param("pageSize")int pageSize,@Param("::map.")NutMap map) {
//        QueryResult queryResult = userService.findPage(pageNo, pageSize, map);
//        return queryResult;
//    }
    
    @At("/checkLogin")
    @Ok("json")
    @Filters
    public String checkLogin(@Param("username")String username, @Param("pwd")String pwd){
    	String errMsg = "";
    	User user = userService.fetchUser(username);
        if(user != null) {
        	String pas = user.getPwd();
        	if(pas.equals(pwd)) {
        		errMsg="1";
        	} else {
            	errMsg = "登录密码错误";
            }
        	
        }else { 
        	errMsg = "用户名不存在";
        }
    	
    	return errMsg;
    }
    
    @At("/login2")
    @Ok(">>:/system/main.htm")
    @POST
    @Filters
    public String login2(@Param("username")String username, @Param("pwd")String pwd,HttpSession session,HttpServletRequest req,HttpServletResponse res) throws Exception {
    	String errMsg = "";
    	User user = userService.fetchUser(username);
        if(user != null) {
            String pas = user.getPwd();
            if(pas.equals(pwd)) {
                session.setAttribute("online", 1);
                session.setAttribute("username", username);
                session.setAttribute("userId", user.getId());
                session.setAttribute("roleId", user.getRoleId());
                session.setAttribute("produceid", user.getProduceid());
                session.setAttribute("roleName", ConstantRole.roleMap.get(user.getRoleId()));
                user.setLastLoginDate(Times.sDT(new Date()));
                userService.updateUser(user);
                UserLog userLog = new UserLog();
                userLog.setUserId(user.getId());
                userLog.setUserName(user.getLoginName());
                userLog.setRoleId(user.getRoleId());
                userLog.setLoginDate(Times.sDT(new Date()));
                userLog.setCreateDate(user.getCreateDate());
                loginUserLogService.addUserLog(userLog);
                session.removeAttribute("errMsg");
            } else {
//            	session.setAttribute("errMsg","密码错误");
            	errMsg = "密码错误";
//            	throw new Exception(username +":密码错误");
            }
        }else { 
//        	session.setAttribute("errMsg","用户不存在");
        	errMsg = "用户不存在";
//        	throw new Exception(username + "用户不存在");
        }
        return errMsg;
    }
   
    
    @At("/add")
	@Ok("jsp:jsp/user/us/add")
	public List<Producecar> add() {
    	return rigistService.findAll();

	}

	@At("/doInputUser")
	@Ok("jsp:jsp/user/us/tip")
	public String doInputUser(@Param("::user.") User v) {
		v.setCreateDate(Times.sD(new Date()));
		userService.addUser(v);
		return "addUser";
	}
	
	@At("/deleteUser")
	@Ok("json")
	public String deleteUser(@Param("::user.") User v, HttpSession session){
		Long conUser = Long.parseLong(session.getAttribute("userId").toString());
		if(conUser==v.getId()){
			return "2";
		}else{
			userService.deleteUser(v.getId());
		}
		return "1";
	}
	
	@At("/toUpdateUser")
	@Ok("jsp:jsp/user/us/updateUser")
	public User toUpdateUser(@Param("::user.") User user){
		User obj = userService.fetchUser(user.getId());
		List<Producecar> list =  rigistService.findAll();
		obj.setProds(list);
		return obj ;
	}
	
	@At("/doUpdateUser")
	@Ok("jsp:jsp/user/us/tip")
	public String doUpdateUser(@Param("::user.") User user){
		
		User u = userService.fetchUser(user.getId());
		u.setLoginName(user.getLoginName());
		u.setRoleId(user.getRoleId());
		
		userService.updateUser(u);
		
		return "updateUser";
	}

	@At("/list")
	@Ok("jsp:jsp/user/us/list")
	public User list(HttpSession session) {
		return userService.fetchUser(Long.parseLong(session.getAttribute("userId").toString()));
	}

	@At("/showPage")
	@Ok("json")
	public QueryResult findPage(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
		QueryResult queryResult = userService.findPage(pageNo, pageSize, map);
		return queryResult;
	}
	   
    @At("/validatorLoginName")
    @Ok("json")
    public int validatorLoginName(@Param("user.loginName") String v) {
        User user = userService.fetchUser(v);
        if(!Lang.isEmpty(user)) {
            return 1;
        }
        return 0;
    }
    
    @At("/toUpdatePwd")
	@Ok("jsp:jsp/user/us/updatePwd")
    public User toUpdatePwd(@Param("::user.") User user){
    	return userService.fetchUser(user.getId());
    }
    
    @At("/doUpdatePwd")
	@Ok("jsp:jsp/user/us/tip")
    public String doUpdatePwd(@Param("::user.") User user){
    	User u = userService.fetchUser(user.getId());
    	u.setPwd(user.getPwd());
    	userService.updateUser(u);
    	
    	return "updateUser";
    }
    
    @At("/validatorUpdateLoginName")
    @Ok("json")
    public int validatorUpdateLoginName(@Param("user.loginName") String loginName, @Param("userName") String userName) {
        User user = userService.fetchUser(loginName);
        if(!Lang.isEmpty(user)) {
        	if(user.getLoginName().equals(userName)){
        		return 0;
        	}else{
        		return 1;
        	}
        }
        return 0;
    }
    
    @At("/toUserLog")
	@Ok("jsp:jsp/user/log/logList")
    public void toUserLog(){
    	
    }
    
    @At("/showUserLog")
    @Ok("json")
    public QueryResult showUserLog(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map){
    	return loginUserLogService.findPageList(pageNo, pageSize, map);
    }
    
}
