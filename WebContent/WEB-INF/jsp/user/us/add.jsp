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
    <script src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidator-4.1.3.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
	<script src="<c:url value='/js/user/us/userformvalidator.js'/>"></script>
	<script type="text/javascript">
		var basePath = "${base}";
		
		function check(){
			document.getElementById("loginNameTip").innerHTML="";
			document.getElementById("roelIdTip").innerHTML="";
			document.getElementById("produceidTip").innerHTML="";
			document.getElementById("password2Tip").innerHTML="";
			document.getElementById("password1Tip").innerHTML="";
		}
		
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">用户管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>用户管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>用户添加</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
        <form action="<c:url value='/buzuser/doInputUser.htm'/>" class="form-add clear" id="normal" method="post">
            <div class="label-form">
                <label class="left-add" for="loginName">用户名：</label>
                <div class="right-add">
                    <input type="text" id="loginName" class="inp_r" name="user.loginName"/>
                    <div id="loginNameTip"></div>
                </div>
            </div>
            <div class="label-form">
                <div class="left-add">用户类型：</div>
                <div class="right-add u-select">
                   <!-- <h5 class="seled gray">请选择</h5>
                    <ul class="select-ul">
                        <li><a href="#">管理员</a></li>
                        <li><a href="#">厂家</a></li>
                        <li><a href="#">普通用户</a></li>
                     </ul> -->
                    <select id="roelId" class="inp_r" style=" width:233px; height:30px; line-height:30px; padding-right: 0" name="user.roleId">
							<option value="" >请选择</option>
							<option value="-1">管理员</option>
							<option value="-2">厂家</option>
					</select>
					<div id="roelIdTip"></div>
                </div>
            </div>
            <div class="label-form">
                <label class="left-add">所属厂家：</label>
                 <div class="right-add u-select">
                    <select id="produceid" class="inp_r" style=" width:233px; height:30px; line-height:30px; padding-right: 0" name="user.produceid">
							<option value="" >请选择</option>
							<c:forEach items="${obj}" var="z" varStatus="list">
								<option value="${z.id}" >${z.name}</option>
							</c:forEach>
					</select>
					<div id="produceidTip"></div>
                </div>
            </div>
            <div class="label-form">
                <label class="left-add" for="password1">密码：</label>
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
                <button class="btn-green double" id="user_btn-green_id">添加</button>
                <button type="reset" onclick="check();" class="btn-green single">重置</button>
            </div>
        </form>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>