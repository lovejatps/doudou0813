<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ID添加</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery/jquery.mask.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/layer/layer.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/formValidator/formValidator-4.1.3.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/sbm/id/idformvalidator.js?v=123'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
		var basePath = "${base}";
		
		var load = "<div align='center' style='' class=> <img alt='loadding...' src='"
			+"<c:url value='/images/bigloading1.jpg'/>"+"'/><span class='load-all'>文件正在上传中，请勿离开此页面！</span></div>";
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">设备码管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>ID管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>ID添加</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
        <div class="form-add clear">
        	<form action="<c:url value='/buzid/doInputId.htm'/>" id="normal" method="post">
            <div class="label-form">
                <label class="left-add" for="idvalue">ID：</label>
                <div class="right-add">
                    <input type="text" name="car.value" id="idvalue" class="inp_r"/>
                    <button class="btn-green">提交</button>
                    <div id="idvalueTip"></div>
                </div>
            </div>
            </form>
            
            <form action="<c:url value='/buzid/doInputIdFile.htm'/>" id="batch" onsubmit="return myForm.batch();" method="post" enctype="multipart/form-data">
            <div class="label-form">
                <div class="left-add">批量添加：</div>
                <div class="right-add">
                    <input type="file" name="idFile" class="up-file" id="idFile" size="28" onchange="document.getElementById('textfield').value=this.value">
                    <input class="up-left" name="textfield" id="textfield"/>
                    <span class="up-right">选择文件</span>
                    <button class="btn-green">上传</button>
                </div>
            </div>
            </form>
            
            <div class="label-form">
                <label class="left-add" for="xtms"><i>*&nbsp;&nbsp;</i></label>
                <p class="right-add">
                    <span class="sm">
                        文件格式要求：<br/>
                        1.后缀为.txt&nbsp;&nbsp;&nbsp;2.id为34位<br/>
                        3.内容格式要求，每个号码占一行，示例如下：<br/>
                    </span>
                    <span class="no-img">
                        X3DBJYBSX8YFCD2KFPRRA5TGBR8454DF14<br/>
                        25NHERJUX5YKFH7FAERTB4SDT2KFPRRA51<br/>
                        Q4FGHDTSXXXYLS2QZSOK5LZAYERJUX5YKF
                    </span>
                </p>
            </div>
        </div>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>