<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/head.jsp" %>	    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- add -->
<form id="fm" method="post" action="system/user_add.action">
	<div class="datagrid-body">
		<div class="ftitle">User Information</div>
			<div class="fitem">
				<label>用户代码:</label> <input name="model.userCode"
					class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>用户名称:</label> <input name="model.userName"
					class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>密码:</label> <input name="model.password" type="password">
			</div>
			<div class="fitem">
				<label>状态:</label> 
				<select id="cc" name="model.state" class="combobox-f combo-f textbox-f" style="width:120px;">
				    <option value="0">有效</option>
				    <option value="1">无效</option>
				</select>
			</div>
			
			<div class="fitem">
				<label>备注:</label> <input type="text" name="model.remark">
			</div> 
	</div>
</form>
</body>
</html>