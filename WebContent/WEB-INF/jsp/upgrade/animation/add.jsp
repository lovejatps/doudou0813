<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>动画上传</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
    <script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
	<script src="<c:url value='/js/jquery/jquery.mask.js'/>"></script>
	<script src="<c:url value='/layer/layer.min.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidator-4.1.3.js'/>"></script>
	<script src="<c:url value='/js/formValidator/formValidatorRegex.js'/>"></script>
	<script src="<c:url value='/js/upgrade/sys/animationformvalidator.js'/>"></script>
    <!--[if IE 6]>
    <script src="<c:url value='/js/DD_belatedPNG_0.0.8a.min.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
    
	    
    
		var basePath = "${base}";
		
		var load = "<div align='center' style='' class=> <img alt='loadding...' src='"
			+"<c:url value='/images/bigloading1.jpg'/>"+"'/><span class='load-all'>文件正在上传中，请勿离开此页面！</span></div>";
			
			function vers(){ 
				var longName = document.getElementById("aniFile").value;
				var num = longName.lastIndexOf("\\") ;
				var shortName = longName.substring(num+1);
				if(!/^[a-zA-Z0-9.\-_]*$/g.test(shortName)){
					layer.alert("名称只能出现大小写字母、数字、点、下划线、小横杆,例如A_a-10.zip",0);
				}else{
					document.getElementById('textfield').value=document.getElementById("aniFile").value
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
				document.getElementById("modelIdMassage").innerHTML = "";
				if(str){
					$.ajax({
						type: "post",
					    dataType: "json",
					    async:false,
					    url: "<c:url value='/upgrade/animation/checkAnimation.htm'/>?modelIds="+str,
					    success: function (data) {
							if(data){
								document.getElementById("modelIdMassage").innerHTML = data + "已存在动画包,确认添加将覆盖原动画包";
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
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>动画管理</a></li>
            <li class="bread-home"><a href="javascript:void(0);"><font>&gt;&gt;&nbsp;</font>动画上传</a></li>
        </ul>
    </div>
    <!-- /breadNav -->
    <div class="formBody">
        <form action="<c:url value='/upgrade/animation/doInputAnimation.htm'/>" id="normal" method="post" class="form-add clear" enctype="multipart/form-data">
            <div class="label-form">
                <div class="left-add">系统型号：</div>
                
                	<div class="right-add" style="width: 400px; height:auto;">
                	<c:forEach items="${obj.modelList}" var="z" varStatus="list">
                	<div class="f-left">
					<input id="modelId" onchange="checkAnimation();" class="c_l" 
						<c:if test="${fn:length(obj.modelList)==1}">checked="checked"</c:if>
						type="checkbox" name="modelId" value="${z.id}"/>
						<label class="c_r">${z.name}</label>
					</div>
					</c:forEach>
					</div>
				
				<div style="color: red; clear:both;font-size: 14px; margin-left: 138px;" id="modelIdMassage"></div>
            </div>
            <div class="label-form">
                <label class="left-add" for="aniName">动画名称：</label>
                <div class="right-add">
                    <input type="text" name="ani.name" id="aniName" class="inp_r" onchange="checkAnimation();"/>
                    <div id="aniNameTip"></div>
                </div>
            </div>
            
<%--              <div class="label-form">
                 <label class="left-add">所属厂家：</label>
                 <div class="right-add u-select">
                    <select id="produceid" class="inp_r" style=" width:233px; height:30px; line-height:30px; padding-right: 0" name="package.produceid">
							<option value="" >请选择</option>
							<c:forEach items="${obj.prods}" var="z" varStatus="list">
								<option value="${z.id}" >${z.name}</option>
							</c:forEach>
					</select>
					<div id="produceidTip"></div>
                </div>
            </div> --%>
            
            <div class="label-form">
                <div class="left-add">动画文件：</div>
                <div class="right-add">
                    <input type="file" name="aniFile" class="up-file" id="aniFile" size="28" onchange="vers()">
                    <input class="up-left" name="textfield" id="textfield"/>
                    <span class="up-right">选择文件</span>
<!--                     <span style="position: absolute; bottom: -30px; left: 7px; font-size:12px; color:#999;"><i style="color:red">*</i>&nbsp;&nbsp;格式要求：zip,rar,tgz</span> -->
<!--                     <button class="up-up">上传</button> -->
                </div>
            </div>
            <div class="label-form" style="position:relative;">
                <label class="left-add" for="xtms">描述：</label>
                <textarea id="describe" class="texta-from" name="ani.describes"></textarea>
                <div id="describeTip"></div>
            </div>
            <div class="label-form">
                <div class="left-add"></div>
                <button class="btn-green single" id="sys_add_btn_id007">提交</button>
            </div>
        </form>
    </div>
    <!-- /formBody -->
</div>
</body>
</html>