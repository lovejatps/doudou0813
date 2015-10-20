/**
 * @description doudou com.uniits.doudou.filter
 */
package com.uniits.doudou.filter;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.mvc.view.UTF8JsonView;

/**
 * @author liuchengwei
 * @since 2015-3-21 下午12:00:58
 * @description
 */
@IocBean
public class LoginFilter implements ActionFilter {

    @Override
    public View match(ActionContext actionContext) {
        // 可用，直接加入HttpServletRequest参数，nutz能传值给它
        HttpServletRequest request = actionContext.getRequest();

        ServerRedirectView v1 = new ServerRedirectView("/index.jsp");

//        String go = request.getParameter("go");
        Integer online = (Integer) request.getSession().getAttribute("online");
        if (online ==null || online == 0)
            return v1;
//        if ("no".equals(go)) {
//            UTF8JsonView v = new org.nutz.mvc.view.UTF8JsonView(new JsonFormat());
//            v.setData("dd");// json 格式
//            return v;
//        }
        return null;
    }

}
