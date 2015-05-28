// JavaScript Document


	$(function(){
		
		$(".btn").click(function(){
			
			if(addInfo.validateForm()){
				console.log("ok");
			}else{
				alert(2);
			}
		});
		
	   var addInfo={
		validateForm:function(){
			var userVal=$('#relativeName').val().trimAll(),mobliePhone=$('#mobilePhone').val().trimAll();
			if(userVal=="")
			  {
				  $('#relativeName').parent().next().html("联系人不能为空!");  
				  return false;
			  }
			 else
			  {
				  $('#relativeName').parent().next().html("&nbsp;");
			  }
			 
			 if(mobliePhone=="")
			  {
				  $('#mobilePhone').parent().next().html("请填写手机号码!"); 
				  return false; 
			  }else
			  {
			   if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mobliePhone)))
			    {
			    $('#mobilePhone').parent().next().html("手机号码格式错误!");
			     return false;
				}
				else
		        {
			      $('#mobilePhone').parent().next().html("&nbsp;");
		         }
		  	  }
			  return true;
		  }
		};
	  });