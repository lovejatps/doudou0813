<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>欢迎登录豆豆车载系统管理平台</title>
    <link rel="stylesheet" href="<c:url value='/css/layout.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/layer/skin/layer.css'/>" >
	<script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/layer/layer.min.js'/>"></script>
    <!--[if IE 6]>
    	<script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
		function exit(){
			//alert(window.parent);
			//window.parent.exit();
			window.parent.exit();
		}
		
		function exit2(){
			if(confirm("是否确定退出？")){
				window.location.href = "<c:url value='/system/exit.htm'/>";
			}
		}
		
		function exit1(){
			$.layer({
			    shade: [0],
			    
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
<script type="text/javascript">
		var $preClick = {};
		$(document).ready(function() {
			$preClick = $(".menu-a sbm");
			$preClick.attr("class",".menu-a sbmed");
			$.each($(".menu-a"), function(i, n) {
				$(this).click(function() {
					if($preClick.attr("class") == $(this).attr("class")) {
						return ;
					}
					var curClz = $(this).attr("class");
	
					curClz = curClz + 'ed';
					var preClick = ($preClick.attr("class")+"").replace(/ed/, '');
	
					$preClick.attr("class", preClick);
	
					$(this).attr("class", curClz);
					
					$preClick = $(this);
	
				});
			});
		});
</script>
</head>
<body>
<div class="g-head">
    <div class="topBar">
        <ul class="topLink">
            <li><a href="#">${username }</a></li>
            <li class="line">|</li>
            <li><a href="#">${roleName }</a></li>
            <li class="line">|</li>
            <li><a onclick="exit2();" href="#">退出</a></li>
        </ul>
        <!-- /topLink -->
    </div>
    <!-- /topBar -->
    <div class="headBar clear">
        <div class="h-logo">
            <a href="#"></a>
        </div>
        <!-- h-logo end -->
        <ul class="h-nav clear">
        	<c:if test="${roleId==-1}">
            	<li id="userli001"><a href="<c:url value='/system/layuser.htm'/>" class="menu-a yhgl" target="home" id="user001">用户管理</a></li>
        	</c:if>
            <li><a href="<c:url value='/system/layupgrade.htm'/>" class="menu-a sjgl" target="home" id="sjgl001">升级管理</a></li>
            <c:if test="${roleId==-1}">
	            <li><a href="<c:url value='/system/laycar.htm'/>" class="menu-a clgl" target="home" id="clgl001">车辆管理</a></li>
	            <li><a href="<c:url value='/system/laysbm.htm'/>" class="menu-a sbm" target="home" id="sbm001">设备码管理</a></li>
	        </c:if>
        </ul>
        <!-- h-nav end -->
    </div>
    <!-- /headBar -->
    <div class="navBar">
    </div>
    <!-- /navBar -->
</div>
</body>
</html>