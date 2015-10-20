<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录日志</title>
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
			var url = basePath + "/buzuser/showUserLog.htm?temp=" + new Date().getTime();
			showTable(pageNo, pageSize, url);
		}
		
		function showTable(pageNo, pageSize, url) {
			if (!url) {
				url = basePath + "/buzuser/showUserLog.htm?temp=" + new Date().getTime();
			}
//	 		url += getQueryMap();
			showTemplateTable(pageNo, pageSize, url, "userPageMsgTemplate1",
					"tableTemplate", "tableTemplateTxt");
		}
		
		
		$(function(){
			showDeafaultTable(1, 10);
			$('.btn-green').live('click', function() {
				showDeafaultTable(1, pageSize);
				return false;
			});
		});
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">用户管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>用户管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>登录日志</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="searchBar clear">
        <!-- <div class="label-form">
            <label for="user-car" class="lb-label">用户名</label>
            <input class="lb-input" type="text" id="user-car"/>
        </div> -->
        <div class="label-form">
            <label for="date" class="lb-label">登录时间</label>
            <input class="lb-inputTime laydate-icon" type="text" id="date" style="width:180px;" name="queryMap.startDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
            <span class="lb-label">&nbsp;-</span>
            <input class="lb-inputTime laydate-icon" type="text"  style="width:180px;" name="queryMap.endDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
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
								<th align="center" width="20%">用户名称</th>
								<th align="center" width="20%">用户类型</th>
								<th align="center" width="20%">创建时间</th>
								<th align="center" width="20%">登录时间</th>
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
							<td align="center">{$T.createDate}</td>
							<td align="center">{$T.lastLoginDate}</td>					
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