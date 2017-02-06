<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<input type="hidden" name="path" value="${path}" />
<input type="hidden" name="basePath" value="${basePath}" />

<!-- 滚动条 -->
<!-- <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" /> -->

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/font-awesome-4.7.0/css/font-awesome.min.css" />

<!-- <script type="text/javascript" src="//cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="//cdn.bootcss.com/jqueryui/1.10.4/jquery-ui.min.js"></script> -->

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-1.10.4.min.js"></script>

<!-- <script type="text/javascript" src="//cdn.bootcss.com/layer/3.0.1/layer.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/layer/layer.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/main/main.js"></script>
<!--[if lt IE 9]>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/main/css3-mediaqueries.js"></script>
<![endif]-->

<script type="text/javascript">
	var path = $("input[name='path']").val();
	var basePath = $("input[name='basePath']").val();
</script>

