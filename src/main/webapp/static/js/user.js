var user={
	drawinfo:function(){
		$(".inf").addClass('hide');
	    $("#usershow").removeClass('hide');
	},
	changepwd:function(){
		$("#pwd-dialog-form").dialog("open");
		$('#new_pwd').attr('value',null);
		$('#renew_pwd').attr('value',null);
		$('#old_pwd').attr('value',null);

	},
	putpwd:function(){
		if($('#new_pwd').val()===$('#renew_pwd').val()){
		var infoUrl=rhurl.origin+'/user/'+uid+'/password';
		$.ajax({
            url: infoUrl, 
            type: 'put', 
            data:JSON.stringify({old_password:$('#old_pwd').val(),new_password:$('#new_pwd').val()}), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){alert("请确保原密码输入正确")}, 
            success: function(result){$("#pwd-dialog-form").dialog("close");alert("修改成功");} 
        });
	    }else alert('两次密码输入不一致');
	}
}