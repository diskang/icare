// JavaScript Document

 $(function(){
	 $(".wrapaddr").hide();//开始隐藏照片div
	 var imgUrl = [];
	 var imgId = [];//老人id
	 var index = 2;
	 $('#elderSearch').click(function(){
		add.validateName();
		$(".wrapaddr").find("ul").html("");//清除原有html
		$.ajax({
		 	url : '/api/wechat/elder',
		 	type : "get",
		 	data : {"elder_name":add.trimAll($('#userName').val()),"gero_id":"2"},
		 	success : function(obj){
		 		if(obj.entities!=null &&obj.entities.length>0){
		 			//alert("inside");
		 			 for(var i=0;i<obj.entities.length;i++){
		 				 imgUrl[i] = obj.entities[i].photo_url;
		 				 imgId[i] = obj.entities[i].elder_id;
		 			 }
		 		}else{//empty
		 			$('.error').eq(1).html("老人不存在，请检查姓名!");
		 			return;
		 		}
		 		add.imgulrAttr();
		 	/*	if(obj.entities.length>0){
	 				 $(".radio")[0].checked = true;
	 			}*/
		 	},
		 	error:function(XMLHttpRequest, textStatus, errorThrown){
		    	that.removeClass("disabled");
		    }
		 });
	 });
	 
	 //图片上向翻
	 $(".top").click(function(){
	 	if(index!=2){
	 		if(index % 2 !=0){
	 			index = index - 1;//图片为单数时
	 		}else{
	 			index = index - 2;//图片为双数时
	 		}
	 		add.imgulrAttr();
	 	}
	 });
	 
	 $(".next").click(function(){
	 	if(imgUrl.length-index <= 0)
	 		return ;
	 	if((imgUrl.length-index) < 2){
	 		index = index + 1;//图片为单数时
	 	}else{
	 		index = index + 2;//图片为双数时
	 	}
	 	add.imgulrAttr();
	 });
	 
	 $("#back").click(function(){
 		if(window.confirm('您确定要离开吗?') ){
			 WeixinJSBridge.call("closeWindow");
		 }
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
		 {
		 	that.addClass("disabled");
		 	var elder_name = $("#userName").val();//姓名,需要elder_id，不是elder_name
		 	var elderId = $('input:radio:checked').val();
		  	$.ajax({
			    url : '/api/wechat/relative/'+relativeId+'/elder/'+elderId+'?wechat_id='+wechatId,
			    type : 'post',
			    data : '{}',
			    contentType : 'application/json',
			    dataType : "json",
			    success : function(obj){
					//后台返回的json对象 麻烦你帮我处理一下	
			    	//var status = obj.status;
			    	if(obj.status==200){
			    		alert("添加成功");
			    		 WeixinJSBridge.call("closeWindow");
			    	}else{
			    		that.removeClass("disabled");
			    	}
			    },
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	that.removeClass("disabled");
			    	if(XMLHttpRequest.status==403){
			    	    alert("最多只能绑定两个老人！");
			    	}else if(XMLHttpRequest.status==400){
			    		alert("已经绑定过该老人!");
			    	}
			    //     alert(XMLHttpRequest.status);
        //             alert(XMLHttpRequest.readyState);
        //             alert(textStatus);
			    }
			});
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
			if($("input[type=radio]").length != 0){
				if(!$("input[type=radio]").is(":checked")){
					$('#imgError').html("请选择!"); 
					return false;
				}else{
					$('#imgError').html("&nbsp;");
				}
			}
			  return true;
		 },
		 validateName:function(){
			var userVal=add.trimAll($('#userName').val());
			if(userVal=="")
			  {
				  $('#userName').parent().next().html("姓名不能为空!");  
				  return false;
			  }
			 else
			  {
				  $('#userName').parent().next().html("&nbsp;");
			  }
			return true;
		 },
		 imgulrAttr : function(){
		 		var leng = imgUrl.length;//获取它的长度
		 		var begImg,endImg; //第一个图片和第二个图片的下标
		 		if(leng<=0)
		 			return ;
		 		if(index % 2 ==0&&leng!=1){
		 			begImg = index-2; //第一个图片的下标
			 		endImg = begImg+1; //第二个图片的下标
			 		$(".wrapaddr").find("ul").html("");//清除原有html
		 			$(".wrapaddr").find("ul").append(
				 			' <li class="imgli">'+
						 		'<img alt="加载失败" src="/downloadObject?file_url='+imgUrl[begImg]+'" width="120px" height="112px" class="img oneImg" >'+
						 		'<input type="radio" name="radio" class="radio" value="'+imgId[begImg]+'">确应照片'+
						 	'</li>'+
						 	'<li class="imgli" >'+
						 		'<img alt="加载失败" src="/downloadObject?file_url='+imgUrl[endImg]+'" width="120px" height="112px" class="img twoImg">'+
						 		'<input type="radio" name="radio" class="radio" value="'+imgId[endImg]+'">确应照片'+
						 	'</li>'
					);
		 		}else{
		 			if(leng==1){
		 				begImg = 0; //第一个图片的下标
		 			}else{
		 				begImg = index-1; //第一个图片的下标
		 			}
		 			
		 			$(".wrapaddr").find("ul").html("");//清除原有html
		 			$(".wrapaddr").find("ul").append(
						 	'<li class="imgli imgCont" >'+
						 		'<img alt="加载失败" src="/downloadObject?file_url='+imgUrl[begImg]+'" width="128px" height="112px" class="img twoImg ">'+
						 		'<input type="radio" name="radio" class="radio" value="'+imgId[begImg]+'">确应照片'+
						 	'</li>'
					);
		 		}
		 		//点击图片也可以选中老人 TODO 没效果
		 		$(".wrapaddr ul").find("li").click(function(){
		 			$(this).find("input[name=radio]").attr('checked',true);
				});
		 		$(".wrapaddr").show();//显示照片div
		 		
		 }
		};
	  });