var user={
	drawinfo:function(){
		$(".inf").addClass('hide');
	    $("#usershow").removeClass('hide');
	},
	changepwd:function(){
		alert("changing");
	},
	putuser:function(){
		var infoUrl=rhurl.origin+'/user/'+uid;
		$.ajax({
            url: infoUrl, 
            type: 'put', 
            data:JSON.stringify({name:document.getElementById("uusername").value,photo_url:''}), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){document.getElementById('uusername').setAttribute('value',result.username);} 
        }); 
	}
}