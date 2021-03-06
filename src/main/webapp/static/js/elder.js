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
        url:'/gero/1/elder',  
        method:'get',
        remoteSort:false,  
        singleSelect:true,//是否单选 
        pagination:true,//分页控件 
        rownumbers:true,//行号
        pagePosition:'bottom',
        pageSize: 10,//每页显示的记录条数，默认为20 
        pageList: [10,20,30],//可以设置每页记录条数的列表 
        loadFilter:function(data){
        	var result={"total":0,"rows":0};
        	result.total=data.total;
        	result.rows=data.entities[0];
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
        elder.eid="/"+data.id;
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('#elder-Info-card-a input').attr('disabled','disabled');
        $('#ename').attr('value',data.name);
        $('#ebirthday').attr('value',data.birthday);
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
        $('#eidentity_no').attr('value',data.identity_no);
        $('#ephone').attr('value',data.phone);
        if(data.photo_url!==undefined) $('#elder-Info-card-b img').attr("src",data.photo_url).attr("width","178px").attr("height","220px");
        else $('#elder-Info-card-b img').attr("src","/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    addElderInfo: function(){
        elder.eid="";
        elder.method='post';
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('#elder-Info-card-a input').attr('value'," ").removeAttr('disabled','');
        $('#elder-Info-card-b img').attr("src","/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    editElderInfo: function(){
        elder.method='put';
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('#elder-Info-card-a input').removeAttr('disabled','');
    },
    delElderInfo: function(){
        var eldert = $('#elderpage').datagrid('getSelected');
        infoUrl="gero/1/elder/" + eldert.id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            success:function(){
                elder.drawElderList();
            }
        })

    },

    onElderDblClickRow:function(index){
                var eldert = $('#elderpage').datagrid('getSelected');
                infoUrl="gero/1/elder/" + eldert.id;
                $.ajax({
        			type: "get",
        			dataType: "json",
        			contentType: "application/json;charset=utf-8",
        			url: infoUrl,
        			success: function (msg) {
                        var data=leftTop.dealdata(msg);
                        elder.drawElderInfo(data);
        			},
        			error: function(e) {
            			alert(e);
        			}
    			});
    },
    buttonclk:function(){
        var obj={
            name:document.getElementById("ename").value,
            gender:sexc[document.getElementById("egender").value],
            address:document.getElementById("eaddress").value,
            identity_no:document.getElementById("eidentity_no").value,
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
        }
        var infoUrl='/gero/1/elder'+elder.eid;
        $.ajax({
            url: infoUrl, 
            type: elder.method, 
            data:obj, 
            dataType: 'json', 
            timeout: 1000, 
            error: function(){alert('Error');}, 
            success: function(result){elder.drawElderList();} 
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