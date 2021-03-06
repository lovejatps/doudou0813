<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>系统包上传</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script src="<c:url value='/js/jquery/jquery.mask.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidator-4.1.3.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
	<script src="<c:url value='/js/upgrade/sys/updatesyspackageformvalidator.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    
    	<script type="text/javascript">
		var basePath = "${base}";

		var load = "<div align='center' style='' class=> <img alt='loadding...' src='"
			+"<c:url value='/images/bigloading1.jpg'/>"+"'/><span class='load-all'>文件正在上传中，请勿离开此页面！</span></div>";
		
			function checkSysPackageModel(){
				var type = document.getElementById("packageType").value;
				var version;
				if(type==2){
					version = $("#packvalue1").val()+"-"+$("#packvalue2").val()
					//$("#packvalue").val($("#packvalue1").val()+"-"+$("#packvalue2").val());
					$("#packvalue")[0].type = 'hidden';
					$("#packvalueTip").css("display", "none");
					$("#typeValue").css("display", "");
				}else if(type==1){
					version = document.getElementById("packvalue").value;
					$("#packvalue")[0].type = 'text';
					$("#packvalueTip").css("display", "");
					$("#typeValue").css("display", "none");
				}
				var str="";
				var model = document.getElementsByName("modelId");
				for(i=0;i<model.length;i++){
					if(model[i].checked==true){
						str += model[i].value + ",";
		            }
				}

				
				document.getElementById("modelIdMassage").innerHTML = "";
				if(str && type && version){
					$.ajax({
						type: "post",
					    dataType: "json",
					    async:false,
					    url: "<c:url value='/upgrade/syspackage/checkSyspackage.htm'/>?modelIds="+str+"&type="+type+"&version="+version,
					    success: function (data) {
							if(data){
								document.getElementById("modelIdMassage").innerHTML = data + "已存在此版本的系统包,确认添加将覆盖原版本包";
								//$("modelIdMassage").html(data + "已存在此版本的系统包,确认添加将覆盖原版本包");
							}else{
								document.getElementById("modelIdMassage").innerHTML = "";
							}
					    }
					});
				}
				
			}
		
		$(function(){
			var str = '${obj.sysPack.sysVersion}';
			var str1 = str.split("-");
			$("#packvalue1").val(str1[0]);
			$("#packvalue2").val(str1[1]);
		})
		
	</script>
    
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">升级管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>系统包管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>系统包上传</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
        <form action="<c:url value='/upgrade/syspackage/doUpdateSyspackage.htm'/>"  id="normal"
        					 method="post" class="form-add clear" enctype="multipart/form-data">
        	<input name="package.id" type="hidden" value="${obj.sysPack.id}" />
        	<input name="package.fileId" type="hidden" value="${obj.sysPack.fileId}" />
        	<input name="package.fileName" type="hidden" value="${obj.sysPack.fileName}" />
        	<input name="package.createDate" type="hidden" value="${obj.sysPack.createDate}" />
        	<input name="package.deleted" type="hidden" value="${obj.sysPack.deleted}" />
            <div class="label-form">
                <div class="left-add">系统型号：</div>
                
                	<div class="right-add"  style="width: 400px; height:auto;">
                <c:forEach items="${obj.sysMod}" var="z" varStatus="list">
                <div class="f-left">
					<input id="modelId" onchange="checkSysPackageModel();" class="c_l" 
					<c:forEach items="${obj.sysPackMod}" var="m" varStatus="mlist">
						<c:if test="${m.sysModelId==z.id}">checked="checked"</c:if>
					</c:forEach>
						type="checkbox" name="modelId" value="${z.id}"/>
						<label class="c_r">${z.name}</label>
						</div>
					</c:forEach>
					</div>
				<div style="color: red; clear:both;font-size: 14px; margin-left: 138px;" id="modelIdMassage"></div>
            </div>
            <div class="label-form">
                <label class="left-add" for="bbh">系统版本：</label>
                <div class="right-add">
                	<c:if test="${obj.sysPack.type==1}">
                    <input type="text" onblur="checkSysPackageModel()" name="package.sysVersion" value="${obj.sysPack.sysVersion}"
                    			 id="packvalue" class="inp_r"/>
	                <div id="packvalueTip"></div>
	                </c:if>
	                
	                <c:if test="${obj.sysPack.type==2}">
	                <div style="float: left;" id="typeValue">
                    	<input type="text" onblur="checkSysPackageModel()" style="width: 93px;" 
                    			 id="packvalue1" class="inp_r"/> <span style="float: left;margin: 0 10px;">-</span>
                  	 	<input type="text" onblur="checkSysPackageModel()" style="width: 93px;" 
                    			 id="packvalue2" class="inp_r"/>
                    </div>
                    <div id="packvalueTip1" class="onError" 
							style="margin: 0px; padding: 0px; background: transparent; display: none">
							<span><span class="onError_top">系统版本长度必须是1~34位之间且不包含小数点，请确认</span><span
								class="onError_bot"></span></span>
						</div>
                    </c:if>
                    
                </div>
            </div>
            <div class="label-form">
                <div class="left-add">系统包类型：</div>
                <div class="right-add u-select">
                	<!--
                    <h5 class="seled gray">请选择</h5>
                    <ul class="select-ul">
                        <li><a href="#" onclick="selectClick(1)">完整包</a></li>
                        <li><a href="#" onclick="selectClick(2)">增量包</a></li>
                    </ul>
                      -->
                    <input type="hidden" class="inp_r"  name="package.type" id="appNameYES" value="${obj.sysPack.type}"/>
                    <select class="inp_r" style="width: 232px; height:30px; line-height:10px; padding:0;" onchange="checkSysPackageModel()" disabled="disabled"  id="packageType">
						<option value="" >请选择</option>
						<option value="1" <c:if test="${obj.sysPack.type==1}">selected</c:if>>完整包</option>
						<option value="2" <c:if test="${obj.sysPack.type==2}">selected</c:if>>增量包</option>
					</select>
                    <div id="packageTypeTip"></div>
                </div>
            </div>
            <div class="label-form">
                <div class="left-add">系统包文件：</div>
                <div class="right-add">
                    <input type="file" name="packFile" class="up-file"  id="packFile" size="28" onchange="document.getElementById('textfield').value=this.value" />
                    <input class="up-left" value="${obj.sysPack.fileName}" name="textfield" id="textfield"/>
                    <span class="up-right">选择文件</span>
<!--                     <button class="up-up">上传</button> -->
                </div>
            </div>
            <div class="label-form" style="position:relative">
                <label class="left-add" for="describe">描述：</label>
                <textarea id="describe" name="package.describes" class="texta-from">${obj.sysPack.describes}</textarea>
                <div id="describeTip"></div>
            </div>
            <div class="label-form">
                <div class="left-add"></div>
                <button class="btn-green single">提交</button>
            </div>
        </form>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>