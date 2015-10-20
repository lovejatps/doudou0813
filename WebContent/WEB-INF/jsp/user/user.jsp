<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html class="html-left" id="user_html-left_001">
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
<div class="g-side">
    <ul class="sideNav clear">
        <li class="side-li">
            <a class="side-a" href="#" id="user_side_a_ID001"><i class="icons t11"></i>用户管理</a>
            <ul class="side-ul clear">
                <li><a id="userlist" href="<c:url value='/buzuser/list.htm'/>" target="show">用户列表</a></li>
                <li><a id="useradd" href="<c:url value='/buzuser/add.htm'/>" target="show">用户添加</a></li>
                <li><a href="<c:url value='/buzuser/toUserLog.htm'/>" target="show">登录日志</a></li>
            </ul>
            <a class="side-a" href="#" id="user_side_a_ID002"><i class="icons t40"></i>厂家管理</a>
            <ul class="side-ul clear">
                <li><a id="rightlist" href="<c:url value='/rights/listpage.htm'/>" target="show">厂家列表</a></li>
                <li><a id="rightadd" href="<c:url value='/rights/addpage.htm'/>" target="show">厂家添加</a></li>
            </ul>
        </li>
        
    </ul>
</div>
</body>
</html>