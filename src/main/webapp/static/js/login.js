 $(function() {
            $("#dialog-form").dialog({
					autoOpen:false,
					resizable:false,
					draggable:false,
    				height:200,
    				width:300,
    				title:"欢迎登录HouseCare",
    				modal: true,
    				fixed:true,
    				overlay: {
        				backgroundColor: '#000',
        				opacity: 1,
        				overflow:'auto'
    				}
               	});
            $('#login').click(function(e) {
               //startCovered();
               $("#dialog-form").dialog("open");
            });

});
