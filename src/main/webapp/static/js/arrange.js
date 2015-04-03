var arrange={
	allow:false,
	changed:false,
	subres:[],
	staffrec:[],
	addsubres:function(id,date){
		var flag=true;
		arrange.changed=true;
		for(var i in arrange.subres){
			if (arrange.subres[i].staff_id===id){
				flag=false;
				arrange.subres[i].work_date.push(date);
				arrange.subres[i].nowork_date.pop(date);
			}
		}
		if(flag){
			var sub={"staff_id":id,work_date:[],nowork_date:[]};
			sub.work_date.push(date);
			arrange.subres.push(sub);
		}
	},
	delsubres:function(id,date){
		var flag=true;
		arrange.changed=true;
		for(var i in arrange.subres){
			if (arrange.subres[i].staff_id===id){
				flag=false;
				arrange.subres[i].nowork_date.push(date);
				arrange.subres[i].work_date.pop(date);
			}
		}
		if(flag){
			var sub={"staff_id":id,work_date:[],nowork_date:[]};
			sub.nowork_date.push(date);
			arrange.subres.push(sub);
		}
	},
	putarrange:function(){
		var infoUrl=rhurl.origin+'/gero/'+gid+'/schedule';
		if (arrange.allow){
			if(window.confirm('你确定要提交修改吗？')){
                 $.ajax({
            			url: infoUrl, 
        	    		type:'put', 
            			data:JSON.stringify(arrange.subres), 
            			dataType: 'json', 
            			contentType: "application/json;charset=utf-8",
           		 		timeout: deadtime, 
            			error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            			success: function(result){arrange.subres=[];arrange.drawArrangeList();arrange.changed=false;} 
            		}); 
             }
		}
	},
	allowchange:function(){
		$('#button-allow').toggleClass("fc-state-default1");
		arrange.allow=(!arrange.allow);
	},
	prev:function(){
		Sundate.setTime(Sundate.getTime()-7*24*60*60*1000);
		if(arrange.changed)arrange.putarrange();
		else arrange.drawArrangeList();
	},
	next:function(){
		Sundate.setTime(Sundate.getTime()+7*24*60*60*1000);
		if(arrange.changed)arrange.putarrange();
		else arrange.drawArrangeList();
	},
	today:function(){
		arrange.putarrange();
		Sundate.setTime(todayms);
		arrange.drawArrangeList();
	},
	initdate:function(){
		Mondate.setTime(Sundate.getTime()+1*24*60*60*1000);
		Tuedate.setTime(Sundate.getTime()+2*24*60*60*1000);
		Weddate.setTime(Sundate.getTime()+3*24*60*60*1000);
		Thudate.setTime(Sundate.getTime()+4*24*60*60*1000);
		Fridate.setTime(Sundate.getTime()+5*24*60*60*1000);
		Satdate.setTime(Sundate.getTime()+6*24*60*60*1000);

	},
	showth:function(){
		weekstr[0]=Sundate.toLocaleDateString().replace(/\//g,'-');
	    weekstr[1]=Mondate.toLocaleDateString().replace(/\//g,'-');
	    weekstr[2]=Tuedate.toLocaleDateString().replace(/\//g,'-');
	    weekstr[3]=Weddate.toLocaleDateString().replace(/\//g,'-');
	    weekstr[4]=Thudate.toLocaleDateString().replace(/\//g,'-');
	    weekstr[5]=Fridate.toLocaleDateString().replace(/\//g,'-');
	    weekstr[6]=Satdate.toLocaleDateString().replace(/\//g,'-');
	    weekstr[0]=weekstr[0].replace(/-(\d)-/,'-0$1-');
	    weekstr[1]=weekstr[1].replace(/-(\d)-/,'-0$1-');
	    weekstr[2]=weekstr[2].replace(/-(\d)-/,'-0$1-');
	    weekstr[3]=weekstr[3].replace(/-(\d)-/,'-0$1-');
	    weekstr[4]=weekstr[4].replace(/-(\d)-/,'-0$1-');
	    weekstr[5]=weekstr[5].replace(/-(\d)-/,'-0$1-');
	    weekstr[6]=weekstr[6].replace(/-(\d)-/,'-0$1-');
	    weekstr[0]=weekstr[0].replace(/-(\d)$/,'-0$1');
	    weekstr[1]=weekstr[1].replace(/-(\d)$/,'-0$1');
	    weekstr[2]=weekstr[2].replace(/-(\d)$/,'-0$1');
	    weekstr[3]=weekstr[3].replace(/-(\d)$/,'-0$1');
	    weekstr[4]=weekstr[4].replace(/-(\d)$/,'-0$1');
	    weekstr[5]=weekstr[5].replace(/-(\d)$/,'-0$1');
	    weekstr[6]=weekstr[6].replace(/-(\d)$/,'-0$1');
	    $('.fc-sun').text("周日"+Sundate.toLocaleDateString());
	    $('.fc-mon').text("周一"+Mondate.toLocaleDateString());
	    $('.fc-tue').text("周二"+Tuedate.toLocaleDateString());
	    $('.fc-wed').text("周三"+Weddate.toLocaleDateString());
	    $('.fc-thu').text("周四"+Thudate.toLocaleDateString());
	    $('.fc-fri').text("周五"+Fridate.toLocaleDateString());
	    $('.fc-sat').text("周六"+Satdate.toLocaleDateString());
	},
    drawArrangeList:function(){
	    $(".inf").addClass('hide');
	    $("#arrangeshow").removeClass('hide');
	    arrange.initdate();
	    arrange.showth();
	    $('.fc-body tr').remove();
	    var datat;
	    if(document.getElementById("arrange_role").value) datat={start_date:weekstr[0],end_date:weekstr[6],role:document.getElementById("arrange_role").value};
	    	else datat={start_date:weekstr[0],end_date:weekstr[6]};
	    $.ajax({
        	type: "get",
        	data: datat,
        	dataType: "json",
        	contentType: "application/json;charset=utf-8",
        	url:rhurl.origin+"/gero/"+gid+"/schedule",
        	timeout:deadtime,
        	success: function (msg) {
        		staffrec=[];
            	var staffsch=leftTop.dealdata(msg);
            	for (var i in staffsch){
            		staffrec.push(staffsch[i].staff_id);
            		var tr=document.createElement('tr');
            		tr.id="schedule"+staffsch[i].staff_id;
            		var tdn=document.createElement('td');
            		tdn.setAttribute('class',"fc-name");
            		tdn.innerHTML=staffsch[i].name;
            		tr.appendChild(tdn);
            		var tdl=[];
            		for(var j=0;j<7;j++){
            			var td=document.createElement('td');
            			if(staffsch[i].work_date.indexOf(weekstr[j])!==-1) td.setAttribute('class',"arrange-work workday");
            			else td.setAttribute('class',"arrange-work");
            			td.setAttribute('num',weekstr[j]);
            			td.setAttribute('pid',staffsch[i].staff_id);
            			tr.appendChild(td);
            		}
            		$('.fc-body').append(tr);
            	}
            	arrange.fillall();
        	},
        	error: function(XMLHttpRequest, textStatus, errorThrown) {
            	leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
        	}
    	});
	},
	fillall:function(){
		$.ajax({
        	type: "get",
        	data:{page:1,rows:65535,sort:'ID',role:document.getElementById("arrange_role").value},
        	dataType: "json",
        	contentType: "application/json;charset=utf-8",
        	url:rhurl.origin+'/gero/'+gid+'/staff',
        	timeout:deadtime,
        	success: function (msg) {
            	var staffsch=leftTop.dealdata(msg);
            	for (var i in staffsch){
            		if (staffrec.indexOf(staffsch[i].staff_id)===-1){
            			var tr=document.createElement('tr');
            			tr.id="schedule"+staffsch[i].staff_id;
            			var tdn=document.createElement('td');
            			tdn.setAttribute('class',"fc-name");
            			tdn.innerHTML=staffsch[i].name;
            			tr.appendChild(tdn);
            			var tdl=[];
            			for(var j=0;j<7;j++){
            				var td=document.createElement('td');
            				td.setAttribute('class',"arrange-work");
            				td.setAttribute('num',weekstr[j]);
            				td.setAttribute('pid',staffsch[i].id);
            				tr.appendChild(td);
            			}
            			$('.fc-body').append(tr);
            		}
            	}
        	},
        	error: function(XMLHttpRequest, textStatus, errorThrown) {
            	leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
        	}
   		});
	}

}

