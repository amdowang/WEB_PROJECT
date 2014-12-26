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
<form id="fm" method="post" action="system/user_edit.action">
	<s:hidden name="model.id"></s:hidden>
	<div class="datagrid-body">
		<div class="fitem">
			<label>用户代码:</label> 
				<s:textfield name="model.userCode" cssClass="easyui-validatebox" required="true"></s:textfield>
		</div>
		<div class="fitem">
			<label>用户名称:</label>
				<s:textfield name="model.userName" cssClass="easyui-validatebox" required="true"></s:textfield>
		</div>
		<div class="fitem">
			<label>密码:</label>
			<s:password name="model.password" required="true"></s:password>
		</div>
		<div class="fitem">
			<label>状态:</label> 
			<s:select 
			       name="model.state"
			       headerKey="" headerValue="--请选择--"
			       list="#{'1':'有效', '0':'无效'}"
			       required="true"/>
		</div>
		
		<div class="fitem">
			<label>备注:</label><s:textarea name="model.remark"></s:textarea>
		</div> 
	</div>
</form>
</body>
</html>