<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户添加</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
		var basePath = "${base}";
	</script>
	<script src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidator-4.1.3.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
	<script src="<c:url value='/js/user/us/updateUserformvalidator.js'/>"></script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">用户管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>用户列表</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>密码修改</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
        <form action="<c:url value='/buzuser/doUpdatePwd.htm'/>" class="form-add clear" id="normal" method="post">
            <div class="label-form">
                <div class="left-add">用户名：</div>
                <div class="right-add">
                    ${obj.loginName}
                    <input type="hidden" id="userId" name="user.id" value="${obj.id}"/>
                </div>
            </div>
            <div class="label-form">
                <label class="left-add" for="password1">新密码：</label>
                <div class="right-add">
                    <input id="password1" type="password" class="inp_r" name="user.pwd"></input>
						<div id="password1Tip"></div>
                </div>
            </div>
            <div class="label-form">
                <label class="left-add" for="password2">确认密码：</label>
                <div class="right-add">
                    <input id="password2" type="password" class="inp_r" name="rePwd"></input>
						<div id="password2Tip"></div>
                </div>
            </div>
            <div class="label-form">
                <div class="left-add"></div>
                <button class="btn-green single">确定</button>
            </div>
        </form>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>