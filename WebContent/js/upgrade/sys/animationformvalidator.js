function myFormbatch() {
		var v = $("#aniFile").val();
		if(!v) {
			layer.alert("文件不能为空", 0);
			return false;
		}
		
		var str="";
		var model = document.getElementsByName("modelId");
		for(i=0;i<model.length;i++){
			if(model[i].checked==true){
				str += model[i].value + ",";
            }
		}
		if(!str){
			layer.alert("系统型号不能为空", 0);
			return false;
		}else{
			var bool = true ;
			$.ajax({
				type: "post",
			    dataType: "json",
			    async:false,
			    url: basePath +"/upgrade/syspackage/isSyspackager.htm?modelIds="+str,
			    success: function (data) {
					if(data==1){
						layer.alert("所选的系统型号不属于同一厂家", 0);
					 	 bool=  false;
					}		
			    }	
			});		
			
			if(!bool){
				return false ;
			}
			
		}
		
//		var patt1 = new RegExp(/(.*)\.(zip|rar|tgz)$/gi);
//		var result = patt1.test(v);
//		if(!result) {
//			layer.alert("必须是zip,rar,tgz文件", 0);
//			return false;
//		}
		
		$(document).mask(load);
		return true;
}


$(function(){
			$.formValidator.initConfig({formID:"normal",theme:"ArrowSolidBox",submitOnce:true,
				onError:function(msg,obj,errorlist){
					layer.alert(msg, 0);
				}, 
				onSuccess:function(){
					return myFormbatch();
				},
				ajaxPrompt : '有数据正在异步验证，请稍等...'
			});
			$("#aniName").formValidator({
				ajax:true,
				empty : false,
				onShow : "请输入动画名称，不可以为空",
				onFocus : "格式例如：**动画**",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				type : "size",
				onError : "动画名称长度必须是1~34位之间，请确认"
			}).functionValidator({
				fun:function() {
					var length = $("#aniName").val();
					var bool = true;
					if(length.length>34){
						bool=false;
					}
					return bool;
				},
				onError : "动画名称长度必须是1~34位之间，请确认"
			});
			
			$("#describe").formValidator({
				ajax:true,
				empty : false,
				onShow : "请输入描述，不可以为空",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				type : "size",
				onError : "描述1-500字"
			}).functionValidator({
				fun:function() {
					var length = $("#describe").val();
					var bool = true;
					if(length.length>500){
						bool=false;
					}
					return bool;
				},
				onError : "描述1-500字"
			});
});


		