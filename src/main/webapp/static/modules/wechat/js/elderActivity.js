// JavaScript Docu'ment
$(document).ready(function(){ 
	$("a.btn").click(function(){
		 WeixinJSBridge.call("closeWindow");
	});
	$(".back").click(function(){
 		if(window.confirm('您确定要离开吗?') ){
			 WeixinJSBridge.call("closeWindow");
		 }
	 });
	console.log(activity.length);
	health.init(activity);
	$("#elder1").click(function(){
		$(this).removeClass("elderNamehide");
		$(this).addClass("elderNameShow");
		$("#elder0").addClass("elderNamehide");
		health.getD(1, activity);
	});
	$("#elder0").click(function(){
		$(this).removeClass("elderNamehide");
		$(this).addClass("elderNameShow");
		$("#elder1").addClass("elderNamehide");
		health.getD(0, activity);
	});
});

var health = {
	init:function(activity){//初始化老人数据
		if(activity.length>1){//如果有2个人就显示姓名
			$(".row").eq(0).append('<div class="wrapinput">'+
			'<label  id="elder0" class="elderNameShow">'+activity[0]["elderName"]+'</label>'+
			'<label  id="elder1" class="elderNamehide">'+activity[1]["elderName"]+'</label>'+
			'</div>'+
			'<p class="error">&nbsp;</p>');
		}
		health.getD(0, activity);//默认第一个显示
	},
	getD:function(index,activity){//循环项目
		var num = [];//次数
		var newStr = [];
		var newIndex = 0;
		var recordLength = activity[index]["elderRecord"].length;
		if(recordLength==0){
			$(".emptyDiv")[0].style.display="block";
		}else{
			$(".emptyDiv")[0].style.display="none"
		}
		for(var k=0;k<recordLength;k++){
			var n = 1;
			for(var j=k+1;j<activity[index]["elderRecord"].length;j++){
				if(activity[index]["elderRecord"][k]["elderItemId"]==activity[index]["elderRecord"][j]["elderItemId"]){
					n++;
				}
			}
			if(n==1){
				newStr[newIndex] = activity[index]["elderRecord"][k];
				newIndex++;
			}
			num[k] = n;
		}
		$(".row").eq(1).nextAll("div.row").remove();
		for(var i=0;i<newStr.length;i++){
			var d = newStr[i];
			var activityDate = d["finishTime"].substring(0,10);
			$(".row").eq(1).after('<div class="row">'+
			'<div class="dateValue">'+
			'<label >'+d["itemName"]+'</label>'+
			'<label style="width: 16%">'+num[i]+'</label>'+
			'<label class="withLabe2">'+activityDate+'</label>'+
			'</div></div>'
		  );
		}
	}
};



