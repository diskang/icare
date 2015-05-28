<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path = request.getContextPath(); %>
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
	<link rel="stylesheet" href="/static/modules/wechat/css/common.css">
	<link rel="stylesheet" href="/static/modules/wechat/css/healthReport.css">
	<script src="/static/js/jquery-1.8.3.min.js"></script>
	<script src="/static/modules/wechat/js/healthReport.js"></script>
	<title>老人身体状况</title>
		
</head>

<body>
<script type="text/javascript">
	//var str = '${healthReport}'; 
	var str = JSON.parse('${healthReport}'); 
	//alert(JSON.stringify(str[0]));
</script>
<h3 class="title"><a href="javascript:history.go(-1);" class="back"></a><label>老人身体状况</label></h3> 
<div class="content">
	<div class="formcontent">
		<div  class="row" >
			<div class="wrapinput" >
				<label class="item">姓名</label>
			</div>
			<p class="error">&nbsp;</p>
	    </div>
		
     
		<div class="row">
			<div class="wrapinput">
				<label class="item">心跳(分/次)</label>
			</div>
			<p class="error">&nbsp;</p>
		</div>
      
		<div class="row">
			<div class="wrapinput">
				<label class="item">体温(℃)</label>
      		</div>
      		<p class="error">&nbsp;</p>
		</div>
		
		<div class="row">
			<div class="wrapinput" >
				<label class="item">血压(mmHg)</label>
			</div>
		</div>
        <a class="btn">返回</a>
	</div>
</div>
</body>
</html>
