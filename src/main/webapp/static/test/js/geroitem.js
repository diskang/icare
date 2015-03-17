var geroItem={

    drawGeroItemList:function(){
	   $(".inf").addClass('hide');
	   $("#geroitemshow").removeClass('hide');
	   $('#aa').accordion({
    	   animate:true,
        });
        $('#geroitempage').datagrid({ 
            title:'专护项目列表', 
            iconCls:'icon-edit',//图标 
            height:270,
            nowrap: false, 
            loadMsg:"正在加载，请稍等...", 
            striped: true, 
            border: true, 
            collapsible:false,//是否可折叠的 
            fit: true,//自动大小 
            url:'/care_item',  
            method:'get',
            remoteSort:false,  
            singleSelect:true,//是否单选 
            pagination:true,//分页控件 
            rownumbers:true,//行号  
            pageNumber:1,
            loadFilter:function(data){
            	var result={"total":0,"rows":0};
        	   result.total=data.total;
        	   result.rows=data.itemList;
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
    }
}