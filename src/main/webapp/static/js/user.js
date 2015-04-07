var user={
	drawinfo:function(){
		$(".inf").addClass('hide');
	    $("#usershow").removeClass('hide');
	},
	changepwd:function(){
		var pwd=prompt('请输入密码:');
		if(pwd){
		var infoUrl=rhurl.origin+'/user/'+uid+'/password';
		$.ajax({
            url: infoUrl, 
            type: 'put', 
            data:JSON.stringify({password:pwd}), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){alert("修改成功");} 
        }); 
	}
	},
}