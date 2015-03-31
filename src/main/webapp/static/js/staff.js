var staff={
    method:'',
    sid:'',
    drawStaffList:function(){
        $("#staff-dialog-form").dialog("close");
        $(".inf").addClass('hide');
	    $("#staffshow").removeClass('hide');
	    $('#staffpage').datagrid({ 
            title:'员工信息列表', 
            iconCls:'icon-edit',//图标 
            fit:true,
            nowrap: false, 
            loadMsg:"正在加载，请稍等...", 
            striped: true, 
            border: true, 
            collapsible:false,//是否可折叠的 
            url:rhurl.origin+'/gero/'+gid+'/staff',  
            method:'get',
            remoteSort:true,  
            sortName:'ID',
            singleSelect:true,//是否单选 
            pagination:true,//分页控件 
            rownumbers:true,//行号  
            pageNumber:1,
            pagePosition:'bottom',
            pageSize: 10,//每页显示的记录条数，默认为20 
            pageList: [10,20,30],//可以设置每页记录条数的列表 
            loadFilter:function(data){
                leftTop.dealdata(data);
        	    var result={"total":0,"rows":0};
                result.total=data.total;
                result.rows=data.entities;
                for (var i in result.rows) {
                    result.rows[i].gender=sex[result.rows[i].gender];
                    var roletemp='';
                    for(var j in result.rows[i].role_list){
                        roletemp+=result.rows[i].role_list[j].name;
                    }
                    result.rows[i].role=roletemp;
                }
                return result;
            },
            toolbar: [{ 
                text: '添加', 
                iconCls: 'icon-add', 
                handler: function() { 
                     staff.addStaffInfo();
                } 
            }, '-',{ 
                text: '删除', 
                iconCls: 'icon-remove', 
                handler: function(){ 
                    staff.delStaffInfo(); 
                }
            }]
        }); 
	    var pager = $('#staffpage').datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        /*onBeforeRefresh:function(){
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }*/ 
		});
    },

     drawStaffInfo: function(data){
        staff.sid="/"+data.id;
        var rolestr='';
        $('.checkrole').attr("checked",false);
        for(var i in data.role_list){
            $("#chkrole"+data.role_list[i].role_id).attr("checked","true");
        };
        $("#staff-dialog-form").dialog("open");
        $("#staff-dialog-form").dialog("center");
        $('#staff-Info-card-a input').attr('disabled','disabled');
        $("#staff-Info-card-a").find('.validatebox-text').validatebox('disableValidation');
        $('#sname').attr('value',data.name);
        $('#semail').attr('value',data.email);
        $('#sbirthday').attr('value',data.birthday);
        $('#sgender').attr('value',sex[data.gender]);
        $('#shousehold_address').attr('value',data.household_address);
        $('#snssf_id').attr('value',data.nssf_id);
        $('#sarchive_id').attr('value',data.archive_id);
        $('#sresidence_address').attr('value',data.residence_address);
        $('#sidentity_no').attr('value',data.identity_no);
        $('#sphone_no').attr('value',data.phone_no);
        $('#susername').attr('value',data.username);
        $('#sregister_date').attr('value',data.register_date);
        $('#scancel_date').attr('value',data.cancel_date);
        $('#snationality').attr('value',data.nationality);
        $('#smarriage').attr('value',data.marriage);
        $('#snative_place').attr('value',data.native_place);
        $('#szip_code').attr('value',data.zip_code);
        $('#swechat_id').attr('value',data.wechat_id);
        $('#sbasic_url').attr('value',data.basic_url);
        $('#sleave_date').attr('value',data.leave_date);
        $('#sage').attr('value',data.age);
        $('#spolitical_status').attr('value',data.political_status);
        if(data.photo_url!==undefined) $('#staff-Info-card-b img').attr("src",data.photo_url).attr("width","178px").attr("height","220px");
        else $('#staff-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    addStaffInfo: function(){
        staff.sid="";
        staff.method='post';
        $("#staff-dialog-form").dialog("open");
        $("#staff-dialog-form").dialog("center");
        $('.checkrole').attr("checked",false);
        $('.checkrole').attr("disabled",false);
        $('#staff-Info-card-a input').attr('value'," ").removeAttr('disabled','');
        $("#staff-Info-card-a").find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
        $('#staff-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    editStaffInfo: function(){
        staff.method='put';
        $("#staff-dialog-form").dialog("open");
        $("#staff-dialog-form").dialog("center");
        $('#staff-Info-card-a input').removeAttr('disabled','');
        $('.checkrole').attr("disabled",false);
        $("#staff-Info-card-a").find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
    },
    delStaffInfo: function(){
        var stafft = $('#staffpage').datagrid('getSelected');
        var infoUrl=rhurl.origin+"/gero/"+gid+"/staff/" + stafft.id;
        $.ajax({
            url: infoUrl,
            type: 'DELETE',
            success:function(){
                staff.drawstaffList();
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        })

    },


    onStaffDblClickRow:function(index){
                var stafft = $('#staffpage').datagrid('getSelected');
                infoUrl=rhurl.origin+"/gero/"+gid+"/staff/" + stafft.id;
                $.ajax({
                    type: "get",
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    url: infoUrl,
                    success: function (msg) {
                        var data=leftTop.dealdata(msg);
                        staff.drawStaffInfo(data[0]);
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        leftTop.error(XMLHttpRequest, textStatus, errorThrown);
                    }
                });
    },

    buttonclk:function(){
        var obj={
            name:document.getElementById("sname").value,
            gender:sexc[document.getElementById("sgender").value],
            household_address:document.getElementById("shousehold_address").value,
            identity_no:document.getElementById("sidentity_no").value,
            nssf_id:document.getElementById("snssf_id").value,
            archive_id:document.getElementById("sarchive_id").value,
            email:document.getElementById("semail").value,
            phone:document.getElementById("sphone").value,
            birthday:document.getElementById("sbirthday").value,
            residence_address:document.getElementById("sresidence_address").value,
            username:document.getElementById("susername").value,
            register_date:document.getElementById("sregister_date").value,
            cancel_date:document.getElementById("scancel_date").value,
            nationality:document.getElementById("snationality").value,
            marriage:document.getElementById("smarriage").value,
            native_place:document.getElementById("snative_place").value,
            zip_code:document.getElementById("szip_code").value,
            wechat_id:document.getElementById("swechat_id").value,
            basic_url:document.getElementById("sbasic_url").value,
            leave_date:document.getElementById("sleave_date"),
            age:document.getElementById("sage").value,
            political_status:document.getElementById("spolitical_status").value,
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/staff'+staff.sid;
        $.ajax({
            url: infoUrl, 
            type: staff.method, 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: 1000, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){staff.drawStaffList();} 
        }); 
        var roleobj={ids:[]};
        var parentBox = document.getElementById("role-check");
        var inputs = parentBox.getElementsByTagName("input");
        for(var i=0;i<inputs.length;i++){
            if(inputs[i].type=="checkbox" && inputs[i].checked){
                roleobj.ids.push(parseInt(inputs[i].getAttribute("rid")));
            }
        }
        if(staff.sid!==''){
        $.ajax({
            url: rhurl.origin+'/user'+staff.sid+'/role', 
            type: 'put', 
            data: JSON.stringify(roleobj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: 1000, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(result){staff.drawStaffList();} 
        }); 
        }
    },
    doSearch:function(){
        $('#staffpage').datagrid('load',{           
                    name: $('#staff_name').val(),
                    role: $('#staff_role').val(),
                    identity_no: $('#staff_identity_no').val(),
                });
    }
}