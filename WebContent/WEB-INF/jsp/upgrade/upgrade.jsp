<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html class="html-left">
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>欢迎登录豆豆车载系统管理平台</title>
    <link rel="stylesheet" href="<c:url value='/css/layout.css'/>"/>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var side_a = $(".sideNav> li> a"),
                    side_ul = $(".sideNav li >.side-ul"),
                    side_ac = $(".side-ul>li>a");
            side_a.on("click", function (event) {
                event.preventDefault();
                if ($(this).attr("class").indexOf("hover") < 0) {
                    side_ul.slideUp("normal");
                    $(this).next().slideToggle("normal");
                    side_a.removeClass("hover");
                    $(this).addClass("hover");
                } else {
                    side_ul.slideUp("normal");
                    $(this).removeClass("hover");
                }
            });
            side_ac.on("click", function () {
                side_ac.removeClass("ed");
                $(this).addClass("ed");
            })
        });
    </script>
</head>
<body>
<div class="g-side"  id="testdiv">
    <ul class="sideNav clear">
    <c:if test="${roleId==-1}">
        <li class="side-li">
            <a class="side-a" href="#" id="side0001001"><i class="icons t1"></i>系统型号</a>
            <ul class="side-ul clear">
                <li><a id="syslist" href="<c:url value='/upgrade/sys/list.htm'/>" target="show">型号列表</a></li>
				<li><a id="sysadd" href="<c:url value='/upgrade/sys/add.htm'/>" target="show">型号添加</a></li>
            </ul>
        </li>     
        <li class="side-li">
            <a class="side-a" href="#" id="side0001002"><i class="icons t6"></i>固件型号</a>
            <ul class="side-ul clear">
                <li><a id="firmlist"  href="<c:url value='/upgrade/firm/listFirm.htm'/>" target="show">型号列表</a></li>
                <li><a id="firmadd" href="<c:url value='/upgrade/firm/addFirm.htm'/>" target="show">型号添加</a></li>
            </ul>
        </li>
      </c:if>
        <li class="side-li">
            <a class="side-a" href="#" id="side0001003"><i class="icons t2"></i>系统包管理</a>
            <ul class="side-ul clear">
                <li><a id="packlist" href="<c:url value='/upgrade/syspackage/listPackage.htm'/>" target="show">系统包列表</a></li>
                <c:if test="${roleId==-1}">
                	<li><a id="packadd" href="<c:url value='/upgrade/syspackage/addPackage.htm'/>" target="show">系统包上传</a></li>
                </c:if>
            </ul>
        </li>
        <li class="side-li">
            <a class="side-a" href="#" id="side0001004"><i class="icons t3"></i>动画管理</a>
            <ul class="side-ul clear">
                <li><a id="anilist" href="<c:url value='/upgrade/animation/listAnimation.htm'/>" target="show">动画列表</a></li>
                <c:if test="${roleId==-1}">
               		<li><a id="aniadd" href="<c:url value='/upgrade/animation/addAnimation.htm'/>" target="show">动画上传</a></li>
               	</c:if>
            </ul>
        </li>
        
        <li class="side-li">
            <a class="side-a" href="#" id="side0001005"><i class="icons t4"></i>固件管理</a>
            <ul class="side-ul clear">
                <li><a id="warelist" href="<c:url value='/upgrade/firmware/listFirmware.htm'/>" target="show">固件列表</a></li>
                <c:if test="${roleId==-1}">
               		 <li><a id="wareadd" href="<c:url value='/upgrade/firmware/addFirmware.htm'/>" target="show">固件上传</a></li>
               	</c:if>
            </ul>
        </li>
        <li class="side-li">
            <a class="side-a" href="#" id="side0001006"><i class="icons t5"></i>APP管理</a>
            <ul class="side-ul clear">
                <li><a id="applist" href="<c:url value='/upgrade/appPack/listApp.htm'/>" target="show">APP列表</a></li>
                <c:if test="${roleId==-1}">
                	<li><a id="appadd" href="<c:url value='/upgrade/appPack/addApp.htm'/>" target="show">APP上传</a></li>
                </c:if>
            </ul>
        </li>
        
        
    </ul>
</div>
</body>
</html>