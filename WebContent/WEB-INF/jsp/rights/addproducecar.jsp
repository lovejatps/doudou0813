<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>厂家添加</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery/jquery.mask.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/layer/layer.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/formValidator/formValidator-4.1.3.min.js'/>"></script>
	<!--  script type="text/javascript" src="<c:url value='/js/sbm/sn/snformvalidator.js?v=123'/>"></script -->
	<script src="<c:url value='/js/user/us/addproducecar.js?v=123333'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
	<script type="text/javascript">
		var basePath = "${base}";		
		function check(){
			document.getElementById("prodNameTip").innerHTML="";
			document.getElementById("prodaddressTip").innerHTML="";
		}
		
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">用户管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>厂家管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>厂家添加</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
  <div class="formBody">
        <form action="<c:url value='/rights/add.htm'/>" class="form-add clear"  id="normal" method="post">
            <div class="label-form">
                <label class="left-add" for="prodName">厂家名称：</label>
                <div class="right-add">                
                    <input type="text" name="prod.name" id="prodName" class="inp_r"/>  
                    <div  id="prodNameTip"></div>        
                </div>                 
            </div>
            <div class="label-form">
                <label class="left-add" for="prodaddress">厂家地址：</label>
                <div class="right-add">
                    <input type="text" name="prod.address" id="prodaddress" class="inp_r"/>
                    <div id="prodaddressTip"></div>
                </div>
            </div>
            <div class="label-form">
                <div class="center-add" >
                <button class="btn-green" id="rights_btn-green_id">提交</button>  
                </div>
            </div>
            </form>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>