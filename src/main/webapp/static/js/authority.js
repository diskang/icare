var authority={

    drawAuthorityList:function(){
	    $(".inf").addClass('hide');
	    $("#authorityshow").removeClass('hide');
	    $("#authoritytree").tree({
            lines:true,
        //onlyLeafCheck:true,
            onDblClick:function(node){
               $(this).tree('beginEdit',node.target);
            }
        })

        $.ajax({
		  type: "get",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url: "/user/1",
            success: function (msg) {
        	//temptree2=msg.entities[0].privilege_list;
                authority.removeAuthTree();
                $("#authoritytree").tree("loadData",temptree2);
            
            },
            error: function(e) {
        	   alert(e);
            }
	   });
    },

    removeAuthTree:function(){
        $("#authoritytree li").remove();
    }
}