<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
	<meta http-equiv="Expires" CONTENT="-1">           
	<meta http-equiv="Cache-Control" CONTENT="no-cache">           
	<meta http-equiv="Pragma" CONTENT="no-cache">
	<link rel="stylesheet" href="/static/modules/wechat/css/common.css"/>
	<link rel="stylesheet" href="/static/modules/wechat/css/bind.css">
	<script type="text/javascript" src="/static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="/static/modules/wechat/js/bind.js"></script>
	<title>新增老人</title>
	<script type="text/javascript">
		var wechatId = '${wechatId}';
		var relativeId = '${user.userId}';
		<% String basePath = request.getScheme()+"://" + request.getServerName()+":"+request.getServerPort();%>
		var basePath = "<%=basePath%>";
	</script>
</head>
<body>

<h3 class="title"><a href="#" class="back" id="back"></a><label>添加老人</label></h3> 
<div class="content">
	<div class="formcontent">
		<div class="row">
			<div class="wrapinput">
				<label>养老院</label>
				<label style="width: 72%;background-color: white ;" id="oldHome">新天地养老院</label>
			</div>
			<p class="error">&nbsp;</p>
		</div>
		
		<div class="row">
			<div class="wrapinput">
				<label>姓名</label>
				<input type="text" id="userName" name="userName"  maxlength="300" value="" style="width:48%; margin-right: 1.5px;"/>
				<button style="width: 23.4%;height:100%;border-radius: 3px; border:0px solid #F52626; background-color: #F52626; color: white;" id="elderSearch">搜&nbsp;索</button>
			</div>
			<p class="error">&nbsp;</p>
		</div>
     
		<div >
			<div class="wrapaddr clearfix">
			<div style="width: 20px; height: 140px;float: left;
				  background-image: url('/static/modules/wechat/images/bg.png'); 
				  background-repeat: no-repeat;
				  background-position:-26px 0px;
				  background-position-y : 44% ;"
				  class="top" 
				  ></div>
				 <ul>
				  
				 </ul>	
				 <div style="width: 20px; height: 140px;float: right;
				  background-image: url('/static/modules/wechat/images/bg.png'); 
				  background-repeat: no-repeat;
				  background-position: 0 46%;" 
				  class="next"
				  >
				 </div>
				</div>
			<p class="error" id="imgError">&nbsp;</p>
		</div> 
   		 <a class="btn">保存</a>
	</div>
</div>

</body>
</html>
