//删除$("#topNavi").children().filter('li').remove();
var toptree=[];
var lefttree=[];
var righttemp;
var temptree;
var temptree2;
var hrefTable=[];
var sex=["男","女"];
var  sexc=[];
var showDate = new Date();
alert(showDate.toLocaleDateString().replace(/\//g,'-'));
// myDate.getFullYear();    //获取完整的年份(4位,1970-????)
// myDate.getMonth();       //获取当前月份(0-11,0代表1月)
// myDate.getDate();        //获取当前日(1-31)
// myDate.getDay();         //获取当前星期X(0-6,0代表星期天)
// myDate.toLocaleDateString();     //获取当前日期
sexc["男"]=0;
sexc["女"]=1;
hrefTable['/gero/1/elder']='elder.drawElderList()';
hrefTable['/gero/1/staff']='staff.drawStaffList()';
hrefTable['/gero/1/schedule']='staff.drawScheduleList()';
hrefTable['/item']='item.drawItemList()';
hrefTable['/gero/1/care_item']='geroItem.drawGeroItemList()';
hrefTable['/gero/1/area_item']='geroItem.drawGeroItemList()';
hrefTable['/gero/1/role']='role.drawGeroRoleList()';
hrefTable['/users/test']='authority.drawAuthorityList()';
hrefTable['/gero/1/schedule']='arrange.drawArrangeList()';

var leftTop = {
    removeLefttree:function (){
     $("#lefttree li").remove();
    },
    
    dealdata:function(msg){
        if (msg.status===400)
            window.location = "www.baidu.com" ;
        else if(msg.status===500)
            window.location = "www.baidu.com" ;
        else if(msg.status===200)
        {
            return msg.entities[0];
        }
    },

    findTreeChildren:function(id){
        var result=[];
        for(var i in temptree){
            if(temptree[i].parent_id===id){
                result.push(temptree[i]);
            }
        }
        return result;
    },

    createTreeNode:function(node){
        this.id=node.id;
        this.text=node.name;
        this.children=[];
        this.attributes={"href":node.href,"permission":node.permission,"notes":node.notes}
        this.iconCls=node.icon;
    },

    createTreeData:function(node){
        var result=[];
        var childs= leftTop.findTreeChildren(node.id);
        if (childs.length!==0){
            for(var i in childs){
                var temp= new leftTop.createTreeNode(childs[i]);
                if (leftTop.findTreeChildren(childs[i].id).length!==0){
                    temp.children=leftTop.createTreeData(childs[i]);
                    result.push(temp);
                }
                else if(childs[i].href!=="")
                {
                    result.push(temp);
                }
            }
        }
        return result;
    },

    createTreeData2:function(node){
        var result=[];
        var childs= leftTop.findTreeChildren(node.id);
        if (childs.length!==0){
            for(var i in childs){
                var temp= new leftTop.createTreeNode(childs[i]);
                if (leftTop.findTreeChildren(childs[i].id).length!==0){
                    temp.children=leftTop.createTreeData2(childs[i]);
                }
                result.push(temp);
            }
        }
        return result;
    },

    dealtree:function(msg){
        toptree = leftTop.findTreeChildren(0);
        for(var i in toptree){
            $("#topNavi").append('<li class="navli-a" ><a href="#">'+toptree[i].name+'<a></li>');
        }
        temptree2=[{"id":0,"text":"权限列表","children":[]}]
        temptree2[0].children=leftTop.createTreeData2(temptree2[0]);
        $("#authoritychecktree").tree("loadData",temptree2);
        return leftTop.createTreeData(toptree[0]);
    },

    findTopNode:function(name){
        for(var i in toptree){
            if (toptree[i].name===name){
                return toptree[i];
            }
        }
    },

    findNode:function(id){
        for(var i in temptree){
            if (temptree[i].id===id){
                return temptree[i];
            }
        }
    }
};

//初始化运行所有js的地方
$(function(){
    //$("#rightNavi").load("elder.html");
    //$("#rightNavi").load('elderInfo.html');
    $("#lefttree").tree({
        onClick:function(node){
            var url=leftTop.findNode(node.id).href;
            if (url!==""){
                eval(hrefTable[url]);
            }
        }
    })
    $.ajax({
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
<<<<<<< HEAD
        url: "/user/1",
=======
        url: "/resthouse/api/web/user/1",
>>>>>>> 9384f14d73bd834335c58102eed2b2051ac09d47
        success: function (msg) {
            temptree=msg.entities[0].privilege_list;
            leftTop.removeLefttree;
            var str=leftTop.dealtree(temptree);
            $("#lefttree").tree("loadData",str);
            
        },
        error: function(e) {
            alert(e);
        }
    });
});

$('.navli-a').live('click',function(){
    var str=leftTop.createTreeData(leftTop.findTopNode($(this).text()));
    $("#lefttree").tree("loadData",str);
});

$(".arrange-work").live('click',function(){
        $(this).toggleClass("workday");
    })
