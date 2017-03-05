<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<%@include file="/WEB-INF/views/static/commonStyle.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/regist.css" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
</head>
<body>
	<div class="bg"></div>
	<div class="regist_wp">
		<div class="title">
			<div class="icon"><i class="fa fa-user-circle-o fa-4x"></i></div>
			<div class="titleName">注册云签到</div>
		</div>
		<div class="lg_box">
			<div class="form_box">
				<form action="${pageContext.request.contextPath}/auth/registIn" id="registForm" method="post">
					<div class="input-element">
						<input type="text" name="name" notNull="true" nullmsg="用户名不能为空！">
						<label for="input">用户名</label>
					</div>
					<div class="input-element">
						<input type="password"  name="password" notNull="true" nullmsg="密码不能为空！">
						<label for="input">密码</label>
					</div>
					<div class="input-element">
						<input type="text" name="email" notNull="true" nullmsg="邮箱不能为空！">
						<label for="input">邮箱</label>
					</div>
					<div class="form_line btn_line">
						<div class="login">已有账号？<a class="loginBtn" href="${pageContext.request.contextPath}/auth/login">立即登录</a></div>
						<a href="javascript:;" class="btn submit_btn right" id="registBtn">立即注册</a>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/auth/regist.js"></script>
</body>
</html>