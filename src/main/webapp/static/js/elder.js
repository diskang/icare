var elder={
    method:'',
    eid:'',
    drawElderList:function(){
        $("#elder-dialog-form").dialog("close");
        $(".inf").addClass('hide');
	    $("#eldershow").removeClass('hide');
	    $('#elderpage').datagrid({ 
        title:'老人信息列表', 
        iconCls:'icon-edit',//图标 
        height:700,
        nowrap: true, 
        loadMsg:"正在加载，请稍等...", 
        striped: true, 
        border: true, 
        collapsible:false,//是否可折叠的 
        fit: true,//自动大小 
        pageNumber:1,
        url:rhurl.origin+'/gero/'+gid+'/elder',  
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
                elder.addElderInfo();
            } 
        }, '-',{ text: '删除', iconCls: 'icon-remove', 
            handler: function(){ 
                elder.delElderInfo(); 
            }
        }]
    }); 
	    var pager = $('#elderpage').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
		});
    },
    
    drawElderInfo: function(data){
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('#elder-Info-card-a input').attr('disabled','disabled');
        $('#elder-Info-card-a').find('.validatebox-text').validatebox('disableValidation');
        $('#ename').attr('value',data.name);
        // $('#eusername').attr('value',data.username);
        $('#ebirthday').attr('value',data.birthday);
        $('#eage').attr('value',data.age);
        $('#egender').attr('value',sex[data.gender]);
        $('#eaddress').attr('value',data.address);
        $('#enative_place').attr('value',data.native_place);
        $('#earea_id').attr('value',data.area_id);
        $('#ecare_level').attr('value',data.care_level);
        $('#enssf_id').attr('value',data.nssf_id);
        $('#earchive_id').attr('value',data.archive_id);
        $('#enationality').attr('value',data.nationality);
        $('#eeducation').attr('value',data.education);
        $('#eresidence').attr('value',data.residence);
        $('#epolitical_status').attr('value',data.political_status);
        $('#echeckin_date').attr('value',data.checkin_date);
        $('#echeckout_date').attr('value',data.checkout_date);
        $('#epad_mac').attr('value',data.pad_mac);
        $('#eage').attr('value',data.age);
        $('#emarriage').attr('value',data.marriage);
        $('#eidentity_no').attr('value',data.identity_no);

        if(data.photo_url!==undefined) $('#elder-Info-card-b img').attr("src",data.photo_url).attr("width","178px").attr("height","220px");
        else $('#elder-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    addElderInfo: function(){
        elder.eid="";
        elder.method='post';
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('#elder-Info-card-a input').attr('value'," ").removeAttr('disabled');
        $('#elder-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
        $('#elder-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    editElderInfo: function(){
        elder.method='put';
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('#elder-Info-card-a input').removeAttr('disabled');
        $('#elder-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
    },
    delElderInfo: function(){
        var eldert = $('#elderpage').datagrid('getSelected');
        infoUrl=rhurl.origin+"/gero/"+gid+"/elder/" + eldert.elder_id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            success:function(){
                elder.drawElderList();
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        })

    },

    onElderDblClickRow:function(index){
                var eldert = $('#elderpage').datagrid('getSelected');
                elder.eid='/'+eldert.elder_id;
                infoUrl=rhurl.origin+"/gero/"+gid+"/elder" + elder.eid;
                $.ajax({
        			type: "get",
        			dataType: "json",
        			contentType: "application/json;charset=utf-8",
        			url: infoUrl,
        			success: function (msg) {
                        var data=leftTop.dealdata(msg);
                        elder.drawElderInfo(data[0]);
        			},
        			error: function(XMLHttpRequest, textStatus, errorThrown) {
            			leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
        			}
    			});
    },
    buttonclk:function(){
        var obj={
            name:document.getElementById("ename").value,
            username:document.getElementById("eusername").value,
            gender:sexc[document.getElementById("egender").value],
            address:document.getElementById("eaddress").value,
            identity_no:document.getElementById("eidentity_no").value,
            phone_no:document.getElementById("ephone_no").value,
            nssf_id:document.getElementById("enssf_id").value,
            archive_id:document.getElementById("earchive_id").value,
            area_id:document.getElementById("earea_id").value,
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
        var infoUrl=rhurl.origin+'/gero/'+gid+'/elder'+elder.eid;
        $.ajax({
            url: infoUrl, 
            type: elder.method, 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: 1000, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){elder.drawElderList();} 
        }); 
    },
    doSearch:function(){
        $('#elderpage').datagrid('load',{           
                    name: $('#elder_name').val(),
                    area_id: $('#elder_areaid').val(),
                    care_level: $('#elder_care_level').val(),
                });
    }

}

/*
function doSearch(){
				$('#elderpage').datagrid('load',{			
					name: $('#elder_name').val(),
					bed_id: $('#elder_bedid').val(),
					care_level: $('#elder_care_level').val(),
				});
				
			}

function onthClick(e,field){
	$('#elderpage').datagrid({ 
		sortName:field,
		sortOrder:"asc"
	});
}*/