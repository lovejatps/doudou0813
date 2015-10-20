<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>型号列表</title>
	<link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/laydate/laydate.js'/>"></script>
	
	<%@ include file="/WEB-INF/jsp/page-msg.jsp"%>
   <script type="text/javascript">
		//上一页，下一页，跳到第n页面(必须写方法,每个页面的请求不一样)
		function showDeafaultTable(pageNo, pageSize) {
			var url = basePath + "/upgrade/sys/showPage.htm?temp=" + new Date().getTime();
			showTable(pageNo, pageSize, url);
		}
		
		function showTable(pageNo, pageSize, url) {
			if (!url) {
				url = basePath + "/upgrade/sys/showPage.htm?temp=" + new Date().getTime();
			}
//	 		url += getQueryMap();
			showTemplateTable(pageNo, pageSize, url, "userPageMsgTemplate1",
					"tableTemplate", "tableTemplateTxt");
		}
		
		
		$(function(){
			var douc = window.parent.frames["side"].document.getElementById("syslist");
			douc.className="ed";
			var douc = window.parent.frames["side"].document.getElementById("sysadd");
			douc.className="";
			showDeafaultTable(1, 10);
			$('.btn-green').live('click', function() {
				showDeafaultTable(1, pageSize);
				return false;
			});
		});
		
		function deleteSys(aniId){
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

		function deleted(sysId){
			$.ajax({
				type: "post",
			    dataType: "json",
			    async:false,
			    url: "<c:url value='/upgrade/sys/deleteSys.htm'/>?sys.id="+sysId,
			    success: function (data) {
					if(data==1){
						layer.alert("删除成功", 0);
						showDeafaultTable(1, 10);
					}else if(data==2){
						layer.alert("此型号正在使用中, 无法删除", 0);
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
            <li class="bread-home"><a href="javascript:void(0);">升级管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>系统型号</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>型号列表</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="searchBar clear">
        <div class="label-form">
            <label for="user-car" class="lb-label">系统型号</label>
            <input class="lb-input" type="text" id="user-car" name="queryMap.sys"/>
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
								<th align="center" width="10%">序号</th>
								<th align="center">系统型号</th>
								<th align="center" width="25%">所属厂家</th>
								<th align="center" width="20%">添加日期</th>
								<th align="center" width="20%">操作</th>
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
							<td align="center">{$T.name}</td>
							<td align="center">{$T.produeName}</td>
							<td align="center">{$T.createDate}</td>					
							<td align="center">
								<a href="#" onClick="deleteSys({$T.id})">删除</a>
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