<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>J2EE WEB 开发基础框架—JST Development PartOne</title>
<script src="<%=basePath%>/template/default/js/plugins/jquery-1.3.2.js"
	type="text/javascript"></script>
<style>
* {
	margin: 0 auto;
	font-size: 12px;
}

span {
	height: 20px;
	line-height: 20px;
	display: block;
	color: #FFFFFF;
	margin-bottom: 8px;
}

.dl {
	width: 1024px;
	height: 715px;
	background: url(<%=basePath%>/Images/login.jpg) repeat center;
	position: relative;
}

p {
	width: 50px;
	float: left;
}

.weib {
	width: 250px;
	height: 80px;
	margin-bottom: 7px;
	position: absolute;
	left: 498px;
	top: 315px;
}

.text1 {
	height: 16px;
	width: 120px;
	float: left;
	border: 1px #0066FF solid;
	background-color: transparent;
	background: #fff;
}

.text2 {
	height: 16px;
	width: 60px;
	border: 1px #0066FF solid;
	background-color: transparent;
	background: #fff;
	float: left;
}

img {
	width: 60px;
	height: 19px;
}

.ann {
	width: 220px;
	position: absolute;
	left: 495px;
	top: 400px;
}

.btn1 {
	width: 75px;
	height: 25px;
	margin-right: 25px;
	cursor: pointer;
	border: none;
	background-color: transparent;
}

.btn2 {
	width: 75px;
	height: 25px;
	cursor: pointer;
	border: none;
	background-color: transparent;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#userCode").focus();
	});
	function checkForm() {
		var msg = "";
		if (!$("#userCode").val()) {
			msg += "用户名不能为空!\n";
		}
		if (!$("#password").val()) {
			msg += "密码不能为空!\n";
		}
		if (!$("#verifyCode").val()) {
			msg += "校验码不能为空!\n";
		}
		if (msg) {
			alert(msg);
			return false;
		}
		return true;
	}
</script>
</head>
<center>
	<body style="background: #0d3e8d">
		<s:form action="userLogin_login" namespace="/system" onsubmit="return checkForm();"
			id="loginForm">

			<div class="dl">
				<div class="weib">
					<span><p>用户名：</p> 
						<s:textfield name="user.userCode" cssClass="text1"></s:textfield></span>
					<span><p>密&nbsp;&nbsp;码：</p>
						<s:password name="user.password" cssClass="text1"></s:password></span>
					<span><p>校验码：</p>
						<input name="verifyCode" id="verifyCode" type="text" class="text1" />
						<em><img border=0 src="<%=basePath%>/system/verifyCode.action"></em></span>
				</div>
				<div class="ann">
					<input class="btn1" type="submit" value="" /> 
					<input class="btn2" type="button" /> <span><s:actionmessage></s:actionmessage></span>
				</div>
			</div>

		</s:form>
	</body>
</center>
</html>
