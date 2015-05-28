// JavaScript Document
$(document).ready(function(){ 
	
	$("a.btn").click(function(){
		 WeixinJSBridge.call("closeWindow");
	});
 var health = {
	init:function(str){//初始化老人数据
		var length = str.length;
		var formcontents = $(".row");
		 for(var k=0;k<length;k++){//循环对象个数
			var val = JSON.stringify(str[k]);
			var i = 0;
		    for(var e in val){ //循环具体内容
		    	if(i==0)
		    		$(".wrapinput").eq(i).append('<label class="item"><a href="#">'+val["elderName"]+'</a></label>');
		    	if(i==1)
		    		$(".wrapinput").eq(i).append('<label class="item"><a href="#">'+val["hr"]+'</a></label>');
		    	if(i==2)
		    		$(".wrapinput").eq(i).append('<label class="item"><a href="#">'+val["t"]+'</a></label>');
		    	if(i==3)
		    		$(".wrapinput").eq(i).append('<label class="item"><a href="#">'+val["bp"]+'</a></label>');
		    	i++ ;
		    }
		} 
	}	 
 };
 health.init(str);
});

