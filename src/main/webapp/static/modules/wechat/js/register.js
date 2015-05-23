$(function() {
   var register={
	  trimAll: function(str) {return str.replace(/\s/g, '');},
	  isMobile:function (str){return new RegExp(/^(13|14|15|17|18)\d{9}$/).test(str)},
	  init:function()
	   {
		   this.initEvent();
	   },
	  initEvent:function()
	   {
		   $('#phoneNum').blur(function(){
			   register.validatePhoneNum();  
			});
	      //登录按钮
		  $('a.btn').click(function(){
			var that=$(this);
			if(that.hasClass("disabled"))
			return false;
			register.validatePhoneNum();  //验证手机号码
			var isValidate=true;
			$('p.error').each(function(){
			  var that=$(this);
			  if(!(that.text()==""||that.text()==" "))	
			   {
				 isValidate=false;
				 return false;   
			   }
			})
			if(isValidate)
			 {that.addClass("disabled")
			 document.getElementById("regForm").submit();
			 }
		  });
	   },
	   validatePhoneNum:function(){
		 var name=$('#phoneNum').val();
		 if(register.trimAll(name)=="")
			{
					$('#phoneNumErr').html("请输入手机号!");  
			}
		else if(!register.isMobile(register.trimAll(name)))
			{
					$('#phoneNumErr').html("手机号码格式错误!");   
		    }
		else{
					$('#phoneNumErr').html("&nbsp;");
			}
		 },
		validateName:function(){
			var name = $("#name").val();
			if(register.trimAll(name)==""){
				$('#nameErr').html("请输入姓名!");
			}else{
				$('#nameErr').html("&nbsp;");
			}
		}
   };
   register.init();
});