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
<div class="g-side">
    <ul class="sideNav clear">
        <li class="side-li">
            <a class="side-a" href="#"><i class="icons t10"></i>租赁型</a>
            <ul class="side-ul clear">
                <li><a href="<c:url value='/buzregist/list.htm'/>" target="show">车辆列表</a></li>
                <li><a href="<c:url value='/buzregist/toOneCall.htm'/>" target="show">服务电话</a></li>
            </ul>
        </li>
    </ul>
</div>
</body>
</html>