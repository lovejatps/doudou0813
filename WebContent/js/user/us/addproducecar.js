
$(function(){
		$.formValidator.initConfig({formID:"normal",theme:"ArrowSolidBox",submitOnce:true,
			onError:function(msg,obj,errorlist){
				layer.alert(msg, 0);
			}, 
			ajaxPrompt : '有数据正在异步验证，请稍等...'
		});
		
		$("#prodName").formValidator({
			ajax:true,
			empty : false,
			onShow : "请输入你的厂家不可以为空哦",
			onFocus : "格式例如：某某车厂，1~20汉字",
			onCorrect : "谢谢你的合作"
		}).inputValidator({
			min : 2,
			max : 40,
			type : "size",
			onError : "厂家名长度必须是1~20汉字，请确认"
		}).functionValidator({
			fun:function() {
				var prodName = $("#prodName").val();
			//	var loginName = encodeURIComponent(encodeURIComponent($("#loginName").val()));
				var bool = true;
				$.ajax({
					type:"post",
					async : false,
					dataType :"json",
					url : basePath + "/rights/validatorUpdateprodName.htm?prodName="+prodName,
					success:function(data) {
						if(data==1) {
							//存在
			            	bool = false;
			            }
					}
				});
				return bool;
			},
			onError : "该厂家已经存在"
		});
		
		$("#prodaddress").formValidator({
			leftEmpty : false,
			rightEmpty : false,
			onShow : "请输入你的厂家地址可以为空哦",
			onFocus : "格式例如：某某路XX号",
			onCorrect : "谢谢你的合作"
		});
		
		$("#prodaddress").formValidator({
			ajax:true,
			empty : false,
			onShow : "请输入你的厂家地址可以为空哦",
			onFocus : "格式例如：某某路XX号",
			onCorrect : "谢谢你的合作"
		}).inputValidator({
			min : 2,
			max : 200,
			type : "size",
			onError : "厂家地址长度必须是1~100汉字，请确认"
		})
			
});


		