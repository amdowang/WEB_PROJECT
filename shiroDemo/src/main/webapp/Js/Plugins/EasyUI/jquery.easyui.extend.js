/**
 * 2014-07-02 李博禹
 * This is the method for easyui-datagrid to move or hide the columns extended by lee.
 */
$.extend($.fn.datagrid.methods,{
	columnMoving:function(jq){
		return jq.each(function(){
			var grid = this;
			
			var directionDiv = $("<div></div>");
			
			directionDiv.hide();
			
			$("body").append(directionDiv);
			
			$(grid).datagrid("getPanel")
					.find(".datagrid-header td[field]:not(td[field='ckb'])").draggable({
				revert:true,
				cursor:"move",
				deltaX:10,
				deltaY:10,
				edge:10,
				proxy:function(source){
					var proxyEl = $("<div></div>");
					
					proxyEl.addClass("dg-proxy dg-proxy-error");
					
					proxyEl.text($(source).text());
					
					proxyEl.appendTo($("body"));
					
					return proxyEl;
				}
			}).droppable({
				accept:".datagrid-header td[field]",
				onDragOver:function(e,source){
					$(source).draggable("proxy").removeClass("dg-proxy-error").addClass("dg-proxy-right");
					
					$(".dg-hide-div").hide();
					
					var thisIndex = $(this).index();
					var sourceIndex = $(source).index();
					
					var className = null;
					
					var height = null;
					
					var thisOffset = null;
					
					var left = null;
					var top = null;
					
					height = $(this).height();
					
					if(sourceIndex > thisIndex){
						className = "dg-move-prev";

						thisOffset = $(this).offset();
						
						left = thisOffset.left;
						top = thisOffset.top;
					}else{
						className = "dg-move-next";
						
						if(thisIndex == $(this).parent().children(":last").index()){
							thisOffset = $(this).offset();
							
							left = thisOffset.left + $(this).width() - directionDiv.width();
							top = thisOffset.top;
						}else{
							thisOffset = $(this).next().offset();
							
							left = thisOffset.left - directionDiv.width();
							top = thisOffset.top;
						}
					}
					
					directionDiv.removeClass().addClass(className);
					directionDiv.css({height:height, left:left, top:top});
					directionDiv.show();
				},
				onDragLeave:function(e,source){
					$(source).draggable("proxy").removeClass("dg-proxy-right").addClass("dg-proxy-error");
					
					directionDiv.hide();
				},
				onDrop:function(e,source){
					directionDiv.remove();
					
					var thisIndex = $(this).index();
					var sourceIndex = $(source).index();
					
					var sourceCol = new Array();
					
					$(source).remove();
					$.each($(grid).datagrid("getPanel")
									.find(".datagrid-body tr"),function(index,obj){
						var sourceTd = $(obj).children("td:eq(" + sourceIndex + ")");
						
						sourceCol.push(sourceTd);
						
						sourceTd.remove();
					});
					
					var prev = sourceIndex > thisIndex;
					
					thisIndex = $(this).index();
					
					if(prev){
						$(this).before($(source));
					}else{
						$(this).after($(source));
					}
					
					$.each($(grid).datagrid("getPanel")
									.find(".datagrid-body tr"),function(index,obj){
						var thisTd = $(obj).children("td:eq(" + thisIndex + ")");
						
						if(prev){
							thisTd.before(sourceCol[index]);
						}else{
							thisTd.after(sourceCol[index]);
						}
					});
					
					$(grid).datagrid("columnMoving").datagrid("columnHiding");
				}
			});
		});
	},
	columnHiding:function(jq){
		return jq.each(function(){
			var grid = this;
			
			var variable = 2;
			
			var tds = $(grid).datagrid("getPanel").find(".datagrid-header td[field]:not(td[field='ckb'])");
			
			var downDiv = null;
			
			if($(".dg-hide-div").length == 0){
				downDiv = $("<div></div>");
				downDiv.addClass("dg-hide-div");
				downDiv.hide();
				
				$("body").append(downDiv);
			}else{
				downDiv = $(".dg-hide-div");
			}
			
			downDiv.click(function(){
				tbDiv.show();
			}).mouseout(function(){
				var tbVisible = tbDiv.is(":visible");
				
				if(!tbVisible){
					$(this).hide();
				}
			});
			
			var tbDiv = null;
			
			if($(".dg-hide-tb").length == 0){
				tbDiv = $("<div><table></table></div>");
				tbDiv.addClass("dg-hide-tb");
				tbDiv.hide();
				
				$("body").append(tbDiv);
			}else{
				tbDiv = $(".dg-hide-tb");
				tbDiv.children("table").children().remove();
			}
			
			var trs = "";
			
			var columns = ($(grid).datagrid("options").columns)[0];
			
			$.each(columns,function(index, obj){
				if(index > 0){
					trs += "<tr>";
					
					trs += "<td><input type='checkbox' checked='checked'></td><td id=" + obj.field + ">" + obj.title + "</td>";
					
					trs += "</tr>";
				}
			});
			
			tbDiv.children().append($(trs));
			
			tbDiv.mouseout(function(e){
				var minX = $(this).offset().left;
				
				var curMouseX = e.pageX;
				
				var maxX = $(this).offset().left + $(this).width();
				
				var minY = $(this).offset().top;
				
				var curMouseY = e.pageY;
				
				var maxY = $(this).offset().top + $(this).height();
				
				var ifOverThis = (curMouseX >= minX && curMouseX < maxX) 
									&& (curMouseY >= minY && curMouseY <= maxY);
				
				if(!ifOverThis){
					downDiv.hide();
					$(this).hide();
				}
			});
			
			tbDiv.children().find("input[type='checkbox']").click(function(){
				var checked = $(this).is(":checked");
				
				var visibleTds = $(grid).datagrid("getPanel")
										.find(".datagrid-header td[field]:visible").length - 1;

				if(1 == visibleTds && !checked){
					return false;
				}
				
				var field = $(this).parent().next().attr("id");
				
				if(checked){
					$(grid).datagrid("showColumn", field);
				}else{
					$(grid).datagrid("hideColumn", field);
				}
			});
			
			tds.mouseover(function(){
				tbDiv.hide();
				
				var thisOffset = $(this).offset();
				
				var height = $(this).height();
				
				var left = null;
				
				var visibleTds =  $(this).parent().children(":visible");
				
				var lastTd = visibleTds[visibleTds.length - 1];
				
				if($(this).index() == $(lastTd).index()){
					left = thisOffset.left + variable;
				}else{
					left = thisOffset.left + $(this).width() - downDiv.width() - variable;
				}
				
				var top = thisOffset.top;
				
				downDiv.css({height:height, left:left, top:top});
				tbDiv.css({left:left,top:top+height+1});
				
				downDiv.show();
			}).mouseout(function(e){
				
				var minY = $(this).offset().top;
				
				var curMouseY = e.pageY;
				
				var maxY = $(this).offset().top + $(this).height();
				
				var minX = null;
				
				var curMouseX = e.pageX;
				
				var maxX = null;
				
				var visibleTds =  $(this).parent().children(":visible");
				
				var lastTd = visibleTds[visibleTds.length - 1];
				
				if($(this).index() == $(lastTd).index()){
					minX = $(this).offset().left + variable;
					maxX = $(this).offset().left + downDiv.width() + variable;
				}else{
					minX = $(this).offset().left + $(this).width() - downDiv.width() - variable;
					maxX = $(this).next().offset().left - variable;
				}
				
				var ifOverThis = (curMouseX >= minX && curMouseX <= maxX) 
									&& (curMouseY >= minY && curMouseY <= maxY);
				
				if(!ifOverThis){
					downDiv.hide();
				}
			});
			
		});
	}
});