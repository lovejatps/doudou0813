<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>APP列表</title>
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
			var url = basePath + "/upgrade/appPack/showAppPack.htm?temp=" + new Date().getTime();
			showTable(pageNo, pageSize, url);
		}
		
		function showTable(pageNo, pageSize, url) {
			if (!url) {
				url = basePath + "/upgrade/appPack/showAppPack.htm?temp=" + new Date().getTime();
			}
			showTemplateTable(pageNo, pageSize, url, "userPageMsgTemplate1",
					"tableTemplate", "tableTemplateTxt");
		}
		
		$(function(){
			var douc = window.parent.frames["side"].document.getElementById("applist");
			douc.className="ed";
			var douc = window.parent.frames["side"].document.getElementById("appadd");
			if(!!douc){
				douc.className="";
			}
			showDeafaultTable(1, 10);
			$('.btn-green').live('click', function() {
				showDeafaultTable(1, pageSize);
				return false;
			});
		});

		function deleteApp(aniId){
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
		
		function deleted(appid){
			$.ajax({
				type: "post",
			    dataType: "json",
			    async:false,
			    url: "<c:url value='/upgrade/appPack/deleteApp.htm'/>?appId="+appid,
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
            <li class="bread-home"><a href="javascript:void(0);">升级管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>APP管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>APP列表</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="searchBar clear">
        <div class="label-form">
            <label for="user-car" class="lb-label">应用名称</label>
            <input class="lb-input" type="text" name="queryMap.appName" id="user-car"/>
        </div>
        <div class="label-form">
            <div class="left-add">应用分类：</div>
            <div class="right-add u-select">
                <!--<h5 class="seled gray">请选择</h5>
                <ul class="select-ul">
                    <li><a href="#">车载</a></li>
                    <li><a href="#">软件</a></li>
                    <li><a href="#">分类</a></li>
                  </ul> -->
                <select id="appClassify" class="inp_r" style="height:32px; line-height:32px; padding-right:0;" name="queryMap.appClassify">
							<option value="" >请选择</option>
							<option value="1">车载</option>
							<option value="2">软件</option>
							<option value="3">游戏</option>
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
		<textarea id="tableTemplateTxt" style="display: none">
			<![CDATA[
					{#template MAIN}
						<table 	class="u-tab">
							<thead>
							<tr>
								<th align="center" width="5%">序号</th>
								<th align="center" width="15%">应用名称</th>
								<th align="center" width="15%">版本号</th>
								<th align="center" >系统型号</th>
								<th align="center" width="10%">所属厂家</th>
								<th align="center" width="10%">应用分类</th>
								<th align="center" width="10%">上传时间</th>
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
							<td align="center">{$T.appName}</td>
							<td align="center">{$T.appVersion}</td>
							<td align="center">{$T.stringModle}</td>
							<td align="center">{$T.produceName}</td>
							<td align="center">
								{#if $T.appClassify==1}车载{#/if}
								{#if $T.appClassify==2}软件{#/if}
								{#if $T.appClassify==3}游戏{#/if}
							</td>
							
							<td align="center">{$T.createDate}</td>
							<c:if test="${roleId==-1}">
								<td align="center">
									<!--<a href="<c:url value='/upgrade/doDownLoad.htm'/>?fileId={$T.appFileId}" >下载</a>-->
									<c:if test="${roleId==-1}">
										<a href="<c:url value='/upgrade/appPack/updateApp.htm'/>?id={$T.id}" >编辑</a>
										<a href="#" onClick="deleteApp({$T.id})">删除</a>
									</c:if>
									<a href="<c:url value='/upgrade/appPack/detailApp.htm'/>?id={$T.id}" >详情</a>
								</td>
							</c:if>
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