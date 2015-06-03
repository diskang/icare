$(document).ready(function(){
	
	 $(".back").click(function(){
 		if(window.confirm('您确定要离开吗?') ){
			 WeixinJSBridge.call("closeWindow");
		 }
	 });
	 
});

