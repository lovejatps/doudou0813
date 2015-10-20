<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html style="">
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>欢迎登录豆豆车载系统管理平台</title>
    <link rel="stylesheet" href="<c:url value='/css/login.css'/>" >
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/layer/layer.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/formValidator/formValidator-4.1.3.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    
    <script type="text/javascript">
		if (window.parent != window) {
			top.location.href = location.href;
		}

		function checkInput(){
			var username = $("#username").val();
			var password = $("#password").val();
			//alert(username + "-----------" + password);
			if(username && password){
				$.ajax({
					type: "post",
				    dataType: "json",
				    async:false,
				    url: "<c:url value='/buzuser/checkLogin.htm'/>?username="+username+"&pwd="+password,
				    success: function (data) {
				    	if(data==1){
				    		$("#normal")[0].submit();
				    	}else{
				    		layer.alert(data, 8);
				    		$("#password").attr("value", "");
				    	}
						//layer.tips(data, this , {guide: 1, time: 200});
				    }
				});
				
			}
		}
		
	</script>
    
</head>
<body style="_height:100%;">
<div class="login-logo"></div>
<!-- /login-logo -->
<div class="login-box">
    <div class="lb-title">
        豆豆车载系统管理平台
    </div>
    <!-- /lb-title -->
    <div class="lb-main">
        <form action="<c:url value='/buzuser/login2.htm'/>" id="normal" method="post" class="registerform">
            <div class="form-group">
                <label for="username" class="form-left">用户</label>
                <div class="form-right">
                    <input class="form-input" name="username" type="text" id="username" autocomplete="off"/>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="form-left">密码</label>
                <div class="form-right">
                    <input class="form-input" name="pwd" type="password" id="password" autocomplete="off"/>
                </div>
            </div>
            <div class="form-group">
                <div class="form-right">
                    <button type="button" onclick="checkInput();" class="btn-green">登录</button>
                </div>
            </div>
        </form>
    </div>
    <!-- /lb-main -->
</div>
<!-- /login-box -->
</body>
</html>