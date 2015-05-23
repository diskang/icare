<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
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
	<script type="text/javascript" src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=path%>/static/modules/wechat/js/register.js"></script>
	<title>注册 </title>
	<script type="text/javascript">
	</script>
</head>

<body>

<h3 class="title">
	<a href="javascript:history.go(-1);" class="back"></a>注册
</h3>

<div class="content">
	<p class="tip">您可以通过以下操作完成注册。注册完才可以添加老人!</p>
	<div class="formcontent">
	
		<form action="/api/wechat/relative" id="regForm" method="post">
		<input type="hidden" name="wechatId" value="${wechatId}">
	
		<div class="row">
				<input type="text" name="name" placeholder="请输入姓名" maxlength="30" id="name"   />
				<p class="error" id="nameErr">&nbsp;</p>
		</div>
		<div class="row">
			<input type="number" name="phoneNo" placeholder="请输入手机号码" maxlength="11" id="phoneNum"/>
			<p class="error" id="phoneNumErr">&nbsp;</p>
		</div>
		<a class="btn" >注册</a>
		</form>
		
	</div>
</div>

</body>

</html>