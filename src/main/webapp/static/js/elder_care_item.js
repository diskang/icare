var care_item={
    eid:'',
    itemtemp:[],
    drawItemList:function(){
       $(".inf").addClass('hide');
       $("#eldercareitemshow").removeClass('hide');
       care_item.doSearch();
    },
    addItemInfo: function(){
        $('#eldercareitem-dialog-form').dialog('open');
        $("#eldercareitem-dialog-form").dialog("center");
        $('#careitemchoosetree li').remove();
        $('#careitemchoosetree ul').remove();
        $.ajax({
            type: "get",
            data:{page:1,rows:65535,sort:'ID'},
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url:rhurl.origin+'/gero/'+gid+'/care_item',
            timeout:deadtime,
            success: function (msg) {
                care_item.itemtemp=[];
                for(var i in msg.entities){
                    var temp={
                        id:msg.entities[i].id,
                        text:msg.entities[i].name,
                        attributes:{icon:msg.entities[i].icon,level:msg.entities[i].level,period:msg.entities[i].period,start_time:msg.entities[i].start_time,end_time:msg.entities[i].end_time},
                        iconCls:'icon-blank',
                    }
                    care_item.itemtemp.push(temp);
                }
                $("#careitemchoosetree").tree("loadData",care_item.itemtemp);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    },
    delItemInfo: function(){
        var itemt = $('#eldercareitempage').datagrid('getSelected');
        var infoUrl=rhurl.origin+"/gero/"+gid+'/elder/'+care_item.eid+"/care_item/" + itemt.id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            success:function(){
                care_item.doSearch();
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        })

    },

    chooseelder:function(){
        $('#elderchoose-dialog-form').dialog('open');
        $("#elderchoose-dialog-form").dialog("center");
        $('#elderchoosetree li').remove();
        $('#elderchoosetree ul').remove();
        $.ajax({
            type: "get",
            data:{page:1,rows:65535,sort:'ID'},
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url:rhurl.origin+'/gero/'+gid+'/elder',
            timeout:deadtime,
            success: function (msg) {
                relative.eldertemp=[];
                for(var i in msg.entities){
                    var temp={
                        id:msg.entities[i].elder_id,
                        text:msg.entities[i].name,
                        iconCls:'icon-blank',
                    }
                    relative.eldertemp.push(temp);
                }
                $("#elderchoosetree").tree("loadData",relative.eldertemp);

                eldervalue='#care_item_elder_id';
                eldernamevalue='#care_item_elder_name';
                var node=$("#elderchoosetree").tree('find',parseInt($('#care_item_elder_id').val()));
                if(node) $("#elderchoosetree").tree("select",node.target);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    },
    doSearch:function(){
        care_item.eid=$('#care_item_elder_id').val();
        $('#eldercareitempage').datagrid({ 
            title:elderchoosename+'专护项目列表', 
            iconCls:'icon-edit',//图标 
            height:400,
            fit:true,
            nowrap: false, 
            loadMsg:"正在加载，请稍等...", 
            striped: true, 
            border: true, 
            collapsible:false,//是否可折叠的 
            url:rhurl.origin+'/gero/'+gid+'/elder/'+care_item.eid+'/care_item',  
            method:'get',
            remoteSort:true,  
            sortName:'ID',
            singleSelect:true,//是否单选 
            pagination:true,//分页控件 
            rownumbers:true,//行号  
            pageNumber:1,
            pagePosition:'bottom',
            pageSize: 20,//每页显示的记录条数，默认为20 
            pageList: [20,30,40],//可以设置每页记录条数的列表 
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
                    if(care_item.eid!=="")
                    care_item.addItemInfo();
                } 
            }, '-',{ 
            text: '删除', 
                iconCls: 'icon-remove', 
                handler: function(){
                    if(care_item.eid!=="")
                    care_item.delItemInfo();
                }
            }]
        }); 
        var pager = $('#eldercareitempage').datagrid('getPager');  // get the pager of datagrid
        pager.pagination({
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        });
    },
    postitem:function(id){
        var obj={
            care_item_id:id,
            level:$("#cilevel").val(),
            start_time:$("#cistime").val(),
            end_time:$("#cietime").val(),
            icon:$("#ciicon").val(),
            period:$("#ciperiod").val(),
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/elder/'+care_item.eid+'/care_item';  
        $.ajax({
            url: infoUrl, 
            type:'post', 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){care_item.doSearch();} 
        }); 
    }

}