
$(function(){
		$.formValidator.initConfig({formID:"normal",theme:"ArrowSolidBox",submitOnce:true,
			onError:function(msg,obj,errorlist){
				layer.alert(msg, 0);
			}, 
//			validatorgroup : "1",
			ajaxPrompt : '有数据正在异步验证，请稍等...'
		});
		
		$("#loginName").formValidator({
			ajax:true,
			empty : false,
			onShow : "请输入你的用户名不可以为空哦",
			onFocus : "格式例如：zhangsan_521，3~20位编码",
			onCorrect : "谢谢你的合作"
		}).inputValidator({
			min : 3,
			max : 20,
			type : "size",
			onError : "用户名长度必须是3~20位，请确认"
		}).regexValidator({
			regExp : [ "username" ],
			dataType : "enum",
			onError : "用户名由字母数字下划线组成"
		}).ajaxValidator({
			dataType : "json",
			async : true,
			type : "post",
			url : basePath + "/buzuser/validatorLoginName.htm",
			success : function(data){
	            if(data=="1") {//存在
	            	return false;
	            }
				return true;
			},
			buttons: $("#normal4button"),
			error: function(jqXHR, textStatus, errorThrown){layer.alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown, 0);},
			onError : "该用户名已经存在",
			onWait : "正在进行合法性校验，请稍候..."
		}); 
		
		$("#roelId").formValidator({
			ajax:true,
			empty : false,
			onShow : "请选择用户类型",
			onFocus : "请选择用户类型",
			onCorrect : "谢谢你的合作"
		}).inputValidator({
			min : 1,
			type : "size",
			onError : "请选择用户类型"
		});
		
		$("#produceid").formValidator({
			ajax:true,
			empty : false,
			onShow : "请选择所属厂家",
			onFocus : "请选择所属厂家",
			onCorrect : "谢谢你的合作"
		}).inputValidator({
			min : 1,
			type : "size",
			onError : "请选择所属厂家"
		});

		$("#password1").formValidator({
			onShow : "请输入密码",
			onFocus : "至少1个长度",
			onCorrect : "密码合法"
		}).inputValidator({
			min : 1,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "密码两边不能有空符号"
			},
			onError : "密码不能为空,请确认"
		});
		$("#password2").formValidator({
			onShow : "输再次输入密码",
			onFocus : "至少1个长度",
			onCorrect : "密码一致"
		}).inputValidator({
			min : 1,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "确认密码两边不能有空符号"
			},
			onError : "确认密码不能为空,请确认"
		}).compareValidator({
			desID : "password1",
			operateor : "=",
			onError : "2次密码不一致,请确认"
		});
			
});


		