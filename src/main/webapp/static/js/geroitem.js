var geroItem={

    drawGeroCareItemList:function(){
	   $(".inf").addClass('hide');
	   $("#gerocareitemshow").removeClass('hide');
        $('#gerocareitempage').datagrid({ 
            title:'专护项目列表', 
            iconCls:'icon-edit',//图标 
            height:270,
            nowrap: false, 
            loadMsg:"正在加载，请稍等...", 
            striped: true, 
            border: true, 
            collapsible:false,//是否可折叠的 
            fit: true,//自动大小 
            url:rhurl.origin+'/gero/2/care_item',  
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
                result.rows=data.entities;
                return result;
            },
            toolbar: [{ 
                text: '添加', 
                iconCls: 'icon-add', 
                handler: function() { 
                    
                } 
            }, '-',{ 
            text: '删除', 
                iconCls: 'icon-remove', 
                handler: function(){ 
                    //delElderInfo(); 
                }
            }]
        }); 
        var pager = $('#gerocareitempage').datagrid('getPager');  // get the pager of datagrid
        pager.pagination({
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        });
    },







    drawGeroAreaItemList:function(){
       $(".inf").addClass('hide');
       $("#geroareaitemshow").removeClass('hide');
        $('#geroareaitempage').datagrid({ 
            title:'专护项目列表', 
            iconCls:'icon-edit',//图标 
            height:270,
            nowrap: false, 
            loadMsg:"正在加载，请稍等...", 
            striped: true, 
            border: true, 
            collapsible:false,//是否可折叠的 
            fit: true,//自动大小 
            url:rhurl.origin+'/gero/2/area_item',  
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
                result.rows=data.entities;
                return result;
            },
            toolbar: [{ 
                text: '添加', 
                iconCls: 'icon-add', 
                handler: function() { 
                    
                } 
            }, '-',{ 
            text: '删除', 
                iconCls: 'icon-remove', 
                handler: function(){ 
                    //delElderInfo(); 
                }
            }]
        }); 
        var pager = $('#geroareaitempage').datagrid('getPager');  // get the pager of datagrid
        pager.pagination({
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        });
    }
}