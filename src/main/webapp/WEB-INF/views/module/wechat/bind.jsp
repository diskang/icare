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
	<link rel="stylesheet" href="/static/modules/wechat/css/common.css"/>
	<link rel="stylesheet" href="/static/modules/wechat/css/bind.css">
	<script type="text/javascript" src="<%=path%>/static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=path%>/static/modules/wechat/js/bind.js"></script>
	<title>新增老人</title>
	<script type="text/javascript">
		var addPath = '<%=path%>';
		var kk = '${ss}';
	</script>
</head>

<body>

<h3 class="title"><a href="javascript:history.go(-1);" class="back"></a><label>添加老人</label></h3> 
<div class="content">
	<div class="formcontent">
		<form action="<%=path%>/address/update" id="address" method="post">
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
				<input type="text" id="userName" name="userName"  maxlength="300" value=""/>
			</div>
			<p class="error">&nbsp;</p>
		</div>
     
		<div >
			
			<div class="wrapaddr clearfix">
			<div style="width: 20px; height: 140px;float: left;
				  background-image: url(' ../images/bg.png'); 
				  background-repeat: no-repeat;
				  background-position:-26px 0px;
				  background-position-y : 44% ;"
				  class="top" 
				  ></div>
				 <ul>
				   <li style="float: left; width: 42%;" >
				 		<img alt="加载失败" src="../images/1.jpg" width="120px" height="112px">
				 		<input type="radio" name="radio" class="radio">确应照片
				 	</li>
				 	<li style="float: left;width: 42%; ">
				 		<img alt="加载失败" src="../images/2.jpg" width="128px" height="112px">
				 		<input type="radio" name="radio" class="radio">确应照片
				 	</li>
				 </ul>	
				 <div style="width: 20px; height: 140px;float: right;
				  background-image: url(' ../images/bg.png'); 
				  background-repeat: no-repeat;
				  background-position: 0 46%;" 
				  class="next"
				  >
				
				 </div>
				</div>
			
		</div> 
    <a class="btn">保存</a>
	</form>	
	</div>
 
</div>

</body>
</html>
