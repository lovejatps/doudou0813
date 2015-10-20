<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>添加成功</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
		$(function(){
			 countDown(5);
		});
	function countDown(secs){
		$('#jumpTo').empty();
		$('#jumpTo').append(secs);
	 	if(--secs>0){
	     	setTimeout("countDown("+secs+")",1000); 
	     }else{
	     	$(".btn-green").trigger("click");
		 }
	 }		
</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">用户管理操作提示</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
  <c:if test="${obj=='addUser'}">  
    <div class="successTip">
        <div class="centerTip">
            <p><img src="<c:url value='/images/bigok.png'/>" alt="success"/></p>
            <p class="red">1条信息添加成功</p>
            <p>您可以继续 <a href="<c:url value='/buzuser/add.htm'/>">【添加用户信息】</a>，<span id="jumpTo">5</span>秒后页面自动回到列表页面</p>
            <p><button onclick="window.location.href='<c:url value='/buzuser/list.htm'/>'" class="btn-green">确定</button></p>
        </div>
    </div>
  </c:if>
    <!-- /successTip -->
  <c:if test="${obj=='updateUser'}">
   <div class="successTip">
        <div class="centerTip">
            <p><img src="<c:url value='/images/bigok.png'/>" alt="success"/></p>
            <p class="red">修改成功</p>
            <p><span id="jumpTo">5</span>秒后页面自动回到列表页面</p>
            <p><button onclick="window.location.href='<c:url value='/buzuser/list.htm'/>'" class="btn-green">确定</button></p>
        </div>
    </div>
  </c:if>
  
</div>
</body>
</html>