<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>升级包列表</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/laydate/laydate.js'/>"></script>
    <%@ include file="/WEB-INF/jsp/page-msg.jsp"%>
	<script type="text/javascript">
		//上一页，下一页，跳到第n页面(必须写方法,每个页面的请求不一样)
		function showDeafaultTable(pageNo, pageSize) {
			var url = basePath + "/upgrade/syspackage/showPagePack.htm?temp=" + new Date().getTime();
			showTable(pageNo, pageSize, url);
		}
		
		function showTable(pageNo, pageSize, url) {
			if (!url) {
				url = basePath + "/upgrade/syspackage/showPagePack.htm?temp=" + new Date().getTime();
			}
//	 		url += getQueryMap();
			showTemplateTable(pageNo, pageSize, url, "userPageMsgTemplate1",
					"tableTemplate", "tableTemplateTxt");
		}
		
		
		$(function(){
			var douc = window.parent.frames["side"].document.getElementById("packlist");
			douc.className="ed";
			var douc = window.parent.frames["side"].document.getElementById("packadd");
			if(!!douc){
				douc.className="";
			}			
			showDeafaultTable(1, 10);
			$('.btn-green').live('click', function() {
				showDeafaultTable(1, pageSize);
				return false;
			});
		});
		
		function deleteSyspackage(aniId){
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

		function deleted(syspackageId){
			$.ajax({
				type: "post",
			    dataType: "json",
			    async:false,
			    url: "<c:url value='/upgrade/syspackage/deleteSyspackage.htm'/>?syspackage.id="+syspackageId,
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
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>系统包管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>系统包列表</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="searchBar clear">
        <div class="label-form">
            <label for="user-car" class="lb-label">系统版本</label>
            <input class="lb-input" type="text" id="user-car" name="queryMap.version"/>
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
								<th align="center" width="10%">系统版本</th>
								<th align="center" width="15%">类型</th>
								<th align="center" width="15%">系统型号</th>
								<th align="center">系统包</th>
								<th align="center" width="10%">所属厂家</th>
								<th align="center" width="15%">上传时间</th>
								<c:if test="${roleId==-1}">	
									<th align="center" width="15%">操作</th>
								</c:if>
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
							<td align="center">{$T.sysVersion}</td>
							
							<td align="center">
								{#if $T.type==1}完整包{#/if}
								{#if $T.type==2}增量包{#/if}
							</td>
							
							<td align="center">{$T.stringModle}</td>
							<td align="center">{$T.fileName}</td>
							<td align="center">{$T.producename}</td>  
							<td align="center">{$T.createDate}</td>		
							<c:if test="${roleId==-1}">			
								<td align="center">
									<!--<a href="<c:url value='/upgrade/doDownLoad.htm'/>?fileId={$T.fileId}" >下载</a>-->
									
										<a href="<c:url value='/upgrade/syspackage/updateSysPackage.htm'/>?id={$T.id}" >编辑</a>
										<a href="#" onClick="deleteSyspackage({$T.id})">删除</a>
									
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