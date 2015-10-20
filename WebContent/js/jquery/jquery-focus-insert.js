
		(function($) {
			$.fn.textFocus = function(v) {
				var range, len, v = v === undefined ? 0 : parseInt(v);
				this.each(function() {
					if ($.browser.msie) {
						range = this.createTextRange();
						v === 0 ? range.collapse(false) : range.move(
								"character", v);
						range.select();
					} else {
						len = this.value.length;
						v === 0 ? this.setSelectionRange(len, len) : this
								.setSelectionRange(v, v);
					}
					this.focus();
				});
				return this;
			}
		})(jQuery);

		(function($) {
			$.fn.extend({
				"insert" : function(value) {
					//默认参数 
					value = $.extend({
						"text" : "\n"
					}, value);
					var dthis = $(this)[0]; //将jQuery对象转换为DOM元素 
					//IE下 
					if (document.selection) {
						$(dthis).focus(); //输入元素textara获取焦点 
						var fus = document.selection.createRange();//获取光标位置 
						fus.text = value.text; //在光标位置插入值 
						$(dthis).focus(); ///输入元素textara获取焦点 
					}
					//火狐下标准 
					else if (dthis.selectionStart || dthis.selectionStart == '0') {
						var start = dthis.selectionStart; //获取焦点前坐标 
						var end = dthis.selectionEnd;//获取焦点后坐标 
						//以下这句，应该是在焦点之前，和焦点之后的位置，中间插入我们传入的值 .然后把这个得到的新值，赋给文本框 
						dthis.value = dthis.value.substring(0, start) + value.text + dthis.value.substring(end, dthis.value.length);
					}
					//在输入元素textara没有定位光标的情况 
					else {
						this.value += value.text;
						this.focus();
					}
					;
					return $(this);
				}
			})
		})(jQuery);

		$(document).ready(function() {
			$(".texta-from").keydown(function() {
				var tt = $(this);
				if (event.keyCode == 13) {
					tt.insert();
				}
				;
			});
		});
		