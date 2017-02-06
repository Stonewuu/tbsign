<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="menu_head animate self_animate">
	<p class="menu_icon"><i class="fa fa-reorder"></i></p>
	<p><i class="fa fa-user-circle-o fa-4x"></i></p>
	<p>你好，${currentUser.name}</p>
	<p><a href="${basePath}/auth/logout">退出</a></p>
</div>
<div class="menu_content self_animate">
	<div class="menu_content_hold">
		<div class="menu_line self_animate">
			<a href="${basePath}">
				<i class="fa fa-home"></i>
				<span>首页</span>
			</a>
		</div>
		<div class="menu_line self_animate">
			<a href="${basePath}/sign/">
				<i class="fa fa-calendar-check-o"></i>
				<span>签到</span>
			</a>
		</div>
		<div class="menu_line self_animate">
			<a href="${basePath}/bindInfo/">
				<i class="fa fa-info-circle"></i>
				<span>绑定信息</span>
			</a>
		</div>
		<div class="menu_line self_animate">
			<a href="javascript:;">
				<i class="fa fa-gear"></i>
				<span>设置</span>
			</a>
		</div>
	</div>
</div>