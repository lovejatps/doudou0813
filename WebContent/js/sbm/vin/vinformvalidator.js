
var myForm = {
	batch : function() {
		var v = $("#vinFile").val();
		$(":submit,:button,:reset").attr("disabled",true);
		if(!v) {
			layer.alert("文件不能为空", 0);
			$(":submit,:button,:reset").attr("disabled",false);
			return false;
		}
		
		var patt1 = new RegExp(/(.*)\.(txt)$/gi);
		var result = patt1.test(v);
		if(!result) {
			layer.alert("必需是txt文件", 0);
			$(":submit,:button,:reset").attr("disabled",false);
			return false;
		}
		
		$(document).mask(load);
		return true;
	}
};


$(function(){
			$.formValidator.initConfig({formID:"normal",theme:"ArrowSolidBox",submitOnce:true,
				onError:function(msg,obj,errorlist){
					layer.alert(msg, 0);
				}, 
// 				validatorgroup : "1",
				ajaxPrompt : '有数据正在异步验证，请稍等...'
			});
			
			$("#vinvalue").formValidator({
				ajax:true,
				empty : false,
				onShow : "请输入你的VIN不可以为空哦",
				onFocus : "格式例如：LSVFA49J*7048，17位编码",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 17,
				max : 17,
				type : "size",
				onError : "VIN长度必须是17位，请确认"
			}).regexValidator({
				regExp : [ "username" ],
				dataType : "enum",
				onError : "你输入的VIN格式不正确,字母数字组成"
			}).functionValidator({
				fun:function() {
					var vinName = encodeURIComponent(encodeURIComponent($("#vinvalue").val()));
					var bool = true;
					$.ajax({
						type:"post",
						async : false,
						dataType :"json",
						url : basePath + "/buzvin/validatorVin_17.htm?vinName="+vinName,
						success:function(data) {
							if(data=="0") {
								//vin错误
				            	bool = false;
				            }
						}
					});
					return bool;
				},
				onError : "输入的VIN码错误"
			}).ajaxValidator({
				dataType : "json",
				async : true,
				type : "post",
				url : basePath + "/buzvin/validatorVIN.htm",
				success : function(data){
		            if(data=="1") {//存在
		            	return false;
		            }
					return true;
				},
				buttons: $("#normal4button"),
				error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},
				onError : "该VIN已经存在",
				onWait : "正在进行合法性校验，请稍候..."
			});
			
			/* $.formValidator.initConfig({formID:"batch",theme:"ArrowSolidBox",submitOnce:true,
				onError:function(msg,obj,errorlist){
					alert(msg);
				}, 
// 				validatorgroup : "2",
				ajaxPrompt : '有数据正在异步验证，请稍等...'
			});
			
			$("#idFile").formValidator({
				empty : true,
				onShow : "请选择txt文件",
				onFocus : "请选择txt文件",
				onCorrect : "谢谢你的合作"
			}).inputValidator({
				min : 1,
				max : 34,
				type : "size",
				onError : "请选择txt文件"
			}).regexValidator({
				regExp : [ "txt"],
				dataType : "enum",
				onError : "请选择txt文件"
			}); */
});


		