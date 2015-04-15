var eldercare={
  eldertemp:[],
  name:'',
  carework_id:'',
  method:'',
  sid:'',
  item_id:'',
  obj:{ staff_id:'',
        elder_ids:[],
        end_date:'',
  },
  careid:-1,
  caretemp:[],
	drawElderCareList:function(){
    min=timeline.getCurrentTime();
		$(".inf").addClass('hide');
		$("#eldercareshow").removeClass('hide');
    $("#elderdutypanel").addClass('hide');
    eldercare.updateeldertree();
  },

  init:function(){
    container = document.getElementById('careitemvision');
    options = {
      editable:{add:true,
        remove:true},
      margin:{item:0},
      dataAttributes:['all'],
      onAdd: function (item, callback) {
        eldercare.method='post';
        eldercare.carework_id='';
        $("#elderdutypanel").removeClass('hide')
        $("#elderchecktree").tree("loadData",eldercare.eldertemp);
        $("#eldercarer-end").attr('value',null);
        callback(null); // cancel item creation
      },

      onUpdate: function (item, callback) {
        eldercare.method='put';
        eldercare.carework_id='/'+item.id;
        $("#elderchecktree").tree("loadData",eldercare.eldertemp);
        for(var i in item.elder_ids){
          var node=$("#elderchecktree").tree('find',item.elder_ids[i]);
            if(node)$("#elderchecktree").tree("check",node.target);
        }
        $("#elderdutypanel").removeClass('hide')
        $("#eldercarer-end").attr('value',item.end);
        callback(null); // cancel updating the item
      },

      onRemove: function (item, callback) {
        if (confirm('确定要删除？')) {
          var infoUrl=rhurl.origin+'/gero/'+gid+'/carework/'+item.id;
          $.ajax({
            url: infoUrl, 
            type:'delete', 
            timeout:deadtime,
            error: function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }, 
            success: function(result){
                eldercare.drawElderCareList();
            } 
        }); 
        }
        callback(null); // cancel deletion

      }
    };
    timeline = new vis.Timeline(container, [], options);
  },

  updateeldercarer:function(){
    $('#eldercarercont li').remove();
    $.ajax({
        type: "get",
        data:{page:1,rows:65535,sort:'ID',role:"老人护工"},
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url:rhurl.origin+'/gero/'+gid+'/staff',
        timeout:deadtime,
        success: function (msg) {
            if(eldercare.careid==-1) eldercare.careid=msg.entities[0].staff_id;
            eldercare.caretemp=[];
            for(var i in msg.entities){
            var temp={
              id:msg.entities[i].staff_id,
              text:msg.entities[i].name,
              iconCls:'icon-blank',
            }
            eldercare.caretemp.push(temp);
          }
          $("#eldercarercont").tree("loadData",eldercare.caretemp);
          var node = $('#eldercarercont').tree('find',eldercare.careid);
          $('#eldercarercont').tree('select', node.target);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
        }
    });
  },
  updateeldertree:function(){
    $('#elderchecktree li').remove();
    $.ajax({
        type: "get",
        data:{page:1,rows:65535,sort:'ID'},
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url:rhurl.origin+'/gero/'+gid+'/elder',
        timeout:deadtime,
        success: function (msg) {
          eldercare.eldertemp=[];
          for(var i in msg.entities){
            var temp={
              id:msg.entities[i].elder_id,
              text:msg.entities[i].name,
              iconCls:'icon-blank',
            }
            eldercare.eldertemp.push(temp);
          }
          $("#elderchecktree").tree("loadData",eldercare.eldertemp);
          eldercare.updateeldercarer();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
        }
    });

  },
  getList:function(id ,name){
    eldercare.careid=id;
    timeline.clear({items:true});
    $("#elderdutypanel").addClass('hide');
    eldercare.name=name;
    eldercare.sid=id;
    $.ajax({
        type: "get",
        data:{page:1,rows:65535,sort:'ID',"staff_id":id},
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url:rhurl.origin+'/gero/'+gid+'/carework',
        timeout:deadtime,
        success: function (msg) {
          var cache=[];
          var cache2=msg.entities;
          var tem;
          for(var i=0;i<cache2.length-1;i++){
            for (var j=i+1;j<cache2.length;j++){
              if(cache2[i].end_date>cache2[j].end_date){
                tem=cache2[i];cache2[i]=cache2[j];cache2[j]=tem;
              }
            }
          }
          for(var i in cache2){
            var result;
            if(i!=0) {result={id:msg.entities[i].id,content:eldercare.name,start:cache2[i-1].end_date,end:cache2[i].end_date,elder_ids:cache2[i].elder_ids}}
              else {result={id:msg.entities[i].id,content:eldercare.name,start:min,end:cache2[i].end_date,elder_ids:cache2[i].elder_ids}}
            cache.push(result);
          }
          var items2 = new vis.DataSet(cache);
          timeline.setItems(items2);
          

          timeline.setSelection(cache[0].id);
          eldercare.method='put';
          eldercare.carework_id='/'+cache[0].id;
          $("#elderchecktree").tree("loadData",eldercare.eldertemp);
          for(var i in cache[0].elder_ids){
            var node=$("#elderchecktree").tree('find',cache[0].elder_ids[i]);
              if(node)$("#elderchecktree").tree("check",node.target);
          }
          $("#elderdutypanel").removeClass('hide')
          $("#eldercarer-end").attr('value',cache[0].end);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
        }
    });
  },
  buttonclk:function(){
    eldercare.obj.staff_id=eldercare.sid;
    eldercare.obj.end_date=$("#eldercarer-end").val();
    var nodes = $('#elderchecktree').tree('getChecked', ['checked','indeterminate']);
    if(nodes){
      eldercare.obj.elder_ids=[];
      for (var i  in nodes){
          eldercare.obj.elder_ids.push(nodes[i].id);
        }
      var infoUrl=rhurl.origin+'/gero/'+gid+'/carework'+eldercare.carework_id;
        $.ajax({
            url: infoUrl, 
            type:eldercare.method, 
            data:JSON.stringify(eldercare.obj), 
            dataType:"json",
            contentType: "application/json;charset=utf-8",
            timeout:deadtime,
            error: function(XMLHttpRequest, textStatus, errorThrown){
                leftTop.dealerror(XMLHttpRequest, textStatus, errorThrown);
            }, 
            success: function(result){
                eldercare.drawElderCareList();
            } 
        }); 
      }
  }
}