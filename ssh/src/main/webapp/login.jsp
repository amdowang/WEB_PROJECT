<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>springmvc login page</title>
</head>
<body>
	<%request.setAttribute("abc", "asdfasdfds"); %>
	<form id="loginForm" action="${ctx}/login" method="post">
	${pageContext.request.contextPath}
		<input type="text" id="username" name="username" value="${username}" />
		<input type="password" id="password" name="password" /> <input
			id="submit_btn" type="submit" value="登录" />
			${ctx}
			${abc}
			<c:out value="abc"></c:out>
			<c:out value="${abc}"></c:out>
	</form>
</body>
</html>