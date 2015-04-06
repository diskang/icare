var area={
	pid:'',
	obj:{'name':'','type':''},
	method:'get',
	drawAreaList:function(){
		$(".inf").addClass('hide');
	    $("#areashow").removeClass('hide');
        $('#areaInfo').addClass('hide');
        $('#alevel').attr('disabled','disabled');
        $("#areatree").tree({
            lines:true,
        //onlyLeafCheck:true,
            onDblClick:function(node){
               area.editTreenode(node);
            },
        })
        $.ajax({
		    type: "get",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url: rhurl.origin+"/gero/"+gid+"/area",
            timeout:deadtime,
            success: function (msg) {
            	temparea=msg.entities;
            	area.dealtree();
                area.removeTree();
                $("#areatree").tree("loadData",temparea2);
                //if(isFirst2){$("#areatree").tree("collapseAll");isFirst2=false;}
                $("#areatree").tree("collapseAll");
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
        	   leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
	   });
	},
	createTreeNode:function(node){
        this.id=node.id;
        this.text=node.name;
        this.children=[];
        this.attributes={"type":node.type,"level":node.level,"fullname":node.fullname}
    },
	findTreeChildren:function(id){
        var result=[];
        for(var i in temparea){
            if(temparea[i].parent_id===id){
                result.push(temparea[i]);
            }
        }
        return result;
    },
	createTreeData:function(node){
        var result=[];
        var childs= area.findTreeChildren(node.id);
        if (childs.length!==0){
            for(var i in childs){
                var temp= new area.createTreeNode(childs[i]);
                if (area.findTreeChildren(childs[i].id).length!==0){
                    temp.children=area.createTreeData(childs[i]);
                }
                result.push(temp);
            }
        }
        return result;
    },

	dealtree:function(){
		temparea2=[{"id":0,"text":"区域列表",attributes:{type:0},"children":[]}]
        temparea2[0].children=area.createTreeData(temparea2[0]);
	},

	removeTree:function(){
		$("#areatree li").remove();
	},
	editTreenode:function(){
        var node=$("#areatree").tree('getSelected');
        area.pid='/'+node.id;
        area.method="put";
        authority.obj.parent_id='';
        $('#areaInfo').removeClass('hide');
        $('#aname').attr('value',node.text);
        $('#atype').attr('disabled',"disabled");
        $('#atype').attr('value',node.attributes.type);
        $('#alevel').attr('value',node.attributes.level);
    },

    addTreenode:function(){
        var node=$("#areatree").tree('getSelected');
        if (node){
            area.pid='';
            area.obj.parent_id=node.id;
            area.method="post";
            $('#areaInfo').removeClass('hide');
            $('#areaInfo input').attr('value',null);
            $('#atype').removeAttr('disabled');
        }
    },
    buttonclk:function(){
        area.obj.name=document.getElementById("aname").value;
        area.obj.type=parseInt(document.getElementById("atype").value);
        var infoUrl=rhurl.origin+'/gero/'+gid+'/area'+area.pid;
        $.ajax({
            url: infoUrl, 
            type:area.method, 
            data:JSON.stringify(area.obj), 
            dataType:"json",
            contentType: "application/json;charset=utf-8",
            timeout:deadtime,
            error: function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }, 
            success: function(result){
                area.drawAreaList();
            } 
        }); 
    },
    delTreenode:function(){
        var node=$("#areatree").tree('getSelected');
        if (node){
            var infoUrl=rhurl.origin+'/gero/'+gid+"/area/"+node.id;
            $.ajax({
                url: infoUrl,
                type: 'DELETE',
                timeout:deadtime,
                success:function(result){
                    area.drawAreaList();
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
        else alert("无删除项");
    }

}