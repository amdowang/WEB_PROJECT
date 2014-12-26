<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>泥头车企业管理</title>
</head>
<body>
<script type="text/javascript">
	$(function(){
		alert("账户已在其它地点登陆，你已被下线");
		top.window.location.href="<%=request.getContextPath()%>/Login.jsp";
	});
	</script>
</body>
</html>