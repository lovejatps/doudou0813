
$(function(){
		$.formValidator.initConfig({formID:"normal",theme:"ArrowSolidBox",submitOnce:true,
			onError:function(msg,obj,errorlist){
				alert(msg);
			}, 
//			validatorgroup : "1",
			ajaxPrompt : '有数据正在异步验证，请稍等...'
		});
		
		$("#phonevalue").formValidator({
			ajax:true,
			empty : false,
			onShow : "请输入手机号不可以为空哦",
			onFocus : "格式例如：13900000000,010-4244110位编码",
			onCorrect : "谢谢你的合作"
		}).regexValidator({
			regExp : [ "tel", "mobile"],
			dataType : "enum",
			onError : "你输入一键呼叫号码，不能为空"
		}); 
			
});


		