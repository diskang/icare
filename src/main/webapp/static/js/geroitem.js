var geroItem={
    gciid:'',
    gcimethod:'',
    gaiid:'',
    gaimethod:'',
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
    drawGCIInfo:function(data){
        $("#gerocareitempost-dialog-form").dialog("open");
        $("#gerocareitempost-dialog-form").dialog("center");
        $("#gerocareitempost-dialog-form input").attr("value",null);
        $('#gciname').attr('value',data.name);
        $('#gciicon').attr('value',data.icon);
        $('#gcilevel').attr('value',data.level);
        $('#gciperiod').attr('value',data.period);
        $('#gcifrequency').attr('value',data.frequency);
        $('#gcinotes').attr('value',data.notes);
        $('#gcistart_time').attr('value',data.start_time);
        $('#gciend_time').attr('value',data.end_time);
    },
    addGeroCareItemInfo: function(){
        geroItem.gcimethod='post';
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
    onGCIDblClickRow:function(index){
                geroItem.gcimethod='put';
                var item = $('#gerocareitempage').datagrid('getSelected');
                geroItem.gciid='/'+item.id;
                infoUrl=rhurl.origin+"/gero/"+gid+"/care_item" + geroItem.gciid;
                $.ajax({
                    type: "get",
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    url: infoUrl,
                    timeout:deadtime,
                    success: function (msg) {
                        var data=leftTop.dealdata(msg);
                        geroItem.drawGCIInfo(data[0]);
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
                    }
                });
    },
    clickcareitem:function(){
        var obj={
            name:document.getElementById("gciname").value,
            level:parseInt(document.getElementById("gcilevel").value),
            notes:document.getElementById("gcinotes").value,
            period:parseInt(document.getElementById("gciperiod").value),
            frequency:parseInt(document.getElementById("gcifrequency").value),
            icon:$("#gciicon").val(),
            start_time:$("#gcistart_time").val(),
            end_time:$("#gciend_time").val(),
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/care_item'+geroItem.gciid;
        $.ajax({
            url: infoUrl, 
            type: geroItem.gcimethod, 
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
            title:'房护项目列表', 
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
        geroItem.gaiid='post';
        $("#geroareaitempost-dialog-form").dialog("open");
        $("#geroareaitempost-dialog-form").dialog("center");
        $("#geroareaitempost-dialog-form input").attr("value",null);
    },
    delGeroAreaItemInfo: function(){
        var itemt = $('#geroareaitempage').datagrid('getSelected');
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
    drawGAIInfo:function(data){
        $("#geroareaitempost-dialog-form").dialog("open");
        $("#geroareaitempost-dialog-form").dialog("center");
        $("#geroareaitempost-dialog-form input").attr("value",null);
        $('#gainame').attr('value',data.name);
        $('#gaiicon').attr('value',data.icon);
        $('#gaiperiod').attr('value',data.period);
        $('#gaifrequency').attr('value',data.frequency);
        $('#gainotes').attr('value',data.notes);
    },
    onGAIDblClickRow:function(index){
                geroItem.gaimethod='put';
                var item = $('#geroareaitempage').datagrid('getSelected');
                geroItem.gaiid='/'+item.id;
                infoUrl=rhurl.origin+"/gero/"+gid+"/area_item" + geroItem.gaiid;
                $.ajax({
                    type: "get",
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    url: infoUrl,
                    timeout:deadtime,
                    success: function (msg) {
                        var data=leftTop.dealdata(msg);
                        geroItem.drawGAIInfo(data[0]);
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
                    }
                });
    },
    clickareaitem:function(){
        var obj={
            name:document.getElementById("gainame").value,
            notes:document.getElementById("gainotes").value,
            period:parseInt(document.getElementById("gaiperiod").value),
            frequency:parseInt(document.getElementById("gaifrequency").value),
            icon:document.getElementById("gaiicon").value,
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/area_item'+geroItem.gaiid;
        $.ajax({
            url: infoUrl, 
            type: geroItem.gaimethod, 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){geroItem.drawGeroAreaItemList();} 
        }); 
    }
}