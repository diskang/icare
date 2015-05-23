// JavaScript Document

 $(function(){
	 health.init(str);
	 var health = {
		init:function(str){//初始化老人数据
			var length = str.healthReport.length;
			var formcontent = $(".formcontent").find(".row");
			var length = $(".formcontent").find(".row").length;
			for(var k=0;k<length;k++){//循环对象个数
				var val = str.healthReport[k];
				var i = 0;
			    for(var e in val){ //循环具体内容
			    	if(i != 0){
			    		formcontent[i].append('<label class="item"><a href="#">'+val[e]+'</a></label>');
			    	}
			    	i++;
			    }
			}
		}	 
	 };
 });