$(function(){
			$.formValidator.initConfig({formID:"normal",theme:"ArrowSolidBox",submitOnce:true,
				onError:function(msg,obj,errorlist){
					layer.alert(msg, 0);
				}, 
				ajaxPrompt : '有数据正在异步验证，请稍等...'
			});
			
			$("#produceid").formValidator({
				ajax:true,
				empty : false,
				onShow : "请选系统固件型号所属的厂家",
				onFocus : "请选系统固件型号所属的厂家",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				type : "size",
				onError : "请选固件型号所属的厂家"
			});
			
			$("#firmvalue").formValidator({
				ajax:true,
				empty : false,
				onShow : "请输入你的固件型号不可以为空哦",
				onFocus : "格式例如：DD09-2014-PL3 ",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				type : "size",
				onError : "固件型号长度必须是1~34位之间，请确认"
			}).functionValidator({
				fun:function() {
					var firmvalue = encodeURIComponent(encodeURIComponent($("#firmvalue").val()));
					var bool = true;
					$.ajax({
						type:"post",
						async : false,
						dataType :"json",
						url : basePath + "/upgrade/firm/validatorFirm.htm?firm.name="+firmvalue,
						success:function(data) {
							if(data==1) {
								//存在
				            	bool = false;
				            }
						}
					});
					return bool;
				},
				onError : "该固件型号已经存在"
			}).functionValidator({
				fun:function() {
					var length = $("#firmvalue").val();
					var bool = true;
					if(length.length>34){
						bool=false;
					}
					return bool;
				},
				onError : "固件型号长度必须是1~34位之间，请确认"
			});
			
			/*.ajaxValidator({
				
				dataType : "json",
				async : true,
				type : "post",
				url : basePath + "/upgrade/sys/validatorSys.htm",
				success : function(data){
		            if(data=="1") {//存在
		            	return false;
		            }
					return true;
				},
				buttons: $("#normal4button"),
				error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},
				onError : "该系统型号已经存在",
				onWait : "正在进行合法性校验，请稍候..."
			}); */

});


		