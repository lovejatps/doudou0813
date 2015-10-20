<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>一键呼叫</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript">
		var basePath = "${base}";
	</script>
<%--     <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script> --%>
<%-- 	<script type="text/javascript" src="<c:url value='/js/jquery/jquery.mask.js'/>"></script> --%>
<%-- 	<script type="text/javascript" src="<c:url value='/layer/layer.min.js'/>"></script> --%>
<%-- 	<script type="text/javascript" src="<c:url value='/js/formValidator/formValidator-4.1.3.min.js'/>"></script> --%>
<%-- 	<script type="text/javascript" src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script> --%>
<%-- 	<script type="text/javascript" src="<c:url value='/js/car/register/carformvalidator.js?v=123'/>"></script> --%>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">车辆管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>租赁型</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>一键呼叫</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
        <form action="<c:url value='/buzregist/doUpdateCallPhone.htm'/>" id="normal" method="post" class="form-add clear">
        	<input id="custphone" value="${obj.custPhone}" name="phone.custPhone" type="hidden"/>
            <div class="label-form">
                <label class="left-add" for="phonevalue">紧急呼叫：</label>
                <div class="right-add">
                	<input type="hidden" name="phone.id" value="${obj.id}" />
                    <input type="text" name="phone.callPhone" value="${obj.callPhone}" id="phonevalue" class="inp_r"/>
                    <button type="submit" class="btn-green">提交</button>
                    <div id="phonevalueTip"></div>
                </div>
            </div>
        </form>
        
        <form action="<c:url value='/buzregist/doUpdateCustPhone.htm'/>" id="normal" method="post" class="form-add clear">
        	<input id="custphone" value="${obj.callPhone}" name="phone.callPhone" type="hidden"/>
            <div class="label-form">
                <label class="left-add" for="phonevalue">客服电话：</label>
                <div class="right-add">
                	<input type="hidden" name="phone.id" value="${obj.id}" />
                    <input type="text" name="phone.custPhone" value="${obj.custPhone}" id="phonevalue" class="inp_r"/>
                    <button type="submit" class="btn-green">提交</button>
                    <div id="phonevalueTip"></div>
                </div>
            </div>
        </form>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>