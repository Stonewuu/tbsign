<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/WEB-INF/views/static/commonStyle.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/sign.css" />
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
					关注的贴吧列表
				</div>
				<c:if test="${current.bdInfo!=null}">
					<c:if test="${fn:length(current.bdInfo) > 0}">
						<p>切换百度账号：</p>
						<p>
							<select class="bdselect" id="bdselect">
								<option value="">请选择账号</option>
								<c:forEach items="${current.bdInfo}" var="info">
									<option value="${info.id}" ${bdInfo.id==info.id?"selected":"" }>${info.bdName}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="uid" value="${bdInfo.uid }" />
							<a href="javascript:;" class="btn small_btn" id="refreshForum">刷新贴吧列表</a>
							<a href="javascript:;" class="btn small_btn" id="signAllForum">签到所有贴吧</a>
						</p>
						<c:if test="${!status}">
							<div class="table_container">
								${msg }
							</div>
						</c:if>
						<c:if test="${bdInfo!=null}">
							<div class="table_container">
								<table>
									<tr>
										<th class="m_hide">编号</th>
										<th>贴吧名</th>
										<th>等级</th>
										<th>签到状态</th>
										<th>操作</th>
									</tr>
									<c:forEach items="${bdInfo.forums }" var="forum">
										<tr>
											<td class="m_hide">${forum.forumId }</td>
											<td>${forum.forumName }</td>
											<td>${forum.level }</td>
											<td>${forum.signed?"已签到":"未签到" }</td>
											<td>
												<c:if test="${!forum.signed}">
													<a href="javascript:;" class="green testSign" forum_id="${forum.forumId }">测试签到</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
					</c:if>
					<c:if test="${fn:length(current.bdInfo) == 0}">
						<p>当前账号未绑定百度账号，请<a href="${basePath }/bindInfo/index">点击此处</a>前往绑定</p>
					</c:if>
				</c:if>
				<c:if test="${current.bdInfo==null}">
					<p>当前账号未绑定百度账号，请<a href="${basePath }/bindInfo/index">点击此处</a>前往绑定</p>
				</c:if>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>

<div class="foot">
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</div>
<%@include file="/WEB-INF/views/static/commonTools.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/main/sign.js"></script>
</body>
</html>