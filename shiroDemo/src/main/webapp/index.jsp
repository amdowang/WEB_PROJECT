<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String basePath = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>泥头车GPS监管系统</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/Css/index.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/Css/report.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/Js/Plugins/EasyUI/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/Js/Plugins/EasyUI/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/Js/Plugins/EasyUI/themes/extend.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/Js/Plugins/My97DatePicker/skin/WdatePicker.css">
<script type="text/javascript" src="<%=basePath%>/Js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/Js/Plugins/EasyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/Js/Plugins/EasyUI/datagrid-detailview.js"></script>
<script type="text/javascript" src="<%=basePath%>/Js/Plugins/EasyUI/jquery.easyui.extend.js"></script>
<script type="text/javascript" src="<%=basePath%>/Js/Plugins/EasyUI/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/Js/Plugins/My97DatePicker/WdatePicker.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:113px;">
		<div class="title">
			<div class="title-common-layer">
				<div class="title-info-box">
					<span>部门：aaa</span>
					<span>用户：aaa</span>
				</div>
			</div>

			<div class="title-common-layer">
				<div class="title-time-box">
					<span>
						<input id="styleBox" class="easyui-combobox">
					</span>
					<span></span>
				</div>
		
				<div class="title-btn-box">
					<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-help'" style="margin-top: 1px; width: 60px;">帮助</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" style="margin-top: 1px; width: 80px;">修改密码</a> -->
					<a href="javascript:void(0)" id="index-logout" class="easyui-linkbutton" data-options="iconCls:'icon-logout'" style="margin-top: 3px; width: 60px;">注销</a>
				</div>
			</div>
		</div>
	</div>
		
	<div title="功能菜单" data-options="region:'west',split:true" style="width:245px;">
		<div class="easyui-accordion" data-options="border:false,fit:true,selected:-1">
			<div title="系统管理" data-options="iconCls:'icon-expert'">
				<ul class="menu-box">
					<li>
						<div>
							<a href="javascript:void(0)" name="system/user_toList.action">
								<span>用户管理1</span>
							</a>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'" style="width:100%;">
		<div id="index-tab" class="easyui-tabs" data-options="border:false,fit:true">
			<div title="首页" style="background-color: #FFFFFF">
				<img src="<%=basePath%>/Images/welcome.jpg">
			</div>
		</div>
	</div>
	
	<div id="index-menu" class="easyui-menu" data-options="onShow:menuOnShowHandler" style="width:160px;">
		<div id="index-menu-closeSelf">关闭标签页</div>
		<div id="index-menu-closeOthers">关闭其他标签页</div>
		<div id="index-menu-closeLeft">关闭左侧标签页</div>
		<div id="index-menu-closeRight">关闭右侧标签页</div>
		<div id="index-menu-closeAll">关闭所有标签页</div>
	</div>
	
	<div id="common-dialog" class="easyui-dialog"></div>
	<div id="common-dialog-buttons" style="text-align: center;">
		<a href="javascript:void(0)" id="common-dialog-save" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		<a href="javascript:void(0)" id="common-dialog-reset" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
		<a href="javascript:void(0)" id="common-dialog-close" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
	</div>
</body>
<script type="text/javascript">
$(function(){
	
	//时间显示
	window.setInterval(function(){
		var date = new Date();
		var second = date.getSeconds();
		
		date.setSeconds(second, 1000);
		
		$(".title-time-box span:last").html(date.toLocaleString());
	}, 1000);
	
	$("#styleBox").combobox({
		url:"<%=basePath%>/Json/style.json",
		method:"post",
		groupField:"group",
		textField:"text",
		valueField:"value",
		onSelect:function(record){
			var href = record.value;
			
			$.each($("link"),function(index,obj){
				if(obj.href.toLowerCase()
						.indexOf("easyui.css") > -1){
					obj.href = "<%=basePath%>/"+href;
					return false;
				}
			});
		}
	});
	
	//左侧菜单点击事件
	$(".menu-box li div a").click(function(){
		var title = $.trim($(this).text());
		var href = $(this).attr("name");
		
		if($(".menu-box div.selected")){
			$(".menu-box div.selected").removeClass("selected");
		}
		
		$(this).parent().addClass("selected");
		
		openMenu({
			title:title,
			href:href,
			target:{
				mode:"tab",
				tabId:"#index-tab"
			}
		});
		
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});
	
	//弹出框初始化
	$("#common-dialog").dialog({
		cache:false, 
		modal:true, 
		closed:true, 
		resizable:true, 
		buttons:"#common-dialog-buttons",
		onClose:function(){
			$("#common-dialog-buttons").children(":hidden").show();
			$(this).panel().find(".panel-body").children().remove();
			
			if(null != intervalId){
				window.clearInterval(intervalId);
				intervalId = null;
			}
			
			if(null != timeoutId){
				window.clearTimeout(timeoutId);
				timeoutId = null;
			}
		}
	});
	
	//弹出框按钮事件
	$("#common-dialog-save").click(function(){
		var ifValid = $("#common-dialog form")
						.form("enableValidation")
						.form("validate");
		if(ifValid){
			$("#common-dialog form").form("submit",{
				url: $(this).attr("action"),
				success:function(data){
					$("#common-dialog").dialog("close");
					data = eval("(" + data + ")");
					if(data.success){
						Messager.show({
							title:"&nbsp;",
							content:data.message
						});
						
						var curTab = $("#index-tab").tabs("getSelected");
						
						curTab.find(".datagrid-view").children("table[id$='grid']").datagrid("load");
					}else{
						Messager.alert({
							type:"error",
							title:"&nbsp;",
							content:data.message
						});
					}
				}
			});
		}
	});
	
	$("#common-dialog-reset").click(function(){
		$("#common-dialog form").form("clear");
	});
	
	$("#common-dialog-close").click(function(){
		$("#common-dialog").dialog("close");
	});
	
	//tab页标题事件委派
	$("#index-tab").on("contextmenu","li:gt(0)",function(event){
		event.preventDefault();
		
		var thisTabTitle = $(this).text();
		$("#index-tab").tabs("select", thisTabTitle);
		
		$("#index-menu").menu("show",{
			left:event.pageX,
			top:event.pageY
		});
	});
	
	//菜单相关按钮事件
	$("#index-menu-closeSelf").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabTitle = curTab.panel("options").title;
		
		$("#index-tab").tabs("close",curTabTitle);
	});
	
	$("#index-menu-closeOthers").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabTitle = curTab.panel("options").title;
		
		$.each($("#index-tab li"),function(index, obj){
			
			var tabTitle = $(obj).text();
			
			if(index > 0 && tabTitle != curTabTitle){
				$("#index-tab").tabs("close", tabTitle);
			}
		});
		
		$("#index-tab").tabs("select",curTabTitle);
	});
	
	$("#index-menu-closeLeft").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabIndex = $("#index-tab").tabs("getTabIndex", curTab);
		var curTabTitle = curTab.panel("options").title;
		
		$.each($("#index-tab li:lt(" + curTabIndex + ")"), function(index, obj){
			if(index > 0){
				var tabTitle = $(obj).text();
				
				$("#index-tab").tabs("close", tabTitle);
			}			
		});
		
		$("#index-tab").tabs("select",curTabTitle);
	});
	
	$("#index-menu-closeRight").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabIndex = $("#index-tab").tabs("getTabIndex", curTab);
		var curTabTitle = curTab.panel("options").title;
		
		$.each($("#index-tab li:gt(" + curTabIndex + ")"), function(index, obj){
			var tabTitle = $(obj).text();
			
			$("#index-tab").tabs("close", tabTitle);
		});
		
		$("#index-tab").tabs("select",curTabTitle);
	});
	
	$("#index-menu-closeAll").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabIndex = $("#index-tab").tabs("getTabIndex", curTab);
		var curTabTitle = curTab.panel("options").title;
		
		$.each($("#index-tab li"), function(index, obj){
			if(index > 0){
				var tabTitle = $(obj).text();
				
				$("#index-tab").tabs("close", tabTitle);
			}
		});
	});
	
	$("#index-logout").click(function(){
		self.location.href = "userLogin_logout.action";
	});
});

//记录运行函数ID
var intervalId = null;
var timeoutId = null;

//新增tab页
function openMenu(menuConfig){
	
	var title = menuConfig.title;
	var href = menuConfig.href;
	
	var mode = menuConfig.target.mode;
	
	if("tab" == mode){
		
		var tabId = menuConfig.target.tabId;
		
		if($(tabId).tabs("exists",title)){
			$(tabId).tabs("select",title)
		}else{
			$(tabId).tabs("add",{
				title:title,
				href:href,
				tools:[{
					iconCls:"icon-mini-refresh",
					handler:function(){
						var curTab = $(tabId).tabs("getSelected");
						curTab.panel("refresh");
					}
				}],
				closable:true
			});
		}
		
	}else if("dialog" == mode){
		openDialog({
			type:"view",
			title:"&nbsp;",
			width:800,
			height:400,
			href:href
		});
	}else if("blank" == mode){
		window.open(href);
	}
}

//新增dialog
function openDialog(config){
	if("view" == config.type){
		$("#common-dialog-buttons").children(":not(a[id='common-dialog-close'])").hide();
	}
	
	$("#common-dialog").dialog(config).dialog("open");
}

//提示信息
var Messager = {
	"alert":function(config){
		$.messager.alert(config.title, config.content, config.type);
	},
	"show":function(config){
		$.messager.show({
            title:config.title,
            msg:config.content,
            showType:"fade",
            timeout:1000,
            style:{
                right:"",
                bottom:""
            }
        });
	},
	"confirm":function(config){
		$.messager.confirm(config.title, config.content, config.handler);
	}
};

//菜单显示后处理方法
function menuOnShowHandler(item){
	var selTab = $("#index-tab").tabs("getSelected");
	
	var selTabIndex = $("#index-tab").tabs("getTabIndex", selTab);
	
	var leftExist = $("#index-tab li:lt(" + selTabIndex + ")").length - 1 > 0;
	var rightExist = $("#index-tab li:gt(" + selTabIndex + ")").length > 0;
	var othersExist = leftExist || rightExist;

	leftExist
			? $("#index-menu").menu("enableItem",$("#index-menu-closeLeft"))
			: $("#index-menu").menu("disableItem",$("#index-menu-closeLeft"));
	
	rightExist
			? $("#index-menu").menu("enableItem",$("#index-menu-closeRight"))
			: $("#index-menu").menu("disableItem",$("#index-menu-closeRight"));
	
	
	othersExist 
			? $("#index-menu").menu("enableItem",$("#index-menu-closeOthers"))
			: $("#index-menu").menu("disableItem",$("#index-menu-closeOthers"));
}

//报表datepicker共享方法
function getDateConfig(){
	var cfg = {};//#F{$dp.$D(\'beginTime\')
		
	var curMaxDate = null;
	
	var target = window.event.target;
	
	var targetId = target.id;
	
	var selectVal = $(target).closest("table").find("#repType").val();
	
	switch(selectVal){
		case "month":
			cfg.dateFmt = "yyyy-MM";
			curMaxDate = "%y-%M";
			break;
		case "year":
			cfg.dateFmt = "yyyy";
			curMaxDate = "%y";
			break;
		case "custom":
			cfg.dateFmt = "yyyy-MM-dd";
			curMaxDate = "%y-%M-%d";
			break;
		default:
			cfg.dateFmt = "yyyy-MM-dd";
			curMaxDate = "%y-%M-%d";
	}
	
	if(targetId.toLowerCase().indexOf("begintime") > -1){
		var endTimeId = $(target)
							.parent()
							.siblings("td")
							.children("input[id$='EndTime']")
							.attr("id");
		
		cfg.maxDate = "#F{$dp.$D(\'" + endTimeId + "\')||\'" + curMaxDate + "\'}";
	}else if(targetId.toLowerCase().indexOf("endtime") > -1){
		var beginTimeId = $(target)
							.parent()
							.siblings("td")
							.children("input[id$='BeginTime']")
							.attr("id");
		
		cfg.minDate = "#F{$dp.$D(\'" + beginTimeId + "\')}";
		cfg.maxDate = curMaxDate;
	}
			
	return cfg;
}
</script>
</html>