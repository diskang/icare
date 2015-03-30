var role={
    rid:"",
    temp:[],
    temp2:[],
    drawGeroRoleList:function(){
    $(".inf").addClass('hide');
	$("#geroroleshow").removeClass('hide');
	$('#gerorolepage').datagrid({ 
        title:'角色列表', 
        iconCls:'icon-edit',//图标 
        height:300,
        width:500,
        nowrap: false, 
        loadMsg:"正在加载，请稍等...", 
        striped: true, 
        border: true, 
        collapsible:false,//是否可折叠的 
        fit:false,//自动大小 
        url:rhurl.origin+'/gero/'+gid+'/role',  
        method:'get',
        remoteSort:true,  
        sortName:'ID',  
        singleSelect:true,//是否单选 
        pagination:true,//分页控件 
        rownumbers:true,//行号  
        pageNumber:1,
        pagePosition:'bottom',
        pageSize: 10,//每页显示的记录条数，默认为20 
        pageList: [10,20,30],//可以设置每页记录条数的列表 
        loadFilter:function(data){
            leftTop.dealdata(data);
        	var result={"total":0,"rows":0};
            result.total=data.total;
            result.rows=data.entities;
            return result;
        },
        toolbar: [{ 
            text: '添加', 
            iconCls: 'icon-add', 
            handler: function() { 
                role.addRoleInfo();
            } 
        },'-',{ 
            text: '删除', 
            iconCls: 'icon-remove', 
            handler: function(){ 
                role.delRoleInfo(); 
            }
        }]
    }); 
	var pager = $('#gerorolepage').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        /*onBeforeRefresh:function(){
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }*/ 
		});
    },

    drawRoleInfo:function(data){
        $("#role-dialog-form").dialog("open");
        $("#role-dialog-form").dialog("center");
        temp=[];
        temp2=[];
        $("#authoritychecktree").tree("loadData",temptree2);
        $("#authoritychecktree").tree("collapseAll");
        var nodes = $('#authoritychecktree').tree('getChecked', ['checked','indeterminate']);
        for (var i  in nodes){
                $("#authoritychecktree").tree("uncheck",nodes[i].target);
        }
        for (var i  in data.privilege_list){
            var node=$("#authoritychecktree").tree('find',data.privilege_list[i].id);
            if ($("#authoritychecktree").tree('isLeaf',node.target))
                $("#authoritychecktree").tree("check",node.target);
        }
        var nodes = $('#authoritychecktree').tree('getChecked', ['checked','indeterminate']);
        for (var i in nodes) temp.push(nodes[i].id);
    },
    addRoleInfo: function(){
        $("#rolepost-dialog-form").dialog("open");
        $("#rolepost-dialog-form").dialog("center");
        $("#rolepost-dialog-form input").attr("value",' ');
    },
    delRoleInfo: function(){
        var rolet = $('#gerorolepage').datagrid('getSelected');
        var infoUrl=rhurl.origin+"/gero/"+gid+"/role/" + rolet.id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            timeout:1000,
            success:function(){
                role.drawGeroRoleList();
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        })

    },

    onRoleDblClickRow:function(index){
        var rolet = $('#gerorolepage').datagrid('getSelected');
                role.rid="/"+rolet.id;
        var infoUrl=rhurl.origin+"/gero/"+gid+"/role" + role.rid;
        $.ajax({
            type: "get",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url: infoUrl,
            timeout:1000,
            success: function (msg) {
                var data=leftTop.dealdata(msg);
                role.drawRoleInfo(data[0]);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    },

    buttonclk:function(){
        var nodes = $('#authoritychecktree').tree('getChecked', ['checked','indeterminate']);
        for (var i in nodes) temp2.push(nodes[i].id);
        var insert_privilege_ids=[];
        var delete_privilege_ids=[];
        for(var i in temp2) {
            if(temp.indexOf(temp2[i])===-1) insert_privilege_ids.push(temp2[i]);
        }
        for(var i in temp) {
            if(temp2.indexOf(temp[i])===-1) delete_privilege_ids.push(temp[i]);
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/role'+role.rid+'/privilege';
    if(insert_privilege_ids.length>=1){
        $.ajax({
            url: infoUrl, 
            type: 'post', 
            data:JSON.stringify({"insert_privilege_ids":insert_privilege_ids}), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: 1000, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){role.drawGeroRoleList();} 
        }); 
    }
    if(delete_privilege_ids.length>=1){
        $.ajax({
            url: infoUrl, 
            type: 'delete', 
            data:JSON.stringify({"delete_privilege_ids":delete_privilege_ids}), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: 1000, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){role.drawGeroRoleList();} 
        }); 
    }
    },
    postrole:function(){
        var obj={
            name:document.getElementById("rname").value,
            notes:document.getElementById("rnotes").value,
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/role';
        $.ajax({
            url: infoUrl, 
            type: 'post', 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: 1000, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){role.drawGeroRoleList();} 
        }); 
    }
}