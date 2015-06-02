<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String basePath = request.getScheme()+"://" + request.getServerName()+":"+request.getServerPort();%>
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
	<link rel="stylesheet" href="<%=basePath%>/static/modules/wechat/css/common.css"/>
	<link rel="stylesheet" href="<%=basePath%>/static/modules/wechat/css/updateInfo.css">
	<title>个人信息修改</title>
	<script src="<%=basePath%>/static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/static/modules/wechat/js/updateInfo.js"></script>
</head>

<body>

<h3 class="title"><a href="#" class="back"></a><label>个人信息修改</label></h3> 
<div class="content">
	<div class="formcontent">
		<div class="row">
			<div class="wrapinput">
				<label>姓名</label>
				<input type="text" id="relativeName" name="relativeName" disabled="disabled" maxlength="10" value=""/>
			</div>
			<p class="error">&nbsp;</p>
		</div>
		<div class="row">
			<div class="wrapinput">
				<label>手机号码</label>
				<input  type="text"  id="mobilePhone" name="phoneNumber" maxlength="11" value=""/>
			</div>
			<p class="error">&nbsp;</p>
		</div>
		<div class="row">
			<div class="wrapinput">
				<label>联系地址</label>
				<input type="text" id="relativeAddress" name="address"  maxlength="50" value=""/>
			</div>
			<p class="error">&nbsp;</p>
		</div>
     
        <a class="btn">保存修改</a>
	</div>
 
</div>
<script type="text/javascript">
	var wechatId = '${wechatId}';
	var relativeAbout = JSON.parse('${relativeAbout}'); 
	$('#relativeName').val(relativeAbout.name);
	$('#mobilePhone').val(relativeAbout.phoneNo);
	$('#relativeAddress').val(relativeAbout.householdAddress);
	//alert(JSON.stringify(relativeAbout));
</script>
</body>
</html>
