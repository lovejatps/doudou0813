<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>固件型号添加</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript">
		var basePath = "${base}";
		function check(){
			document.getElementById("firmvalueTip").innerHTML="";
			document.getElementById("produceidTip").innerHTML="";
		}
	</script>
   <!--  <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
   		<script src="<c:url value='/js/jquery/jquery.mask.js'/>"></script>
   --> 
	<script src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>	
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidator-4.1.3.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
	<script src="<c:url value='/js/upgrade/sys/firmformvalidator.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">升级管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>固件型号</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>型号添加</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
        <form action="<c:url value='/upgrade/firm/doInputFirm.htm'/>" id="normal" method="post" class="form-add clear">
            <div class="label-form">
                <label class="left-add" for="bbh">固件型号：</label>
                <div class="right-add">
                    <input type="text" name="firm.name" id="firmvalue" class="inp_r"/>                    
                    <div id="firmvalueTip"></div>
                </div>
            </div>
            <div class="label-form">
                 <label class="left-add">所属厂家：</label>
                 <div class="right-add u-select">
                    <select id="produceid" class="inp_r" style=" width:233px; height:30px; line-height:30px; padding-right: 0" name="firm.produceid">
							<option value="" >请选择</option>
							<c:forEach items="${obj}" var="z" varStatus="list">
								<option value="${z.id}" >${z.name}</option>
							</c:forEach>
					</select>
					<div id="produceidTip"></div>
				</div>	
           </div>
                
            <div class="label-form">
                <div class="center-add" >
                <button class="btn-green" id="sys_add_btn_id005">提交</button>  
                </div>
            </div>
        </form>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>