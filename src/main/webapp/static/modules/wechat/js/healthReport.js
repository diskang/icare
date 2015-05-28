// JavaScript Document
$(document).ready(function(){ 
	
	$("a.btn").click(function(){
		 WeixinJSBridge.call("closeWindow");
	});
 var health = {
	init:function(str){//初始化老人数据
		var length = str.length;
		if(length>2)alert("您绑定的老人个数超过了2个，其余将无法显示");
		var formcontents = $(".row");
		 for(var k=0;k<2;k++){//循环对象个数
			//var val = JSON.stringify(str[k]);
			var elder = str[k]; 
		    $(".wrapinput").eq(0).append('<label class="item" style="width: 30%"><a href="#">'+elder["elderName"]+'</a></label>');
		    $(".wrapinput").eq(1).append('<label class="item" style="width: 30%"><a href="#">'+elder["hr"]+'</a></label>');
		    $(".wrapinput").eq(2).append('<label class="item" style="width: 30%"><a href="#">'+elder["t"]+'</a></label>');
		    $(".wrapinput").eq(3).append('<label class="item" style="width: 30%"><a href="#">'+elder["bp"]+'</a></label>'); 	
		} 
	}	 
 };
 health.init(str);
});

