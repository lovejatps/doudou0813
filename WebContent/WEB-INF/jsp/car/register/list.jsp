<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>车辆列表</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/laydate/laydate.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    
    <%@ include file="/WEB-INF/jsp/page-msg.jsp"%>
	<script type="text/javascript">
		//上一页，下一页，跳到第n页面(必须写方法,每个页面的请求不一样)
		function showDeafaultTable(pageNo, pageSize) {
			var url = basePath + "/buzregist/showPage.htm?temp=" + new Date().getTime();
			showTable(pageNo, pageSize, url);
		}
		
		function showTable(pageNo, pageSize, url) {
			if (!url) {
				url = basePath + "/buzregist/showPage.htm?temp=" + new Date().getTime();
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
		
		function deleteRegist(aniId){
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

		function deleted(reId){
			$.ajax({
				type: "post",
			    dataType: "json",
			    async:false,
			    url: "<c:url value='/buzregist/deleteRegist.htm'/>?reg.id="+reId,
			    success: function (data) {
					if(data==1){
						layer.alert("删除成功", 0);
						showDeafaultTable(1, 10);
					}
			    }
			});
		}
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">车辆管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>租赁型</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>车辆列表</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="searchBar clear">
        <div class="label-form">
            <label for="username" class="lb-label">用户名</label>
            <input class="lb-input" type="text" id="username" name="queryMap.username"/>
        </div>
        <div class="label-form">
            <label for="vin" class="lb-label">VIN</label>
            <input class="lb-input" type="text" id="vin" name="queryMap.vin"/>
        </div>
        <div class="label-form">
            <label for="carMachineId" class="lb-label">ID</label>
            <input class="lb-input" type="text" id="carMachineId" name="queryMap.carMachineId"/>
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
						<table class="u-tab">
							<thead>
							<tr>
								<th align="center" width="5%">序号</th>
								<th align="center" width="10%">VIN</th>
								<th align="center" width="30%">ID</th>
								<th align="center" width="10%">用户名</th>
								<th align="center" width="15%">注册时间</th>
								<th align="center" width="20%">最近登录时间</th>
								<th align="center" width="10%">操作</th>
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
							<td align="center">{$T.VIN}</td>
							<td align="center">{$T.carMachineId}</td>
							<td align="center">{$T.username}</td>
							<td align="center">{$T.createDate}</td>
							<td align="center">{$T.lastLoginDate}</td>
							<td align="center">
								<a href="#" onClick="deleteRegist({$T.id})">删除</a>
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