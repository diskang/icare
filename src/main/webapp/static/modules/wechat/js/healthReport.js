// JavaScript Document
$(document).ready(function(){ 
	
	$("a.btn").click(function(){
		 WeixinJSBridge.call("closeWindow");
	});
	$(".back").click(function(){
 		if(window.confirm('您确定要离开吗?') ){
			 WeixinJSBridge.call("closeWindow");
		 }
	 });


	health.init(str);
	
});

var _setHealthData=function(dataUrl,options,parseFunction){
	$.ajax({
	    url : dataUrl,
	    type : 'get',
	    success : function(obj){
			//后台返回的json对象 麻烦你帮我处理一下	
	    	//var status = obj.status;
	    	if(obj.status==200){
	    		if(obj.entities.length>0){
	    			$(".wrapper").html("");
	    			$(".wrapper").append("<canvas id='canvas'></canvas>");
	    			var healthData = parseFunction(obj.entities);
	    			var myChart = new Chart(document.getElementById("canvas").getContext("2d")).Line(healthData,options);
	    			//var legend = myChart.generateLegend();
	    		}
	    		
	    	}else{
	    		that.removeClass("disabled");
	    	}
	    },
	    error:function(XMLHttpRequest, textStatus, errorThrown){
	    	that.removeClass("disabled");
	    }
	});
}	
var health = {
	init:function(str){//初始化老人数据
		var length = str.length>2?2:str.length;
		//if(length>2)alert("只能显示两个老人");
		var formcontents = $(".row");
		 for(var k=0;k<length;k++){//循环对象个数
			//var val = JSON.stringify(str[k]);
			var elder = str[k];
			var eid  = elder.elderId;
			popup.preAppend(0,eid,elder.elderName);
			popup.preAppend(1,eid,elder.hr);
			popup.preAppend(2,eid,elder.t);
			popup.preAppend(3,eid,elder.bp);
		} 
	},
	
	getT:function(elderId){
		var dataUrl = '/api/wechat/elder/'+elderId+'/temperature?wechat_id='+wechatId;
		var tOptions = {'responsive' : true, 'scaleOverride':true,'scaleSteps':6, 'scaleStepWidth':0.5,'scaleStartValue':36
				,tooltipTemplate: "体温:<%= value %>℃",tooltipEvents: ["click"]};
		var parseFunction = function(entities){
			var len=entities.length;
//			Ts = new Array(len<7?7:len);//at least 7
//			for (var i= len-1; i>=0; i--){
//				Ts[i] = entities[i].temperature;
//			}
//			Ts=Ts.reverse();
			var usedLength = len<7?len:7;
			Ts = new Array(usedLength);//at most 7
			var x_axis = new Array(usedLength);
			for (var i= 0; i<usedLength; i++){
				Ts[i] = entities[i].temperature;
				x_axis[i]= entities[i]['time'].substr(5,5);
			}
			var tData = {
				labels : x_axis,
				datasets : [{
					label: "体温",
					fillColor : "rgba(90,190,90,.5)",
					strokeColor : "rgba(90,190,90,1)",
					pointColor : "rgba(90,190,90,1)",
					pointStrokeColor : "#fff",
					data : Ts
				}]
			};
			return tData;
		}
		_setHealthData(dataUrl,tOptions,parseFunction);
		popup.open("老人最近体温记录");
	},
	getHR:function(elderId){
		var dataUrl = '/api/wechat/elder/'+elderId+'/heart_rate?wechat_id='+wechatId;
		var hrOptions = {'responsive' : true,'scaleOverride':true,'scaleSteps':10, 'scaleStepWidth':5,'scaleStartValue':50
		,tooltipTemplate: "心率:<%= value %>次/分",tooltipEvents: ["click"]};
		var parseFunction = function(entities){
			var len=entities.length;
			var usedLength = len<7?len:7;
			var hrs = new Array(usedLength);//at most 7
			var x_axis = new Array(usedLength);
			for (var i= 0; i<usedLength; i++){
				hrs[i] = entities[i].rate;
				x_axis[i]= entities[i]['time'].substr(5,5);
			}
			
			var hrData = {
				labels : x_axis,
				datasets : [{
					fillColor : "rgba(90,190,90,.5)",
					strokeColor : "rgba(90,190,90,1)",
					pointColor : "rgba(90,190,90,1)",
					pointStrokeColor : "#fff",
					data : hrs
				}]
			}
			return hrData;
		}
		_setHealthData(dataUrl,hrOptions,parseFunction);
		popup.open("老人最近心率记录");
	},
	getBP:function(elderId){
		var dataUrl = '/api/wechat/elder/'+elderId+'/blood_pressure?wechat_id='+wechatId;
		var bpOptions = {'responsive' : true,'scaleOverride':true,'scaleSteps':10, 'scaleStepWidth':10,'scaleStartValue':50
				,multiTooltipTemplate: "<%= datasetLabel  %> - <%= value %>mmHg",tooltipEvents: ["click"]};
		var parseFunction = function(entities){
			var len=entities.length;
			var usedLength = len<7?len:7;
			var sp = new Array(usedLength);//at most 7
			var dp = new Array(usedLength);//at most 7
			var x_axis = new Array(usedLength);
			for (var i= 0; i<usedLength; i++){
				sp[i] = entities[i].systolicPressure;
				dp[i] = entities[i].diastolicPressure;
				x_axis[i]= entities[i]['time'].substr(5,5);
			}
			var bpData = {
				labels : x_axis,
				datasets : [{
					label:"低压",
					fillColor : "rgba(90,190,90,1)",
					strokeColor : "rgba(90,190,90,1)",
					pointColor : "rgba(90,190,90,1)",
					pointStrokeColor : "#fff",
					data : sp
				},
				{
					label:"高压",
					fillColor : "rgba(220,220,220,0.5)",
					strokeColor : "rgba(220,220,220,1)",
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					data : dp
				}]
			}
			return bpData;
		}
		_setHealthData(dataUrl,bpOptions,parseFunction);
		popup.open("老人最近血压记录");
	}
};

var popup = {
	open:function(title){
		var popWindow = document.getElementById("popupBackground");
		$('.popWindow b').html(title);
		popWindow.style.display="block";
	},
	close:function(){
		var popWindow = document.getElementById("popupBackground");
		popWindow.style.display="none";
	},
	preAppend:function(number,elderId,content){//number=0,1,2,3
		var onclickEvent = [' ', 
		                    ' onclick="health.getHR('+elderId+')"',
		                    ' onclick="health.getT('+elderId+')"',
		                    ' onclick="health.getBP('+elderId+')"'][number];
		$(".wrapinput").eq(number).append(
				'<label class="item" style="width:30%" ' +onclickEvent +'>'+content+'</a></label>');
	}
//	,toggle:function(){
//		var popWindow = document.getElementById("popupBackground");
//		if(popWindow.style.display=="none"){
//			popWindow.style.display="block";
//		}else{
//			popWindow.style.display="none";
//		}
//	}
};


