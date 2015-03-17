var role={
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
        singleSelect:false,//是否单选 
        pagination:true,//分页控件 
        rownumbers:true,//行号  
        pageNumber:1,
        loadFilter:function(data){
        	var result={"total":0,"rows":0};
        	result.total=data.total;
        	result.rows=data.roleList;
        	return result;
        },
        toolbar: [{ 
            text: '添加', 
            iconCls: 'icon-add', 
            handler: function() { 
                
            } 
        },'-',{ 
            text: '修改', 
            iconCls: 'icon-edit', 
            handler: function(){ 
                //delElderInfo(); 
            }
        },'-',{ 
            text: '删除', 
            iconCls: 'icon-remove', 
            handler: function(){ 
                //delElderInfo(); 
            }
        }]
    }); 
	var pager = $('#gerorolepage').datagrid().datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			pageSize: 10,//每页显示的记录条数，默认为20 
        	pageList: [5,10,15],//可以设置每页记录条数的列表 
        	beforePageText: '第',//页数文本框前显示的汉字 
        	afterPageText: '页    共 {pages} 页', 
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        /*onBeforeRefresh:function(){
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }*/ 

		});
    },

    onRoleDblClickRow:function(index){
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('.info-card').addClass('hide');
        $('#authority-Info-card').removeClass('hide')
        }
}