function myFormbatch() {
		var d = $("#detail").val();
		if(d.length>500){
			layer.alert("详细描述在500字以内！", 0);
			return false;
		}
	
		var bool = false;
		var model = document.getElementsByName("modelId");
		for(i=0;i<model.length;i++){
			if(model[i].checked==true){
				bool=true;
            }
		}
		if(!bool){
			layer.alert("请选择适配车型！", 0);
			return false;
		}
		
		var img = $("#pictureDiv").html();
		
		if(!img.trim()){
			layer.alert("请上传应用截图！", 0);
			return false;
		}
		
		if(!boolMod){
			layer.alert("系统已存在高版本包，无法上传！", 0);
			return false;
		}
		
		var co = document.getElementById("pictureDiv").getElementsByTagName("div").length;
		
		if(co != 5){
			layer.alert("应用截图必需上传5张", 0);
			return false;
		}
		
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
			
			$("#appClassify").formValidator({
				ajax:true,
				empty : false,
				onShow : "请选择应用分类",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				type : "size",
				onError : "请选择应用分类"
			});
			
			$("#appRecommend").formValidator({
				ajax:true,
				empty : false,
				onShow : "请选择推荐指数",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				type : "size",
				onError : "请选择推荐指数"
			});
			
			$("#describe").formValidator({
				ajax:true,
				empty : false,
				onShow : "请输入应用描述，不可以为空",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				type : "size",
				onError : "应用描述50-1000字"
			}).functionValidator({
				fun:function() {
					var length = $("#describe").val();
					var bool = true;
					if(length.length>1000 || length.length<50){
						bool=false;
					}
					return bool;
				},
				onError : "应用描述50-1000字"
			});
			
});


		