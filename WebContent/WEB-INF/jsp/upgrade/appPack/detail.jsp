<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>APP上传</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
		var basePath = "${base}";
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">升级管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>APP管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>APP详情</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
         <div class="form-add clear">
            			
            <div class="label-form" style="padding-bottom:10px;">
                <div class="left-add">应用安装包：</div>
            	    <div class="right-add" id="pictureDiv" style="height: auto;" >
            	    			<img src="<c:url value='/upgrade/doDownLoad.htm'/>?fileId=${obj.app.iconId}"  width='100' height='100' style="float:left;"></img>
            	    			<p style="float:left; padding-left:20px;line-height:24px;">
            	    			应用名称：${obj.app.appName} <br>
            	    			包名：${obj.app.packName} <br>
            	    			版名号：${obj.app.appVersion}
            	    			</p>
            	    </div>
            </div>
            
           <form action="<c:url value='/upgrade/appPack/listApp.htm'/>" id="normal" method="post"   enctype="multipart/form-data">
            
            <div class="label-form">
                <div class="left-add">系统型号：</div>
                <div class="right-add" style=" background:#eee; min-width: 130px;">
               		${obj.appSystemString}
				</div>
            </div>
            
            <div class="label-form">
                <label class="left-add" for="appName">应用名称：</label>
                <div class="right-add" style="min-width: 130px; background:#eee;">
                	${obj.app.appName}
                </div>
            </div>
            <div class="label-form">
                <label class="left-add" for="bbh">版本号：</label>
                <div class="right-add" style="min-width: 130px;  background:#eee;">
                	${obj.app.appVersion}
                </div>
            </div>
            
            <div class="label-form">
                <label class="left-add" for="bbh">包名：</label>
                <div class="right-add" style="min-width: 130px;  background:#eee;">
                	${obj.app.packName}
                </div>
            </div>
            
            <div class="label-form">
                <div class="left-add">应用分类：</div>
                <div class="right-add" style="min-width: 130px;  background:#eee;">
						<c:if test="${obj.app.appClassify==1}">车载</c:if>
						<c:if test="${obj.app.appClassify==2}">软件</c:if>
						<c:if test="${obj.app.appClassify==3}">游戏</c:if>
                </div>
            </div>
            
            <div class="label-form right-addpic" id="pictureFaDiv" style="width: 800px;">
           			<div class="left-add">应用截图：</div>
            	    <div class="right-add" id="pictureDiv" style="width: 650px;">
            	    	<c:forEach items="${obj.appPic}" var="p" varStatus="plist">
            	    		<div style="position: relative; float:left;"  id="divCross_${p.fileId}">
            	    		<img src="<c:url value='/upgrade/doDownLoad.htm'/>?fileId=${p.fileId}"  height='130'></img>
            	    		</div>
            	    	</c:forEach>
            	    </div>
            </div>
            
            <div class="label-form">
                <div class="left-add">是否默认升级：</div>
                <div class="right-add" style="min-width: 130px;  background:#eee;">
						<c:if test="${obj.app.upgrade=='YES'}">是</c:if>
						<c:if test="${obj.app.upgrade=='NO'}">否</c:if>
                </div>
            </div>
            
            <div class="label-form">
                <div class="left-add">推荐指数：</div>
                <div class="right-add" style="min-width: 130px;  background:#eee;">
						<c:if test="${obj.app.recommendIndex==1}">1</c:if>
						<c:if test="${obj.app.recommendIndex==2}">2</c:if>
						<c:if test="${obj.app.recommendIndex==3}">3</c:if>
						<c:if test="${obj.app.recommendIndex==4}">4</c:if>
						<c:if test="${obj.app.recommendIndex==5}">5</c:if>
                </div>
            </div>
            
            
            <div class="label-form" style="position: relative;">
                <label class="left-add" for="describe">应用描述：</label>
                <div class="right-add" style="width:400px; height:auto;word-wrap:break-word; background:#eee; min-height:100px;">${obj.app.describes}</div>
            </div>
            <div class="label-form" style="position: relative;">
                <label class="left-add" for="detail">更新描述：</label>
                <div class="right-add" style="width:400px; height:auto;word-wrap:break-word; background:#eee;min-height:100px;">${obj.app.detail}</div>
                
            </div>
            <div class="label-form">
                <div class="left-add"></div>
                <button class="btn-green single" onclick="window.location.href='<c:url value='/upgrade/appPack/listApp.htm'/>'" >返回</button>
            </div>
        </form>
        </div>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>