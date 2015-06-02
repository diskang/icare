// JavaScript Document


$(function(){
	
	$(".back").click(function(){
		WeixinJSBridge.call("closeWindow");
	 });
	$('a.btn').click(function(){
		var that=$(this);
		if(that.hasClass("disabled"))return false;
		var isValidate = addInfo.validateForm();
		if(isValidate){
			that.addClass("disabled");
			//var name = $('#relativeName').val();
			var phoneNum = $('#mobilePhone').val();
			var address = $('#relativeAddress').val();
			$.ajax({//TODO 加入elder id
		 		url : '/api/wechat/relative?wechat_id='+wechatId,
		 		contentType : 'application/json',
			    type : 'put',
			    data : JSON.stringify({"phoneNo":phoneNum,"householdAddress":address}),
			    dataType : "json",
			    success : function(obj){
			    	if(obj.status==200){
			    		that.removeClass("disabled");
				    	alert("更新成功");
			    	}else if(obj.error!=""){
		 				alert(obj.error);
		 				that.removeClass("disabled");
			    	}
			    	
			    },
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	that.removeClass("disabled");
			    	
			    }
		 	});
		}
	});
   
 });

var addInfo={

	validateForm:function(){
		var mobliePhone=$('#mobilePhone').val().trim();
		var addressVal=$('#relativeAddress').val().trim();
		 
		 if(mobliePhone==""){
			 $('#mobilePhone').parent().next().html("请填写手机号码!"); 
			 return false;
		 }else{
			 if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mobliePhone))){
				 $('#mobilePhone').parent().next().html("手机号码格式错误!");
				 return false;
			 }else{
				 $('#mobilePhone').parent().next().html("&nbsp;");
	         }
	  	  }
		 if(addressVal==""){
			  $('#relativeAddress').parent().next().html("联系人不能为空!");  
			  return false;
		 }else{
			 $('#relativeAddress').parent().next().html("&nbsp;");
		 }
		 return true;
	 }
};