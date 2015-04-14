var elder={
    method:'',
    eid:'',
    uid:"",
    temparea:[],
    areatemp:[],
    drawElderList:function(){
        $("#elder-dialog-form").dialog("close");
        $(".inf").addClass('hide');
	    $("#eldershow").removeClass('hide');
        $("#elder_areafullname").attr('value',null);
        $("#elder_areaid").attr('value',null);
        $("#elder_name").attr('value',null);
        $("#elder_care_level").attr('value',null);
        $('#elderpage').datagrid('load',{});
	    $('#elderpage').datagrid({ 
        title:'老人信息列表', 
        iconCls:'icon-edit',//图标 
        fit:true,//自动大小 
        nowrap: false, 
        loadMsg:"正在加载，请稍等...", 
        striped: true, 
        border: true, 
        collapsible:false,//是否可折叠的 
        url:rhurl.origin+'/gero/'+gid+'/elder',  
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
        	var result={"total":0,"rows":0};
            leftTop.dealdata(data);
        	result.total=data.total;
        	result.rows=data.entities;
            for (var i in result.rows) {
                result.rows[i].gender=sex[result.rows[i].gender];
                result.rows[i].care_level=clevellist[result.rows[i].care_level];
            }
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
        },'-',{ text: '查看项目', iconCls: 'icon-search', 
            handler: function(){ 
                elder.searchitem(); 
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
        $('#elder-Info-card-a .input-group-addon').addClass('hide');
        $('#elder-Info-card-a').find('.validatebox-text').validatebox('disableValidation');
        $('#ename').attr('value',data.name);
        $('#ebirthday').attr('value',data.birthday);
        var radios = document.getElementsByName("egender");
            for (var i = 0; i < radios.length; i++) {
                if (radios[i].getAttribute('value')==data.gender) radios[i].checked="checked";
            }
        $('#eaddress').attr('value',data.address);
        $('#enative_place').attr('value',data.native_place);
        $('#earea_id').attr('value',data.area_id);
        $('#earea_fullname').attr('value',data.area_fullname);
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
        $('#emarriage').attr('value',data.marriage);
        $('#eidentity_no').attr('value',data.identity_no);

        if(data.photo_url!==undefined) $('#elder-Info-card-b img').attr("src",data.photo_src).attr("width","178px").attr("height","220px");
        else $('#elder-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    addElderInfo: function(){
        elder.eid="";
        elder.method='post';
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('#elder-Info-card-a input').attr('value',null).removeAttr('disabled');
        $('#elder-Info-card-a .input-group-addon').removeClass('hide');
        $('#elder-Info-card-a').find('.validatebox-text').validatebox('disableValidation');
        //$('#elder-Info-card-a').find('.easyui-validatebox').validatebox('enableValidation').validatebox('validate');
        $('#elder-Info-card-b img').attr("src",rhurl.staticurl+"/images/p_2.jpg").attr("width","178px").attr("height","220px");
    },

    editElderInfo: function(){
        elder.method='put';
        $("#elder-dialog-form").dialog("open");
        $("#elder-dialog-form").dialog("center");
        $('#elder-Info-card-a input').removeAttr('disabled');
        $('#elder-Info-card-a .input-group-addon').removeClass('hide');
        $('#elder-Info-card-a').find('.validatebox-text').validatebox('disableValidation');
        //$('#elder-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
    },
    delElderInfo: function(){
        var eldert = $('#elderpage').datagrid('getSelected');
        if(eldert){
            infoUrl=rhurl.origin+"/gero/"+gid+"/elder/" + eldert.elder_id;
            if (confirm('确定要删除？')) {
                $.ajax({
                    url: infoUrl,
                    type: 'DELETE',
                    timeout:deadtime,
                    success:function(){
                        elder.drawElderList();
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
                    }
                })
            }
        }
    },
    searchitem:function(){
        var eldert = $('#elderpage').datagrid('getSelected');
        if(eldert){
            infoUrl=rhurl.origin+"/gero/"+gid+"/elder/" + eldert.elder_id+"/care_item";
            $.ajax({
                url: infoUrl,
                data:{page:1,rows:65535,sort:'ID'},
                type: 'get',
                timeout:deadtime,
                success:function(){
                    alert(1);
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
                }
            })
        }
    },

    onElderDblClickRow:function(index){
                elder.method='put';
                var eldert = $('#elderpage').datagrid('getSelected');
                elder.eid='/'+eldert.elder_id;
                elder.uid='/'+eldert.id;
                infoUrl=rhurl.origin+"/gero/"+gid+"/elder" + elder.eid;
                $.ajax({
        			type: "get",
        			dataType: "json",
        			contentType: "application/json;charset=utf-8",
        			url: infoUrl,
                    timeout:deadtime,
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
        $('#elder-Info-card-a').find('.validatebox-text').validatebox('enableValidation').validatebox('validate');
        if($('#ename').validatebox('isValid') && $('#ephone_no').validatebox('isValid') && $('#eidentity_no').validatebox('isValid') && $('#earea_fullname').validatebox('isValid') && $('#ebirthday').validatebox('isValid'))
        {
        var sexc;
        var radios = document.getElementsByName("egender");
            for (var i = 0; i < radios.length; i++) {
                if (radios[i].checked) sexc=i;
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
            marriage:document.getElementById("emarriage").value,
        }
        var infoUrl=rhurl.origin+'/gero/'+gid+'/elder'+elder.eid;
        $.ajax({
            url: infoUrl, 
            type: elder.method, 
            data:JSON.stringify(obj), 
            dataType: 'json', 
            contentType: "application/json;charset=utf-8",
            timeout: deadtime, 
            error: function(XMLHttpRequest, textStatus, errorThrown){leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);}, 
            success: function(msg){
                elder.drawElderList();
                if (elder.method==='post'){
                    elder.method='put';
                    var data=leftTop.dealdata(msg);
                        elder.drawElderInfo(data[0]);
                }

            } 
        }); 
        }
        else(alert('请确保输入正确'));
    },
    doSearch:function(){
        $('#elderpage').datagrid('load',{           
                    name: $('#elder_name').val(),
                    area_id: $('#elder_areaid').val(),
                    care_level: $('#elder_care_level').val(),
                });
    },
    reset:function(){
        $('#elder_name').attr('value',null);
        $('#elder_areaid').attr('value',null);
        $('#elder_areafullname').attr('value',null);
        $('#elder_care_level').attr('value',null);
    },


    createTreeNode:function(node){
        this.id=node.id;
        this.text=node.name;
        this.children=[];
        iconCls='icon-blank';
        this.attributes={"type":node.type,"level":node.level,'fullname':node.full_name}
    },
    findTreeChildren:function(id){
        var result=[];
        for(var i in elder.temparea){
            if(elder.temparea[i].parent_id===id){
                result.push(elder.temparea[i]);
            }
        }
        return result;
    },
    createTreeData:function(node){
        var result=[];
        var childs= elder.findTreeChildren(node.id);
        if (childs.length!==0){
            for(var i in childs){
                var temp= new elder.createTreeNode(childs[i]);
                if (elder.findTreeChildren(childs[i].id).length!==0){
                    temp.children=elder.createTreeData(childs[i]);
                }
                result.push(temp);
            }
        }
        return result;
    },
    area_idclick:function(){
        $('#area-dialog-form').dialog('open');
        $("#area-dialog-form").dialog("center");
        $('#areachoosetree li').remove();
        $('#areachoosetree ul').remove();
        $.ajax({
            type: "get",
            data:{page:1,rows:65535,sort:'ID'},
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url:rhurl.origin+'/gero/'+gid+'/area',
            timeout:deadtime,
            success: function (msg) {
                elder.temparea=msg.entities;
                elder.areatemp=elder.createTreeData({"id":0,"types":0});
                $("#areachoosetree").tree("loadData",elder.areatemp);
                areavalue="#earea_id";
                areanamevalue="#earea_fullname";
                var node=$("#areachoosetree").tree('find',parseInt($('#earea_id').val()));
                if(node)$("#areachoosetree").tree("check",node.target);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    },
    searcharea_id:function(){
        $('#area-dialog-form').dialog('open');
        $("#area-dialog-form").dialog("center");
        $('#areachoosetree li').remove();
        $('#areachoosetree ul').remove();
        $.ajax({
            type: "get",
            data:{page:1,rows:65535,sort:'ID'},
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            url:rhurl.origin+'/gero/'+gid+'/area',
            timeout:deadtime,
            success: function (msg) {
                elder.temparea=msg.entities;
                elder.areatemp=elder.createTreeData({"id":0,"types":0});
                $("#areachoosetree").tree("loadData",elder.areatemp);
                areavalue="#elder_areaid";
                areanamevalue="#elder_areafullname";
                var node=$("#areachoosetree").tree('find',parseInt($('#elder_areaid').val()));
                if(node)$("#areachoosetree").tree("check",node.target);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    }

}
