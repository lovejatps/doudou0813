package com.uniits.doudou;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.uniits.doudou.filter.LoginFilter;

/**
 * 主模块
 *
 * @author steven
 *
 */
@Modules(scanPackage = true)/*@Modules - 声明应用的所有子模块*/
@IocBy(type = ComboIocProvider.class, args = {
	"*org.nutz.ioc.loader.json.JsonLoader", "ioc/", 
	"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.uniits.doudou"})/*@IocBy - 设置应用所采用的 Ioc 容器*/
@SetupBy(NutzSetup.class)/*@SetupBy - 应用启动以及关闭时的额外处理*/
@Filters({@By(type=LoginFilter.class)})
public class MainModule {

}
