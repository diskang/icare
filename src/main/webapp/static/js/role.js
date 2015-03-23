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
        url:'/gero/1/role',  
        method:'get',
        remoteSort:false,  
        singleSelect:true,//是否单选 
        pagination:true,//分页控件 
        rownumbers:true,//行号  
        pageNumber:1,
        pagePosition:'bottom',
        pageSize: 10,//每页显示的记录条数，默认为20 
        pageList: [10,20,30],//可以设置每页记录条数的列表 
        loadFilter:function(data){
        	var result={"total":0,"rows":0};
            result.total=data.total;
            result.rows=data.entities[0];
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
        var infoUrl="gero/1/role/" + rolet.id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            success:function(){
                role.drawRoleList();
            }
        })

    },

    onRoleDblClickRow:function(index){
        var rolet = $('#gerorolepage').datagrid('getSelected');
        var infoUrl="gero/1/role/" + rolet.id;
        role.rid="/"+rolet.id;
        $.ajax({
            type: "get",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url: infoUrl,
            success: function (msg) {
                var data=leftTop.dealdata(msg);
                role.drawRoleInfo(data);
            },
            error: function(e) {
                alert(e);
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
        for(var i in temp2) {
            if(temp2.indexOf(temp[i])===-1) delete_privilege_ids.push(temp[i]);
        }
        var infoUrl='/gero/1/role/1/privilege';
        $.ajax({
            url: infoUrl, 
            type: 'put', 
            data:{"insert_privilege_ids":insert_privilege_ids}, 
            dataType: 'text', 
            timeout: 1000, 
            error: function(){alert('Error');}, 
            success: function(result){role.drawGeroRoleList();} 
        }); 

        $.ajax({
            url: infoUrl, 
            type: 'delete', 
            data:{"delete_privilege_ids":delete_privilege_ids}, 
            dataType: 'text', 
            timeout: 1000, 
            error: function(){alert('Error');}, 
            success: function(result){role.drawGeroRoleList();} 
        }); 
    },
    postrole:function(){
        var obj={
            name:document.getElementById("rname").value,
            notes:document.getElementById("rnotes").value,
        }
        var infoUrl='/gero/1/staff';
        $.ajax({
            url: infoUrl, 
            type: 'post', 
            data:obj, 
            dataType: 'json', 
            timeout: 1000, 
            error: function(){alert('Error');}, 
            success: function(result){role.drawGeroRoleList();} 
        }); 
    }
}