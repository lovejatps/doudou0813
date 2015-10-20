<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<c:url value='/js/jquery/jquery-jtemplates.js'/>"></script>
<!DOCTYPE html>
<script type="text/javascript">
	var basePath= "${base}";
	var pageSize = 10;
	
	/**
	*  queryMap 在action中有成员属性及get、set方法
	* 按照标签上name=‘queryMap.***’ 取查询条件,为空则不传参(一定要安规范才能取得到参数值)
	*/
	function getQueryMap(){
		var url = "";
		$(".searchBar").find("select[name^=query]").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});
		$(".searchBar").find("input[type=text][name^=query]").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});
		
		$("body").find("input[type=hidden][name^=query]").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});
		
		$(".searchBar").find("input[type=radio][name^=query]:checked").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});  
		
		$(".searchBar").find("input[type=checkbox][name^=query]:checked").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});
		
		//==============================
		$(".searchBar").find("select[name^=spec]").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});
		$(".searchBar").find("input[type=text][name^=spec]").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});
		
		$("body").find("input[type=hidden][name^=spec]").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});
		
		$(".searchBar").find("input[type=checkbox][name^=spec]:checked").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		});
		
		$(".searchBar").find("input[type=radio][name^=spec]:checked").each(function(){
			var key = $(this).attr("name");	
			var val = $(this).val();
			if(val!=""&&val!=null){
				val = encodeURIComponent(val);
				url += "&" + key + "=" + val;
			}
		}); 
		//url = url.replace(/\+/g,"|");
		//url = encodeURI(url);
		return url;
	}
	
		/**
		* 根据用户输入跳转到第n页面,showDeafaultTable方法必须在自己的页面上写上
		*/
		function jumpPage(data){
// 			$('.xbutton').live('click', function() {
				var $page = $(data).parent().parent().find("input:first");
				var pageNo = parseInt($page.val());
				if(isNaN(pageNo)){
					pageNo = 1;
					$page.val(pageNo);
				}
				var totalPageNo = $(data).attr("title");
				if(pageNo<=0) pageNo = 1;
				if(pageNo>totalPageNo) pageNo = totalPageNo;
				showDeafaultTable(pageNo,pageSize);//此方法在自己的页面要有
				//return false;
// 			});
		}
		/**
		* 根据用户输入跳转到第n页面,showDeafaultTable方法必须在自己的页面上写上
		*/
		function selectPageSize(select){
			/*
			pageSize=$('#select_page_line').val();
			$("#select_page_line").children("value=["+pageSize+"]").attr("selected", "selected");
			//.attr("selected", "selected");
			*/
			pageSize = $(select).val();
			showDeafaultTable(1,pageSize);
		}

		function afterSuccess(){
			
		}
		
		/**
		* pageNo 第几页
		* pageSize 页面记录条数
		* url 获取table json 的链接请求，不带查询条件
		* pageMsgTemplateId 分页信息所放的div ID
		* tableTemplateId 生成的表放在哪div中(div ID)
		* tableTemplateTxtId 表的模版
		*/
		function showTemplateTable(pageNo,pageSize,url,pageMsgTemplateId,tableTemplateId,tableTemplateTxtId){
			url += "&pageNo=" + pageNo;
			url += "&pageSize=" + pageSize;//正常使用
// 			url += "&pageSize=" + 2;//测试用,
			var load = "<div align='center' style='height:220px;margin-top:100px;' class=><img alt='loadding...' src='"
			+"<c:url value='/images/bigloading.gif'/>"+"'/></div>";
			
			$("#" + tableTemplateId).empty().html(load);
			$.ajax({
				url : url,
				type : "post",
				data : getQueryMap(),
				dataType:"json",
				success : function(page){
					//支持多个分页div
					var pageDiv=pageMsgTemplateId.split(",");
					$(pageDiv).each(function(i){
						$('#'+this).setTemplateElement("pageMsgTemplate");
						$('#'+this).processTemplate(page);
					});
					
					$("#" + tableTemplateId).setTemplateElement(tableTemplateTxtId);
					$("#" + tableTemplateId).processTemplate(page);
					//表格鼠标滑过行变色；奇偶行色不同
					$(".content_tab0 tr").mouseover(function(){$(this).addClass("over");}).mouseout(function(){$(this).removeClass("over");}); //行变色
					$(".content_tab0 tr:odd").css("background-color","#fff");
					$(".content_tab0 tr:even").css("background-color","#F5F5F5");

					afterSuccess();
				},
				error : function() {
					window.close();
				}
			});
			
		}
		
		/**
		*单独应用一个table 后台传一个list json
		* url 获取table json 的链接请求，不带查询条件
		* tableTemplateId 生成的表放在哪div中(div ID)
		* tableTemplateTxtId 表的模版
		*/
		function showTemplateTableList(url,tableTemplateId,tableTemplateTxtId){
			var load = "<div align='center' style='height:220px;margin-top:100px;'><img alt='loadding...' src='"
			+"<c:url value='/js/liger/ligerUI/skins/Aqua/images/common/bigloading.gif'/>"+"'/></div>";
			
			$("#" + tableTemplateId).empty().html(load);
			$.ajax({
				url : url,
				type : "post",
				data : getQueryMap(),
				success : function(list){
					$("#" + tableTemplateId).setTemplateElement(tableTemplateTxtId);
					$("#" + tableTemplateId).processTemplate(list);
					//表格鼠标滑过行变色；奇偶行色不同
// 					$(".content_tab0 tr").mouseover(function(){$(this).addClass("over");}).mouseout(function(){$(this).removeClass("over");}); //行变色
// 					$(".content_tab0 tr:odd").css("background-color","#fff");
// 					$(".content_tab0 tr:even").css("background-color","#F5F5F5");
					if(list==null || (list && list.length==0)) $("#" + tableTemplateId).append('<div align="center" > <b sytle="color:red;">暂无数据</b></div>');
					afterSuccess();
				},
				error : function() {
					window.close();
				}
			});
			
		}
			
</script>

<!-- 表分页信息模版 -->
<textarea id="pageMsgTemplate" style="display:none">
	<![CDATA[
		<div class="link-main modular2">
			<div class="FL">
			   
				<div class="bar_btn">
					{#if $T.pager.pageNumber>1}
						<a class="left-first" href="javascript:showDeafaultTable(1,{$T.pager.pageSize});">首页</a>
					{#else}{* 已经是第一页*}
						<a class="left-first2" href="javascript:void(0);">首页</a>
					{#/if}
				</div>
				<div class="bar_btn">
					{#if $T.pager.pageNumber>1}
						<a class="pre-left" href="javascript:showDeafaultTable({$T.pager.pageNumber-1},{$T.pager.pageSize});">上一页</a>
					{#else}{* 没有上一页*}
						<a class="pre-left2" href="javascript:void(0);">上一页</a>
					{#/if}
				</div>
				<div class="FL"><input type="text" value="{$T.pager.pageNumber}"/>/{$T.pager.pageCount}</div>
				<div class="bar_btn">
					{#if $T.pager.pageNumber<$T.pager.pageCount}
						<a class="next-right" href="javascript:showDeafaultTable({$T.pager.pageNumber+1},{$T.pager.pageSize});">下一页</a>
					{#else}{* 没有下一页*}
						<a class="next-right2" href="javascript:void(0);">下一页</a>
					{#/if}
				</div>
				<div class="bar_btn">
					{#if $T.pager.pageNumber<$T.pager.pageCount}
						<a class="right-last" href="javascript:showDeafaultTable({$T.pager.pageCount},{$T.pager.pageSize});">尾页</a>
					{#else}{* 已经是最后一页*}
						<a class="right-last2" href="javascript:void(0);">尾页</a>
					{#/if}
				</div>
				<div class="bar_btn"><a class="page_load" title="{$T.pager.pageCount}" onclick="jumpPage(this);" href="javascript:void(0);">GO</a></div>
				{*
				<div class="bar_btn"><a class="page_load" href="javascript:showDeafaultTable({$T.pager.pageNumber},{$T.pager.pageSize});">go</a></div>
				*}
			</div>
			
			<span class="FR" style="*white-space:nowrap;">共 {$T.pager.recordCount} 条</span>
		</div>
	]]>
</textarea>

