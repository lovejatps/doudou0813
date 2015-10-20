<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>豆豆车载系统管理平台</title>
</head>   
    <frameset id="usr_body_frame01" name="body_frame" cols="165,*" frameborder="no" border="0" framespacing="0">
        <frame id="user_side01" name="side" src="<c:url value='/system/user.htm'/>" scrolling="auto">
        <frame id="user_show01" name="show" src="<c:url value='/system/show.htm'/>" scrolling="auto">
    </frameset>
</html>