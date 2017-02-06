<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/WEB-INF/views/static/commonStyle.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/bindInfo.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>

<title>绑定信息</title>
</head>
<body>

<div class="main_page">
	<div class="page_content">
	<%@include file="/WEB-INF/views/include/menu.jsp"%>
		<div class="main_container self_animate">
			<div class="content">
				<div class="content_title">
					绑定信息
				</div>
				<c:if test="${current.bdInfo!=null}">
					<p>当前账号：已绑定${fn:length(current.bdInfo)}个账号</p>
					<c:if test="${fn:length(current.bdInfo) > 0}">
						<p>绑定的百度账号：</p>
						<c:forEach items="${current.bdInfo}" var="bdInfo">
							<p>
								<span class="tb_name">${bdInfo.bdName}</span>
								（<a href="javascript:;" bdid="${bdInfo.id}" class="unbindBduss red h_orange self_animate" id="unbindBduss">解除绑定</a>）
							</p>
						</c:forEach>
					</c:if>
					
				</c:if>
				<c:if test="${current.bdInfo==null}">
					<p>当前账号：未绑定</p>
				</c:if>
				<div class="bind_container">
					<div>请进行以下操作继续绑定</div>
					<div class="input-element">
						<input type="text" name="bduss" notNull="true" nullmsg="用户名不能为空！">
						<label for="input">请输入您的百度bduss</label>
					</div>
					<p><a href="javascript:;" id="bindBduss" class="btn submit_btn">绑定</a></p>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>

<div class="foot">
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</div>
<%@include file="/WEB-INF/views/static/commonTools.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/main/bindInfo.js"></script>
</body>
</html>