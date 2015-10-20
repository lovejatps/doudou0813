/**
 * 
 */
package com.uniits.doudou.action;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * @author 成卫
 *
 */
@IocBean
@At("/system")
public class SystemAction {
	
	@At("/main")
    @Ok("jsp:jsp/main")
    public String main(){
    	return "1";
    }
	
	@At("/top")
    @Ok("jsp:jsp/top")
    public void top(){
    	
    }
	
	@At("/show")
    @Ok("jsp:jsp/show")
    public void show(){
    	
    }
	
	@At("/bottom")
    @Ok("jsp:jsp/bottom")
    public void bottom(){
    	
    }

	@At("/sbm")
    @Ok("jsp:jsp/sbm/sbm")
	public void sbm() {
		
	}
	
	@At("/car")
    @Ok("jsp:jsp/car/car")
	public void car() {
		
	}
	
	@At("/user")
    @Ok("jsp:jsp/user/user")
	public void user() {
		
	}
	
	@At("/upgrade")
    @Ok("jsp:jsp/upgrade/upgrade")
	public void upgrade() {
		
	}
	
	@At("/showCar")
    @Ok("jsp:jsp/showcar/show")
	public void showCar(){
		
	}
	
	@At("/exit")
    @Ok(">>:/index.jsp")
    public void exit(HttpSession session){
		session.removeAttribute("online");
	}
	
	@At("/side")
    @Ok("jsp:jsp/side")
	public void side(){
		
	}
	
	@At("/laysbm")
    @Ok("jsp:jsp/laysbm")
	public void laysbm(){
		
	}
	
	@At("/laycar")
    @Ok("jsp:jsp/laycar")
	public void laycar(){
		
	}
	
	@At("/layupgrade")
    @Ok("jsp:jsp/layupgrade")
	public void layupgrade(){
		
	}
	
	@At("/layuser")
    @Ok("jsp:jsp/layuser")
	public void layuser(){
		
	}
		
}
