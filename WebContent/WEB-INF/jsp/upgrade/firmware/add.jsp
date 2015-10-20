<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>固件上传</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script src="<c:url value='/js/jquery/jquery.mask.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidator-4.1.3.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
	<script src="<c:url value='/js/upgrade/sys/firmwareformvalidator.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    	<script type="text/javascript">
		var basePath = "${base}";
		
		var load = "<div align='center' style='' class=> <img alt='loadding...' src='" + "<c:url value='/images/bigloading1.jpg'/>"+"'/><span class='load-all'>文件正在上传中，请勿离开此页面！</span></div>";

		function vers(){
			var longName = document.getElementById("firmFile").value;
			var num = longName.lastIndexOf("\\") ;
			var shortName = longName.substring(num+1);
			if(!/^[a-zA-Z0-9.\-_]*$/g.test(shortName)){
				layer.alert("名称只能出现大小写字母、数字、点、下划线、小横杆,例如Aa-10.zip",0);
			}else{
				document.getElementById('textfield').value=document.getElementById("firmFile").value 
			}  
		}
		
		function checkAnimation(){
			var str="";
			var model = document.getElementsByName("modelId");
			for(i=0;i<model.length;i++){
				if(model[i].checked==true){
					str += model[i].value + ",";
	            }
			}

			var version = document.getElementById("firmVersion").value;
			document.getElementById("modelIdMassage").innerHTML = "";
			if(str && version){
				$.ajax({
					type: "post",
				    dataType: "json",
				    async:false,
				    url: "<c:url value='/upgrade/firmware/checkFirmware.htm'/>?modelIds="+str+"&version="+version,
				    success: function (data) {
						if(data){
							document.getElementById("modelIdMassage").innerHTML = data + "已存在此版本固件包,确认添加将覆盖原固件包";
						}else{
							document.getElementById("modelIdMassage").innerHTML = "";
						}
				    }
				});
			}
			
		}
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">升级管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>固件管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>固件上传</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
        <form action="<c:url value='/upgrade/firmware/doInputFirmware.htm'/>" id="normal" method="post" class="form-add clear" enctype="multipart/form-data">
            <div class="label-form">
                <div class="left-add">固件型号：</div>
                
                	<div class="right-add" style="width: 400px; height:auto;">
                	<c:forEach items="${obj}" var="z" varStatus="list">
                	<div class="f-left">
					<input id="modelId" onchange="checkAnimation();" class="c_l" 
						<c:if test="${fn:length(obj)==1}">checked="checked"</c:if>
						type="checkbox" name="modelId" value="${z.id}"/>
						<label class="c_r">${z.name}</label>
					</div>
					</c:forEach>
					</div>
				
				<div style="color: red; clear:both;font-size: 14px; margin-left: 138px;" id="modelIdMassage"></div>
            </div>
            <div class="label-form">
                <label class="left-add" for="firmVersion">固件版本：</label>
                <div class="right-add">
                    <input type="text" name="firm.firmVersion" id="firmVersion" 
                    			onblur="checkAnimation();" class="inp_r"/>
                    <div id="firmVersionTip"></div>
                </div>
            </div>
            <div class="label-form">
                <div class="left-add">固件包文件：</div>
                <div class="right-add">
                    <input type="file" name="firmFile" class="up-file" id="firmFile" size="28" onchange="vers()">
                    <input class="up-left" name="textfield" id="textfield"/>
                    <span class="up-right">选择文件</span>
<!--                     <span style="position: absolute; bottom: -30px; left: 7px; font-size:12px; color:#999;"><i style="color:red">*</i>&nbsp;&nbsp;格式要求：zip,rar,tgz</span> -->
<!--                     <button class="up-up">上传</button> -->
                </div>
            </div>
            <div class="label-form" style="position:relative;">
                <label class="left-add" for="describe">描述：</label>
                <textarea class="texta-from" name="firm.describes" id="describe"></textarea>
                <div id="describeTip"></div>
            </div>
            <div class="label-form">
                <div class="left-add"></div>
                <button class="btn-green single" id="sys_add_btn_id004">提交</button>
            </div>
        </form>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>