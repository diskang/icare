var relative={
	method:'',
    rid:'',
    uid:"",
    drawRelativeList:function(){
        $("#relative-dialog-form").dialog("close");
        $(".inf").addClass('hide');
	    $("#relativeshow").removeClass('hide');
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
        pageSize: 10,//每页显示的记录条数，默认为20 
        pageList: [10,20,30],//可以设置每页记录条数的列表 
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
        $("#relative-dialog-form").dialog("open");
        $("#relative-dialog-form").dialog("center");
        $('#relative-Info-card-a input').attr('disabled','disabled');
        $('#relative-Info-card-a').find('.validatebox-text').validatebox('disableValidation');
        $('#rname').attr('value',data.name);
        // $('#eusername').attr('value',data.username);
        $('#rbirthday').attr('value',data.birthday);
        $('#rage').attr('value',data.age);
        var radios = document.getElementsByName("rgender");
            for (var i = 0; i < radios.length; i++) {
                if (radios[i].value==data.gender) radios[i].checked="checked";
            }
        $('#raddress').attr('value',data.address);
        $('#rnative_place').attr('value',data.native_place);
        $('#rnssf_id').attr('value',data.nssf_id);
        $('#rarchive_id').attr('value',data.archive_id);
        $('#rnationality').attr('value',data.nationality);
        $('#reducation').attr('value',data.education);
        $('#rresidence').attr('value',data.residence);
        $('#rpolitical_status').attr('value',data.political_status);
        $('#rmarriage').attr('value',data.marriage);
        $('#ridentity_no').attr('value',data.identity_no);
        $('#rphone_no').attr('value',data.phone_no);

        if(data.photo_url!==undefined) $('#relative-Info-card-b img').attr("src",data.photo_src).attr("width","178px").attr("height","220px");
        else $('#relative-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    addRelativeInfo: function(){
        relative.eid="";
        relative.method='post';
        $("#relative-dialog-form").dialog("open");
        $("#relative-dialog-form").dialog("center");
        $('#relative-Info-card-a input').attr('value'," ").removeAttr('disabled');
        $('#relative-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
        $('#relative-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    editRelativeInfo: function(){
        relative.method='put';
        $("#relative-dialog-form").dialog("open");
        $("#relative-dialog-form").dialog("center");
        $('#relative-Info-card-a input').removeAttr('disabled');
        $('#relative-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
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
        var sexc;
        var radios = document.getElementsByName("egender");
            for (var i = 0; i < radios.length; i++) {
                if (radios[i].checked==="checked") sexc=parseInt(radios[i].value);
            }
        var obj={
            name:document.getElementById("ename").value,
            gender:sexc,
            address:document.getElementById("eaddress").value,
            identity_no:document.getElementById("eidentity_no").value,
            phone_no:document.getElementById("ephone_no").value,
            nssf_id:document.getElementById("enssf_id").value,
            archive_id:document.getElementById("earchive_id").value,
            area_id:parseInt(document.getElementById("earea_id").value),
            care_level:document.getElementById("ecare_level").value,
            nationality:document.getElementById("enationality").value,
            native_place:document.getElementById("enative_place").value,
            birthday:document.getElementById("ebirthday").value,
            political_status:document.getElementById("epolitical_status").value,
            education:document.getElementById("eeducation").value,
            residence:document.getElementById("eresidence").value,
            checkin_date:document.getElementById("echeckin_date").value,
            checkout_date:document.getElementById("echeckout_date").value,
            pad_mac:document.getElementById("epad_mac").value,
            age:document.getElementById("eage").value,
            marriage:document.getElementById("emarriage").value,
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
    },
    doSearch:function(){
        $('#relativepage').datagrid('load',{           
                    name: $('#relative_name').val(),
                    area_id: $('#relative_areaid').val(),
                    care_level: $('#relative_care_level').val(),
                });
    }

}