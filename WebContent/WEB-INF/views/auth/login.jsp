<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<%@include file="/WEB-INF/views/static/commonStyle.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/login.css" />
</head>
<body>
	<div class="bg"></div>
	<div class="lg_wp">
		<div class="title">
			<div class="icon"><i class="fa fa-user-circle-o fa-4x"></i></div>
			<div class="titleName">登录云签到${currentUser.name}</div>
		</div>
		<div class="lg_box">
			<div class="form_box">
				<form id="loginForm" action="${pageContext.request.contextPath}/auth/loginIn" method="post">
					<div class="input-element">
						<input type="text" name="name" notNull="true" nullmsg="用户名不能为空！">
						<label for="input">用户名 / 邮箱</label>
					</div>
					<div class="input-element">
						<input type="password"  name="password" notNull="true" nullmsg="密码不能为空！">
						<label for="input">密码</label>
					</div>
					<div class="form_line btn_line">
						<a class="registBtn" href="${pageContext.request.contextPath}/auth/regist">立即注册</a>
						<a href="javascript:;" class="btn submit_btn right" id="loginBtn">登录</a>
						<div class="clear"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<div class="foot">
		<%@include file="/WEB-INF/views/include/foot.jsp"%>
	</div>
	<%@include file="/WEB-INF/views/static/commonTools.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/auth/login.js"></script>
</body>
</html>