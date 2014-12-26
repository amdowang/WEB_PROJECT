<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/head.jsp" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
</head>
<body>
	<div class="datagrid-header" id="expert-grid-toolbar" style="width: 100%;">
	<table class="datagrid-htable" border="0" cellspacing="0" cellpadding="0" style="width:100%;">
		<tr class="datagrid-header-row" style="height: 30px;">
			<td align="left" style="border-style: solid; padding-left: 48px;">
				用户代码：
				<input type="text" id="userCode" name="userCode">
				
				用户名：
				<input type="text" id="userName" name="userName">
			</td>
		</tr>
	
		<tr class="datagrid-header-row" style="height: 40px;">
			<td colspan="6" align="left" style="border-style: solid;">
				<shiro:hasPermission name="user:add">
				<a href="javascript:void(0)" id="user-grid-add" class="easyui-linkbutton" data-options="iconCls:'icon-add'">增加</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="user:delete">
				<a href="javascript:void(0)" id="user-grid-delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="user:edit">
				<a href="javascript:void(0)" id="user-grid-edit" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="user:select">
				<a href="javascript:void(0)" id="user-grid-search" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
				</shiro:hasPermission>
			</td>
		</tr>
	</table>
</div>
<table id="user-grid" toolbar="#expert-grid-toolbar"></table>
<div id="user-menu" class="easyui-menu" style="width:160px;">
	<shiro:hasPermission name="user:add">
	<div id="user-menu-add" data-options="iconCls:'icon-redo'">增加</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:delete">
	<div id="user-menu-delete" data-options="iconCls:'icon-remove'">删除</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="user:edit">
	<div id="user-menu-edit" data-options="iconCls:'icon-edit'">编辑</div>
	</shiro:hasPermission>
</div>
<script type="text/javascript">
$(function() {
	$("#user-grid").datagrid({
		//title:"泥头车企业管理",
		toolbar : "#expert-grid-toolbar",
		border : false,
		fit : true,
		columns : [ [ {
			field : "ckb",
			halign : "center",
			align : "center",
			resizable : false,
			checkbox:true
		}, {
			field : "userCode",
			title : "用户代码",
			width : 80,
			halign : "center",
			align : "center",
			resizable : true,
			sortable:true
		}, {
			field : "userName",
			title : "用户名称",
			width : 200,
			halign : "center",
			align : "center",
			resizable : true,
			sortable:true
		}, {
			field : "deptName",
			title : "部门名称",
			width : 80,
			align : "center",
			resizable : false
		}, {
			field : "state",
			title : "有效状态",
			width : 80,
			align : "center",
			resizable : false,
			sortable:true
		} ] ],
		//striped:true,
		fitColumns : true,
		//autoRowHeight:true,
		rownumbers : true,//显示行数
		singleSelect : false,//单选
		pagination : true,//分页
		pageSize : 15,//每页显示行数
		pageList : [ 5, 10, 15, 20, 25, 30 ],//列表可选择的 每页显示行数
		sortName : "id",//排序名称
		sortOrder : "desc",//升，降
		//url : "system/user_list.action",
		method : "post",
		loadMsg : "加载数据中，请稍后",//*数据库加载过程提示
		onRowContextMenu:function(e, rowIndex, rowData){//右键时触发事件
			e.preventDefault();
			$(this).datagrid("unselectAll");
			$(this).datagrid("selectRow", rowIndex);
			$("#user-menu").menu("show",{
				left:event.pageX,
				top:event.pageY
			});
		}
	}).datagrid("columnMoving")
	  .datagrid("columnHiding");
	$("#user-grid-search").click(function(){
		$("#user-grid").datagrid("options").url='<%=basePath%>/system/user_list.action';
		$("#user-grid").datagrid("load",{
			'model.userCode':$("#userCode").val(),
			'model.userName':$("#userName").val()
		});
	});
	$("#user-grid-add,#user-menu-add").click(function(){
		openDialog({
			title:"用户添加",
			width:350,
			height:300,
			maximizable:false,
			href:"system/user_toAdd.action"
		});
	});
	$("#user-grid-delete,#user-menu-delete").click(function(){
		//var row = $('#user-grid').datagrid('getSelected');
		var row = $('#user-grid').datagrid('getSelections');
		if (row && row.length>0) {
			$.messager.confirm('Confirm',
			'Are you sure you want to destroy this user?',
			function(r) {
				if (r) {
					//单个删除
					if(row.length==1){
						$.post('<%= basePath%>/system/user_delete.action', {
							id : row[0].id
						}, function(result) {
							//成功
							if (result.success) {
								$.messager.show({
									title:'success',
									msg:result.message,
									timeout:1000,
									style:{
					                    right:'',
					                    bottom:''
					                }
								});
								$('#user-grid').datagrid('reload'); // reload the user data
							} else {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.message,
									style:{
					                    right:'',
					                    bottom:''
					                }
								});
							}
						}, 'json');
						//批量删除
					}else{
						//拼装id
						var parm = "";
						$.each(row, function (i, n) {
							if (i == row.length-1) {
								parm += n.id;
							} else {
								parm += n.id +",";
							}
						});
						$.post('<%= basePath%>/system/user_deletes.action', {
							ids : parm
						}, function(result) {
							//成功
							if (result.success) {
								$.messager.show({
									title:'success',
									msg:result.message,
									timeout:1000,
									style:{
					                    right:'',
					                    bottom:''
					                }
								});
								$('#user-grid').datagrid('reload'); // reload the user data
							} else {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.message,
									style:{
					                    right:'',
					                    bottom:''
					                }
								});
							}
						}, 'json');
					}
				}
			});
		}
	});
	$("#user-grid-edit,#user-menu-edit").click(function(){
//		var row = $('#user-grid').datagrid('getSelected');
		var row = $('#user-grid').datagrid('getSelections');
		var infoMsg = null;
		infoMsg = row.length < 1 ? "请选择一条记录" : (row.length > 1 ? "最多只能选择一条记录" : null);
		if(null != infoMsg){
			$.messager.alert('提示',infoMsg,'warning');
		}else{
			openDialog({
				title:"用户编辑",
				width:350,
				height:300,
				maximizable:false,
				href:"system/user_toEdit.action?id="+row[0].id
			});
		}
	});
});

</script>
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
</body>
</html>