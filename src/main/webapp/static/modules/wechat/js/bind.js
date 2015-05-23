// JavaScript Document

 $(function(){
	 
	 $('#userName').blur(function(){
		 add.validateName();
	 });
	 
	 //保存按钮
	  $('a.btn').click(function(){
		var that=$(this);
		if(that.hasClass("disabled"))
		return false;
		add.validateForm();  //验证form
		var isValidate=true;
		$('p.error').each(function(){
		  var that=$(this);
		  if(!(that.text()==""||that.text()==" "))	
		   {
			 isValidate=false;
			 return false;   
		   }
		});
		if(isValidate)
		 {that.addClass("disabled");
		 	document.getElementById("add").submit();
		 }
	  });
	 
	 var add = {
		trimAll: function(str) {
					return str.replace(/\s/g, '');
			},
		validateForm:function(){
			if(!add.validateName()){
				return false;
			}
			/*if(!$("input[type=radio]").is(":checked")){
				$('#imgError').html("请选择!"); 
				return false;
			}else{
				$('#imgError').html("&nbsp;");
			}*/
			  return true;
		 },
		 validateName:function(){
			var userVal=add.trimAll($('#userName').val());
			if(userVal=="")
			  {
				  $('#nameError').html("姓名不能为空!");  
				  return false;
			  }
			 else
			  {
				  $('#nameError').html("&nbsp;");
			  }
			return true;
		 }
		};
	  });