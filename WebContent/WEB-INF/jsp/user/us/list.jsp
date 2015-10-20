<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户列表</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/laydate/laydate.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <%@ include file="/WEB-INF/jsp/page-msg.jsp"%>
	<script type="text/javascript">
		//上一页，下一页，跳到第n页面(必须写方法,每个页面的请求不一样)
		function showDeafaultTable(pageNo, pageSize) {
			var url = basePath + "/buzuser/showPage.htm?temp=" + new Date().getTime();
			showTable(pageNo, pageSize, url);
		}
		
		function showTable(pageNo, pageSize, url) {
			if (!url) {
				url = basePath + "/buzuser/showPage.htm?temp=" + new Date().getTime();
			}
//	 		url += getQueryMap();
			showTemplateTable(pageNo, pageSize, url, "userPageMsgTemplate1",
					"tableTemplate", "tableTemplateTxt");
		}
		
		
		$(function(){
			var douc = window.parent.frames["side"].document.getElementById("userlist");
			douc.className="ed";
			var douc = window.parent.frames["side"].document.getElementById("useradd");
			douc.className="";
			showDeafaultTable(1, 10);
			$('.btn-green').live('click', function() {
				showDeafaultTable(1, pageSize);
				return false;
			});
		});
		
		function deleteUser(aniId){
			$.layer({
			    shade: [0],
			    area: ['auto','auto'],
			    dialog: {
			        msg: '是否确定删除？',
			        btns: 2,                    
			        type: 4,
			        btn: ['确定','取消'],
			        yes: function(){
			        	deleted(aniId);
			        }, no: function(){
			            bool = false;
			        }
			    }
			});
			
		}

		function deleted(userId){
			$.ajax({
				type: "post",
			    dataType: "json",
			    async:false,
			    url: "<c:url value='/buzuser/deleteUser.htm'/>?user.id="+userId,
			    success: function (data) {
					if(data==1){
						layer.alert("删除成功", 0);
						showDeafaultTable(1, 10);
					}else if (data==2){
						layer.alert("无法删除本人用户！", 0);
					}
			    }
			});
		}
		
		function updateUser(userId){
			window.location.href = "<c:url value='/buzuser/toUpdateUser.htm'/>?user.id="+userId;
		}
		
		function updatePwd(userId){
			window.location.href = "<c:url value='/buzuser/toUpdatePwd.htm'/>?user.id="+userId;
		}
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">用户管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>用户管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>用户列表</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="searchBar clear">
        <div class="label-form">
            <label for="user-car" class="lb-label">用户名</label>
            <input class="lb-input" type="text" id="user-car" name="queryMap.loginName"/>
        </div>
        <div class="label-form">
            <label for="user-car" class="left-add">用户类型</label>
            <div class="right-add u-select">
            <select id="roleId" class="inp_r" style="height:32px; line-height:32px; padding-right:0;"   name="queryMap.roleId">
							<option value="" >请选择</option>
							<option value="-1">管理员</option>
							<option value="-2">厂家</option>
			</select>
			</div>
        </div>
        <div class="label-form">
            <button class="btn-green">搜索</button>
        </div>
    </div>
    <!-- /searchBar -->
    <div class="tableList">
        
        <div id="tableTemplate" class="table"></div>
		<!-- 	在这里写你的表格模版 -->
		<textarea id="tableTemplateTxt" style="display: none">
			<![CDATA[
					{#template MAIN}
						<table 	class="u-tab">
							<thead>
							<tr>
								<th align="center" width="5%">序号</th>
								<th align="center" width="15%">用户名</th>
								<th align="center" width="15%">用户类型</th>
								<th align="center" width="15%">所属厂家</th>
								<th align="center" width="15%">创建时间</th>
								<th align="center" width="20%">最近登录时间</th>
								<th align="center" width="15%">操作</th>
							</tr>
							</thead>
							<tbody>
						{#foreach $T.list as row}
							{#include ROW root=$T.row}
						{#/for}
							</tbody>
						</table>
					{#/template MAIN}
					
					{#template ROW}
						<tr>
							<td align="center">{$T.serialNumber}</td>
							<td align="center">{$T.loginName}</td>
							<td align="center">{$T.roleName}</td>
							<td align="center">{$T.produceName}</td>
							<td align="center">{$T.createDate}</td>
							<td align="center">{$T.lastLoginDate}</td>					
							<td align="center">
								<c:if test="${obj.roleId==-1}">
									{#if $T.id!=-1}
										<a href="#" onClick="updateUser({$T.id})">修改</a>
									{#/if}
									<a href="#" onClick="deleteUser({$T.id})">删除</a>
									
									{#if $T.id!=-1}
										<a href="#" onClick="updatePwd({$T.id})">修改密码</a>
									{#/if}
								</c:if>
								<c:if test="${obj.roleId!=-1}">
									{#if $T.id==${obj.id}}
									<a href="#" onClick="updatePwd({$T.id})">修改密码</a>
									{#/if}
								</c:if>
							</td>
						</tr>
					{#/template ROW}
			]]>
		</textarea>
		
		<div class="page-link clear" id="userPageMsgTemplate1"></div>
        <!-- /page-link -->
    </div>
    <!-- /tableList -->
</div>
</body>
</html>