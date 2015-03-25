var authority={
    method:'post',
    obj:{'parent_id':'','name':'','permission':'','href':'','icon':'','notes':''},
    pid:'',
    drawAuthorityList:function(){
	    $(".inf").addClass('hide');
	    $("#authorityshow").removeClass('hide');
        $('#authorityInfo').addClass('hide');
	    $("#authoritytree").tree({
            lines:true,
        //onlyLeafCheck:true,
            onDblClick:function(node){
               authority.editTreenode(node);
            },
        })
        $.ajax({
		    type: "get",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url: rhurl.origin+"/user/1",
            success: function (msg) {
        	//temptree2=msg.entities[0].privilege_list;
                authority.removeAuthTree();
                $("#authoritytree").tree("loadData",temptree2);
                $('.panel').css("margin-right",0)
            
            },
            error: function(e) {
        	   alert(e);
            }
	   });
    },

    removeAuthTree:function(){
        $("#authoritytree li").remove();
    },

    editTreenode:function(){
        var node=$("#authoritytree").tree('getSelected');
        authority.pid='/'+node.id;
        authority.method="put";
        $('#authorityInfo').removeClass('hide');
        $('#pname').attr('value',node.text);
        $('#picon').attr('value',node.iconCls);
        $('#phref').attr('value',node.attributes.href);
        $('#pnotes').attr('value',node.attributes.notes);
        $('#ppermission').attr('value',node.attributes.permission);
    },

    addTreenode:function(){
        var node=$("#authoritytree").tree('getSelected');
        if (node){
            authority.pid='';
            authority.obj.parent_id=node.id;
            authority.method="post";
            $('#authorityInfo').removeClass('hide');
            $('#authorityInfo input').attr('value',' ');
        }
    },
    buttonclk:function(){
        authority.obj.name=document.getElementById("pname").value;
        authority.obj.notes=document.getElementById("pnotes").value;
        authority.obj.permission=document.getElementById("ppermission").value;
        authority.obj.href=document.getElementById("phref").value;
        authority.obj.icon=document.getElementById("picon").value;
        var infoUrl=rhurl.origin+'/privilege'+authority.pid;
        $.ajax({
            url: infoUrl, 
            type:authority.method, 
            data:JSON.stringify(authority.obj), 
            dataType:"json",
            contentType: "application/json;charset=utf-8",
            error: function(e){
                alert("error");
            }, 
            success: function(result){
                temptree=result.entities;
                temptree2=[{"id":1,"text":"权限列表","children":[]}]
                temptree2[0].children=leftTop.createTreeData2(temptree2[0]);
                authority.drawAuthorityList();
            } 
        }); 
    },
    delTreenode:function(){
        var node=$("#authoritytree").tree('getSelected');
        if (node){
            var infoUrl=rhurl.origin+"/privilege/"+node.id;
            $.ajax({
                url: infoUrl,
                type: 'DELETE',
                success:function(result){
                    temptree=result.entities;
                    temptree2=[{"id":1,"text":"权限列表","children":[]}]
                    temptree2[0].children=leftTop.createTreeData2(temptree2[0]);
                    authority.drawAuthorityList();
                }
            })
        }
        else alert("无删除项");
    }


}