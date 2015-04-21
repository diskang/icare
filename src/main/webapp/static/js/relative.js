var relative={
	method:'',
    flag:false,
    rid:'',
    uid:"",
    eldertemp:[],
    drawRelativeList:function(){
        $("#relative-dialog-form").dialog("close");
        $(".inf").addClass('hide');
	    $("#relativeshow").removeClass('hide');
        $("#relative_name").attr('value',null);
        $("#relative_elderid").attr('value',null);
        $("#relative_eldername").attr('value',null);
        $('#relativepage').datagrid('load',{});
	    $('#relativepage').datagrid({ 
        title:'家属信息列表', 
        iconCls:'icon-edit',//图标 
        height:700,
        nowrap: true, 
        loadMsg:"正在加载，请稍等...", 
        striped: true, 
        border: true, 
        collapsible:false,//是否可折叠的 
        fit: true,//自动大小 
        pageNumber:1,
        url:rhurl.origin+'/gero/'+gid+'/relative',  
        method:'get',
        remoteSort:true,  
        sortName:'ID',
        singleSelect:true,//是否单选 
        pagination:true,//分页控件 
        rownumbers:true,//行号
        pagePosition:'bottom',
        pageSize: 35,//每页显示的记录条数，默认为20 
        pageList: [20,35,50],//可以设置每页记录条数的列表 
        loadFilter:function(data){
        	var result={"total":0,"rows":0};
            leftTop.dealdata(data);
        	result.total=data.total;
        	result.rows=data.entities;
            for (var i in result.rows) result.rows[i].gender=sex[result.rows[i].gender];
        	return result;
        },
        toolbar: [{ text: '添加', iconCls: 'icon-add', 
            handler: function() { 
                relative.addRelativeInfo();
            } 
        }, '-',{ text: '删除', iconCls: 'icon-remove', 
            handler: function(){ 
                relative.delRelativeInfo(); 
            }
        }]
    }); 
	    var pager = $('#relativepage').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
		});
    },
    
    drawRelativeInfo: function(data){
        relative.flag=false;
        $("#relative-dialog-form").dialog("open");
        $("#relative-dialog-form").dialog("center");
        $('#relative-Info-card-a input').attr('disabled','disabled');
        $('#relative-Info-card-a select').attr('disabled','disabled');
        $('#relative-Info-card-a .input-group-addon').addClass('hide');
        $('#rpnote').addClass('hide');
        $('#rpic').removeClass('hide');
        $('#relative-Info-card-a').find('.validatebox-text').validatebox('disableValidation');
        $('#rname').attr('value',data.name);
        $('#rbirthday').attr('value',data.birthday);
        $('#relder_name').attr('value',data.elder_name);
        var radios = document.getElementsByName("rgender");
            for (var i = 0; i < radios.length; i++) {
                radios[i].checked=null;
                if (i==parseInt(data.gender)) radios[i].checked="checked";
            }
        $('#raddress').attr('value',data.address);
        $('#relder_id').attr('value',data.elder_id);
        $('#rnative_place').attr('value',data.native_place);
        $('#rurgent').attr('value',data.urgent);
        $('#rwechat_id').attr('value',data.wechat_id);
        $('#rnationality').attr('value',data.nationality);
        $('#reducation').attr('value',data.education);
        $('#rresidence').attr('value',data.residence);
        $('#rpolitical_status').attr('value',data.political_status);
        $('#rmarriage').attr('value',data.marriage);
        $('#ridentity_no').attr('value',data.identity_no);
        $('#rphone_no').attr('value',data.phone_no);
        $('#rzip_code').attr('value',data.zip_code);
        $('#rregister_date').attr('value',data.register_date);
        $('#rcancel_date').attr('value',data.cancel_date);
        $('#remail').attr('value',data.email);

        if(data.photo_url!==undefined) $('#relative-Info-card-b img').attr("src",data.photo_src).attr("width","178px").attr("height","220px");
        else $('#relative-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    addRelativeInfo: function(){
        relative.eid="";
        relative.method='post';
        relative.flag=false;
        $("#relative-dialog-form").dialog("open");
        $("#relative-dialog-form").dialog("center");
        $('#relative-Info-card-a input').attr('value',null).removeAttr('disabled');
        $('#relative-Info-card-a select').attr('value',null).removeAttr('disabled');
        var radios = document.getElementsByName("rgender");
            for (var i = 0; i < radios.length; i++) {
                radios[i].checked=null;
            }
        $('#relative-Info-card-a .input-group-addon').removeClass('hide');
        $('#relative-Info-card-a').find('.validatebox-text').validatebox('disableValidation');
        $('#rpnote').removeClass('hide');
        $('#rpic').addClass('hide');
        //$('#relative-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
        $('#relative-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    editRelativeInfo: function(){
        relative.flag=true;
        $("#relative-dialog-form").dialog("open");
        $("#relative-dialog-form").dialog("center");
        $('#relative-Info-card-a input').removeAttr('disabled');
        $('#relative-Info-card-a select').removeAttr('disabled');
        $('#relative-Info-card-a .input-group-addon').removeClass('hide');
        $('#relative-Info-card-a').find('.validatebox-text').validatebox('disableValidation');
        $('#rpnote').removeClass('hide');
        //$('#relative-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
    },
    delRelativeInfo: function(){
        var relativet = $('#relativepage').datagrid('getSelected');
        infoUrl=rhurl.origin+"/gero/"+gid+"/relative/" + relativet.relative_id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            timeout:deadtime,
            success:function(){
                relative.drawRelativeList();
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        })

    },

    onRelativeDblClickRow:function(index){
                relative.method='put';
                relative.flag=false;
                var relativet = $('#relativepage').datagrid('getSelected');
                relative.rid='/'+relativet.relative_id;
                relative.uid='/'+relativet.id;
                infoUrl=rhurl.origin+"/gero/"+gid+"/relative" + relative.rid;
                $.ajax({
        			type: "get",
        			dataType: "json",
        			contentType: "application/json;charset=utf-8",
        			url: infoUrl,
                    timeout:deadtime,
        			success: function (msg) {
                        var data=leftTop.dealdata(msg);
                        relative.drawRelativeInfo(data[0]);
        			},
        			error: function(XMLHttpRequest, textStatus, errorThrown) {
            			leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
        			}
    			});
    },
    buttonclk:function(){
        $('#relative-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
        if($('#relder_name').validatebox('isValid') && $('#rname').validatebox('isValid') && $('#rphone_no').validatebox('isValid') && $('#ridentity_no').validatebox('isValid') && $('#rbirthday').validatebox('isValid'))
        {
        var sexc;
        var radios = document.getElementsByName("rgender");
            for (var i = 0; i < radios.length; i++) {
                if (radios[i].checked) sexc=i;
            }
        var obj={
            name:document.getElementById("rname").value,
            gender:sexc,
            address:document.getElementById("raddress").value,
            elder_id:document.getElementById("relder_id").value,
            identity_no:document.getElementById("ridentity_no").value,
            phone_no:document.getElementById("rphone_no").value,
            wechat_id:parseInt(document.getElementById("rwechat_id").value),
            zip_code:document.getElementById("rzip_code").value,
            nationality:document.getElementById("rnationality").value,
            native_place:document.getElementById("rnative_place").value,
            birthday:document.getElementById("rbirthday").value,
            political_status:document.getElementById("rpolitical_status").value,
            education:document.getElementById("reducation").value,
            residence:document.getElementById("rresidence").value,
            email:document.getElementById("remail").value,
            relationship:document.getElementById("rrelationship").value,
            urgent:document.getElementById("rurgent").value,
            register_date:document.getElementById("rregister_date").value,
            cancel_date:document.getElementById("rcancel_date").value,
            marriage:document.getElementById("rmarriage").value,
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/relative'+relative.rid;
        $.ajax({
            url: infoUrl, 
            type: relative.method, 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){relative.drawRelativeList();} 
        }); 
        }
        else alert("请确保输入正确");
    },
    doSearch:function(){
        $('#relativepage').datagrid('load',{           
                    name: $('#relative_name').val(),
                    elder_id: $('#relative_elderid').val(),
                });
    },
    elder_idclick:function(){
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
                eldervalue='#relder_id';
                eldernamevalue='#relder_name';
                var node=$("#elderchoosetree").tree('find',parseInt($('#relder_id').val()));
                if(node)$("#elderchoosetree").tree("check",node.target);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    },
    searchelder_id:function(){
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
                eldervalue='#relative_elderid';
                eldernamevalue='#relative_eldername';
                var node=$("#elderchoosetree").tree('find',parseInt($('#relative_elderid').val()));
                if(node)$("#elderchoosetree").tree("check",node.target);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    },
    reset:function(){
        $("#relative_name").attr('value',null);
        $("#relative_elderid").attr('value',null);
        $("#relative_eldername").attr('value',null);
    }

}