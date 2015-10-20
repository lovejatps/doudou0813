function updateFormbatch() {
		var v = $("#packFile").val();
		
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
		}
//		if(v) {
//			var patt1 = new RegExp(/(.*)\.(zip|rar|tgz)$/gi);
//			var result = patt1.test(v);
//			if(!result) {
//				layer.alert("必需是zip,rar,tgz文件", 0);
//				return false;
//			}
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
					return updateFormbatch();
				},
				ajaxPrompt : '有数据正在异步验证，请稍等...'
			});
			$("#packvalue").formValidator({
				ajax:true,
				empty : false,
				onShow : "请输入你的系统版本不可以为空哦",
				onFocus : "格式例如：V101 ",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				type : "size",
				onError : "系统版本长度必须是1~34位之间且不包含小数点，请确认"
			}).functionValidator({
				fun:function() {
					var length = $("#packvalue").val();
					var patt = new RegExp(/(.*)\.(.*)$/);
					var pat = new RegExp(/.*[\u4e00-\u9fa5]+.*$/);
					var china = pat.test(length);
					var result = patt.test(length);
					var bool = true;
					if(length.length>34 || china || result){
						bool=false;
					}
					return bool;
				},
				onError : "系统版本长度必须是1~34位之间且不包含小数点，请确认"
			});
			
			$("#packageType").formValidator({
				ajax:true,
				empty : false,
				onShow : "请选择系统包类型",
				onFocus : "请选择系统包类型",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				type : "size",
				onError : "请选择系统包类型"
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


		