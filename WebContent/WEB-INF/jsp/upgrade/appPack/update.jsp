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
	<script src="<c:url value='/js/jquery/jquery.mask.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/upload/ajaxfileupload.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidator-4.1.3.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
	<script src="<c:url value='/js/upgrade/sys/updateappformvalidator.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
		var basePath = "${base}";
		var boolMod = true;
		var load = "<div align='center' style='' class=> <img alt='loadding...' src='"
			+"<c:url value='/images/bigloading1.jpg'/>"+"'/><span class='load-all'>文件正在上传中，请勿离开此页面！</span></div>";
		
		function checkAppFile(){
			var v = $("#appFile").val();
			var patt1 = new RegExp(/(.*)\.(apk)$/gi);
			var result = patt1.test(v);
			if(!result) {
				layer.alert("请选择apk文件", 0);
			}else{
				var str="";
				var model = document.getElementsByName("modelId");
				for(i=0;i<model.length;i++){
					if(model[i].checked==true){
						str += model[i].value + ",";
		            }
				}
				
				$(document).mask(load);
				
				var version = document.getElementById("appVersion").value;
				var packname = document.getElementById("packName").value;
				$.ajaxFileUpload({
		        	url:"<c:url value='/upgrade/app/doUploadAppFile.htm'/>?packname="+packname+"&version="+version+"&modelIds="+str,
		        	secureuri:false,
		        	fileElementId:'appFile',
//		        	dataType: 'JSON',
		        	success: function (data, status){
		        		data = $(data).find('pre').text();
		        		var obj = jQuery.parseJSON(data);
		        		if(obj.error){
			        		if(obj.error==1){
			        			layer.alert("您上传的应用版本低于线上版本，无法上传！", 0);
			        		}else if(obj.error==2){
			        			layer.alert("您上传的APK包名与此应用其他版本包名不一致，无法上传！", 0);
			        		}
			        	}else{
			        		boolMod = true;
			        		$("#appName").attr("value", obj.appName);
			        		$("#appNameYES").attr("value", obj.appName);
			        		
			        		$("#appVersion").attr("value", obj.versionName);
			        		$("#appVersionYES").attr("value", obj.versionName);
			        		
			        		$("#packName").attr("value", obj.packageName);
			        		$("#packNameYES").attr("value", obj.packageName);
			        		$("#fileId").attr("value", obj.fileId);
			        		
			        		$("#iconId").attr("value", obj.iconId);
			        		$("#up-end").css("display","");
			        		$("#qca").css("display","");
			        		$("#up-up").css("display","none");
			        	}
		        		
		        		$(document).unmask();
		        	},
		        	error: function (data, status, e){
			        	layer.alert("上传失败", 0);
			        	$(document).unmask();
	                }
				}); 
			}
		}

		function checkAppPicture(){
			var v = $("#appPicture").val();
			var patt1 = new RegExp(/(.*)\.(jpg|png)$/gi);
			var result = patt1.test(v);
			if(!result) {
				layer.alert("必需是jpg|png图片", 0);
			}else{
				
				var co = document.getElementById("pictureDiv").getElementsByTagName("div").length;
				if(co>=5){
					layer.alert("应用截图只能上传5张", 0);
					return;
				}
				
				$(document).mask(load);
				
				$.ajaxFileUpload({
		        	url:"<c:url value='/upgrade/app/doUploadAppPicture.htm'/>",
		        	secureuri:false,
		        	fileElementId:'appPicture',
		        	//dataType: 'JSON',
		        	success: function (data, status){
		        		data = $(data).find('pre').text();

		        		var obj = jQuery.parseJSON(data);
			        	if(obj.error){
			        		if(obj.error==1){
			        			layer.alert("应用截图尺寸必需大于800*480px", 0);
			        		}else if(obj.error==2){
			        			layer.alert("应用截图容量大小必需小于2MB", 0);
			        		}
			        	}else{
			        		//$("pictureFaDiv").attr("display","");
			        		document.getElementById("pictureFaDiv").style.display="";
			        		$("#pictureDiv").append("<div style='position: relative; float:left;'  id='divCross_"+ obj.picFileId+ 
			        				"'><a href='javascript:;' onclick='crossThisDiv(divCross_" + obj.picFileId + 
			        				");' style='position: absolute; top:-12px; right:-12px;'><img style='border:none;' src='<c:url value='/images/icon/cross.png'/>' /></a>"+
			        				"<input type='hidden' value='"+obj.picFileId+"' name='pictureFileId' />" +  
			        				"<img src='<c:url value="/upgrade/doDownLoad.htm"/>?fileId="+ obj.picFileId+"' height='130'></img>");
			        	}
		        		$(document).unmask();
		        	},
		        	error: function (data, status, e){
			        	layer.alert("上传失败", 0);
			        	$(document).unmask();
	                }
				});
			}
		}
		
		function crossThisDiv(divId){
			$(divId).remove();
			if($("#pictureDiv").html().trim()==""){
				document.getElementById("pictureFaDiv").style.display="none";
			}
		}
		
		function checkAppModel(obj){
			var str="";
			var model = document.getElementsByName("modelId");
			for(i=0;i<model.length;i++){
				if(model[i].checked==true){
					str += model[i].value + ",";
	            }
			}

			var version = document.getElementById("appVersion").value;
			var packname = document.getElementById("packName").value;
			if(str && version && packname){
				$.ajax({
					type: "post",
				    dataType: "json",
				    async:false,
				    url: "<c:url value='/upgrade/app/checkAppModel.htm'/>?modelIds="+str+"&version="+version+"&packName="+packname,
				    success: function (data) {
						if(data==1){
							boolMod = false;
							layer.alert("系统已存在高版本包，无法上传！", 0);
							obj.checked="";
						}else{
							boolMod = true;
						}
				    }
				});
			}
		}
		
		function checkFileInput(obj){
			var str = obj.value;
			var arr = str.split("\\");
			if(arr!=null && arr.length==3 && arr[2]!=null){
				$("#textfield").val(arr[2]);
			}else{
				$("#textfield").val(str.substr(str.lastIndexOf("\\")+1));
			}
		}
		
		function removeUpEnd(){
			$("#up-end").css("display","none");
    		$("#qca").css("display","none");
    		$("#up-up").css("display","");
		}
		
	</script>
</head>
<body>
<div class="container">
    <div class="breadNav">
        <ul class="clear">
            <li class="bread-home"><a href="javascript:void(0);">升级管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>APP管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>APP编辑</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
         <div class="form-add clear">
            			
            <div class="label-form" style="padding-bottom:20px;">
                <div class="left-add">应用安装包：</div>
                <div class="right-add">
                    <input type="file" style="width: 72px;" name="appFile" class="up-file" id="appFile" size="28" onchange="checkAppFile()">
<!--                     <input class="up-left" name="textfield" id="textfield"/> -->
<!--                     <span class="up-right">选择文件</span> -->
                    <button type="button"  class="up-up" id="up-up" style="display: none">上传</button>
                    <button type="button"  class="up-up" id="up-end" style="position:absolute; left:10px; top:0; background:#f1f1f1; color:#ccc; ">已上传</button>
                    <a href="#" style="font-size:12px; margin-left:88px; color:red; " id="qca" onclick="removeUpEnd();">清除</a>
                    <span style="position: absolute; bottom: -30px; font-size:12px; color:#999; display:block; width:500px;"><i style="color:red">*</i>&nbsp;&nbsp;格式要求：apk</span>
                </div>
            </div>
            
           <form action="<c:url value='/upgrade/appPack/doUpdateApp.htm'/>" id="normal" method="post"   enctype="multipart/form-data">
            <input name="app.id" type="hidden" value="${obj.app.id}" />
        	<input name="app.deleted" type="hidden" value="${obj.app.deleted}" />
            <div class="label-form">
                <div class="left-add">系统型号：</div>
                	<div class="right-add"  style="width: 400px; height:auto;">
               			<c:forEach items="${obj.sysMod}" var="z" varStatus="list">
               			<div class="f-left">
					<input id="modelId"  class="c_l" type="checkbox" onchange="checkAppModel(this);"
					<c:forEach items="${obj.appSys}" var="m" varStatus="mlist">
						<c:if test="${m.sysModelId==z.id}">checked="checked"</c:if>
					</c:forEach>
					  name="modelId" value="${z.id}"/>
						<label class="c_r">${z.name}</label>
						</div>
						</c:forEach>
					</div>
            </div>
            <div class="label-form">
                <label class="left-add" for="appName">应用名称：</label>
                <div class="right-add">
                	<input type="hidden" name="app.appFileId" value="${obj.app.appFileId}" id="fileId"/>
                	<input type="hidden" name="app.iconId" value="${obj.app.iconId}" id="iconId"/>
                	<input type="hidden" class="inp_r" value="${obj.app.appName}" name="app.appName" id="appNameYES"/>
                    <input type="text" disabled="disabled" class="inp_r" value="${obj.app.appName}" name="appName" id="appName"/>
                    <div class="tip_right">自动从APK中解析，不用填写</div>
                </div>
            </div>
            <div class="label-form">
                <label class="left-add" for="bbh">版本号：</label>
                <div class="right-add">
                	<input type="hidden" name="app.appVersion" value="${obj.app.appVersion}" id="appVersionYES" class="inp_r"/>
                    <input type="text" disabled="disabled" name="appVersion" value="${obj.app.appVersion}" id="appVersion" class="inp_r"/>
                    <div class="tip_right">自动从APK中解析，不用填写</div>
                </div>
            </div>
            <div class="label-form">
                <label class="left-add" for="bbh">包名：</label>
                <div class="right-add">
                	<input type="hidden" name="app.packName" value="${obj.app.packName}" id="packNameYES" class="inp_r"/>
                    <input type="text" disabled="disabled" name="packName" value="${obj.app.packName}" id="packName" class="inp_r"/>
                    <div class="tip_right">自动从APK中解析，不用填写</div>
                </div>
            </div>
            <div class="label-form">
                <div class="left-add">应用分类：</div>
                <div class="right-add u-select">
 <!--                   <h5 class="seled gray">请选择</h5>
                    <ul class="select-ul">
                        <li><a href="#">车载</a></li>
                        <li><a href="#">软件</a></li>
                        <li><a href="#">分类</a></li>
                     </ul> -->
                    <select class="inp_r" style="width: 234px; height:30px; line-heigth:30px;padding-right: 0" name="app.appClassify" id="appClassify">
						<option value="" >请选择</option>
						<option value="1" <c:if test="${obj.app.appClassify==1}">selected</c:if>>车载 </option>
						<option value="2" <c:if test="${obj.app.appClassify==2}">selected</c:if>>软件</option>
						<option value="3" <c:if test="${obj.app.appClassify==3}">selected</c:if>>游戏</option>
					</select>
					<div id="appClassifyTip"></div>
                </div>
            </div>
            <div class="label-form" style="padding-bottom:10px; height:75px;">
                <div class="left-add">应用截图：</div>
                <div class="right-add">
                    <input type="file" name="appPicture" class="up-file" onchange="checkAppPicture()"  id="appPicture" size="28" onchange="document.getElementById('textfield').value=this.value">
                    <input class="up-left" name="textfield" id="textfield"/>
                    <span class="up-right">选择文件</span>     
                    <span style="position: absolute; bottom: -70px; left: 7px; font-size:12px; color:#999; width:467px;"><i style="color:red">*</i>
                    &nbsp;&nbsp;格式要求：请上传5张JPG或PNG格式的截图(尺寸保持一致)，尺寸大于800*480px，容量小于2MB，请去除图中的顶部通知栏。将显示在应用详情页中。</span>      
					<!-- <button class="up-up">上传</button> -->
                </div>
            </div>
            
            <div class="label-form right-addpic" id="pictureFaDiv" style="width: 800px;">
           			<div class="left-add">&nbsp;</div>
            	    <div class="right-add" id="pictureDiv" style="width: 650px;">
            	    	<c:forEach items="${obj.appPic}" var="p" varStatus="plist">
            	    		<div style="position: relative; float:left;"  id="divCross_${p.fileId}">
            	    		<a href="javascript:;" onclick="crossThisDiv(divCross_${p.fileId});" style="position: absolute; top:-12px; right:-12px;"><img style="border:none;" src="<c:url value='/images/icon/cross.png'/>" /></a>
            	    		<input type="hidden" value="${p.fileId}" name="pictureFileId" />
            	    		<img src="<c:url value='/upgrade/doDownLoad.htm'/>?fileId=${p.fileId}"  height='130'></img>
            	    		</div>
            	    	</c:forEach>
            	    </div>
            </div>
            
            <div class="label-form">
                <div class="left-add">是否默认升级：</div>
                <div class="right-add">
                    <input type="radio" name="app.upgrade" class="c_l" value="0" <c:if test="${obj.app.upgrade=='YES'}">checked="checked"</c:if> />
                    <label class="c_r">YES</label>
                </div>
                <div class="right-add">
                    <input type="radio" name="app.upgrade" class="c_l" value="1" <c:if test="${obj.app.upgrade=='NO'}">checked="checked"</c:if> />
                    <label class="c_r">NO</label>
                </div>
            </div>
            <div class="label-form">
                <div class="left-add">推荐指数：</div>
               <div class="right-add u-select">
                <select class="inp_r" style="width: 233px; height:30px; line-heigth:30px; padding-right: 0" name="app.recommendIndex" id="appRecommend">
						<option value="" >请选择</option>
						<option value="1" <c:if test="${obj.app.recommendIndex==1}">selected</c:if>>1 </option>
						<option value="2" <c:if test="${obj.app.recommendIndex==2}">selected</c:if>>2</option>
						<option value="3" <c:if test="${obj.app.recommendIndex==3}">selected</c:if>>3</option>
						<option value="4" <c:if test="${obj.app.recommendIndex==4}">selected</c:if>>4</option>
						<option value="5" <c:if test="${obj.app.recommendIndex==5}">selected</c:if>>5</option>
					</select>
					<div id="appRecommendTip"></div>
                </div>
            </div>
            <div class="label-form" style="position: relative;">
                <label class="left-add" for="describe">应用描述：</label>
                <textarea name="app.describes" id="describe" class="texta-from" style="height:100px;">${obj.app.describes}</textarea>
                <div id="describeTip"></div>
            </div>
            <div class="label-form" style="position: relative;">
                <label class="left-add" for="detail">更新描述：</label>
                <textarea name="app.detail" id="detail" class="texta-from">${obj.app.detail}</textarea>
            </div>
            <div class="label-form">
                <div class="left-add"></div>
                <button class="btn-green single">提交</button>
            </div>
        </form>
        </div>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>