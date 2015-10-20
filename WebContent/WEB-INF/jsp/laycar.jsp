<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>豆豆车载系统管理平台</title>   
</head>   
    <frameset name="body_frame" cols="165,*" frameborder="no" border="0" framespacing="0">
        <frame name="side" src="<c:url value='/system/car.htm'/>" scrolling="auto">
        <frame id="show" name="show" src="<c:url value='/system/show.htm'/>" scrolling="auto">
    </frameset>
</html>