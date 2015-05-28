<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String basePath = request.getScheme()+"://" + request.getServerName()+":"+request.getServerPort();%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
	<meta http-equiv="Expires" CONTENT="10080">           
	<meta http-equiv="Cache-Control" CONTENT="store,cache">           
	<meta http-equiv="Pragma" CONTENT="cache">
	<link rel="stylesheet" href="/static/modules/wechat/css/common.css">
	<link rel="stylesheet" href="/static/modules/wechat/css/register.css">
	<script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/modules/wechat/js/register.js"></script>
	<title>信息维护 </title>
	<script type="text/javascript">
		//var wechatId = '${wechatId}';
		var basePath = <%=basePath%>+"";
	</script>
</head>

<body>

<h3 class="title">
	<a href="#" class="back"></a>信息维护
</h3>
<div class="content">
<p class="tip">${relativeAbout} </p>
</div>
</body>
</html>