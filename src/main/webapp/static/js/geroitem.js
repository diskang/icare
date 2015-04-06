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
            url:rhurl.origin+'/gero/'+gid+'/care_item',  
            method:'get',
            remoteSort:true,  
            sortName:'ID',
            singleSelect:true,//是否单选 
            pagination:true,//分页控件 
            rownumbers:true,//行号  
            pageNumber:1,
            pagePosition:'bottom',
            pageSize: 35,//每页显示的记录条数，默认为20 
            pageList: [20,35,50],//可以设置每页记录条数的列表  
            loadFilter:function(data){
                leftTop.dealdata(data)
            	var result={"total":0,"rows":0};
                result.total=data.total;
                result.rows=data.entities;
                return result;
            },
            toolbar: [{ 
                text: '添加', 
                iconCls: 'icon-add', 
                handler: function() { 
                    geroItem.addGeroCareItemInfo();
                } 
            }, '-',{ 
            text: '删除', 
                iconCls: 'icon-remove', 
                handler: function(){ 
                    geroItem.delGeroCareItemInfo();
                }
            }]
        }); 
        var pager = $('#gerocareitempage').datagrid('getPager');  // get the pager of datagrid
        pager.pagination({
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        });
    },
    addGeroCareItemInfo: function(){
        $("#gerocareitempost-dialog-form").dialog("open");
        $("#gerocareitempost-dialog-form").dialog("center");
        $("#gerocareitempost-dialog-form input").attr("value",null);
    },
    delGeroCareItemInfo: function(){
        var itemt = $('#gerocareitempage').datagrid('getSelected');
        var infoUrl=rhurl.origin+"/gero/"+gid+"/care_item/" + itemt.id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            success:function(){
                geroItem.drawGeroCareItemList();
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        })

    },
    postcareitem:function(){
        var obj={
            name:document.getElementById("gciname").value,
            level:parseInt(document.getElementById("gcilevel").value),
            notes:document.getElementById("gcinotes").value,
            period:parseInt(document.getElementById("gciperiod").value),
            frequency:parseInt(document.getElementById("gcifrequency").value),
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/care_item';
        $.ajax({
            url: infoUrl, 
            type: 'post', 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){geroItem.drawGeroCareItemList();} 
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
            url:rhurl.origin+'/gero/'+gid+'/area_item',  
            method:'get',
            remoteSort:true,  
            sortName:'ID',  
            singleSelect:true,//是否单选 
            pagination:true,//分页控件 
            rownumbers:true,//行号  
            pageNumber:1,
            pagePosition:'bottom',
            pageSize: 35,//每页显示的记录条数，默认为20 
            pageList: [20,35,50],//可以设置每页记录条数的列表  
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
                    geroItem.addGeroAreaItemInfo();
                } 
            }, '-',{ 
            text: '删除', 
                iconCls: 'icon-remove', 
                handler: function(){ 
                    geroItem.delGeroAreaItemInfo(); 
                }
            }]
        }); 
        var pager = $('#geroareaitempage').datagrid('getPager');  // get the pager of datagrid
        pager.pagination({
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        });
    },
    addGeroAreaItemInfo: function(){
        $("#geroareaitempost-dialog-form").dialog("open");
        $("#geroareaitempost-dialog-form").dialog("center");
        $("#geroareaitempost-dialog-form input").attr("value",null);
    },
    delGeroAreaItemInfo: function(){
        var itemt = $('#gerocareitempage').datagrid('getSelected');
        var infoUrl=rhurl.origin+"/gero/"+gid+"/area_item/" + itemt.id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            timeout:deadtime,
            success:function(){
                geroItem.drawGeroAreaItemList();
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        })

    },
    postareaitem:function(){
          var obj={
            name:document.getElementById("gainame").value,
            notes:document.getElementById("gainotes").value,
            period:parseInt(document.getElementById("gaiperiod").value),
            frequency:parseInt(document.getElementById("gaifrequency").value),
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/area_item';
        $.ajax({
            url: infoUrl, 
            type: 'post', 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){geroItem.drawGeroAreaItemList();} 
        }); 
    }
}