<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
<meta charset="UTF-8">
<title>豆豆车载系统管理平台</title>
<link rel="shortcut icon" href="<c:url value='/images/icon/zd_favicon2.ico'/>" type="image/vnd.microsoft.icon"> 
<link rel="icon" href="<c:url value='/images/icon/zd_favicon2.ico'/>" type="image/vnd.microsoft.icon">
<link rel="stylesheet" href="<c:url value='/css/layout.css'/>"/>
<link rel="stylesheet" href="<c:url value='/layer/skin/layer.css'/>" >
<script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/layer/layer.min.js'/>"></script>
<script type="text/javascript">
		function exit(){
			$.layer({
			    shade: [0],
			    area: ['auto','auto'],
			    dialog: {
			        msg: '是否确定退出？',
			        btns: 2,                    
			        type: 4,
			        btn: ['确定','取消'],
			        yes: function(){
			        	window.location.href = "<c:url value='/system/exit.htm'/>";
			        }, no: function(){
			            bool = false;
			        }
			    }
			});
		}
</script>
</head>

<frameset rows="140,*,36" frameborder="no" border="0" framespacing="0">
    <frame name="top" src="<c:url value='/system/top.htm'/>" scrolling="no" id="top01">
    <frame name="home" src="<c:url value='/system/show.htm'/>" scrolling="no" id="home01">
    <frame name="bottom" src="<c:url value='/system/bottom.htm'/>" scrolling="no" id="bottom01">
</frameset>
</html>