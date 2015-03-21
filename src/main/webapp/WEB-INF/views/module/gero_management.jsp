<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>无标题文档</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/manager.css" rel="stylesheet" type="text/css">
<!---------------------------------响应式------------------------>
<link href="css/conter.css" rel="stylesheet" type="text/css">
<link href="css/jeesite.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<link rel="stylesheet" type="text/css" href="css/icon.css">
</head>

<body>
<!-------------------------头部-------------------------->
<div class="head">
  <div class="container header-s">
    <div class="logo"><img src="images/LOGO.png" ></div>
    <div class="dianhua"><img src="images/d_1.png"></div>
  </div>
</div>
<!--------------------------导航条-------------------------------->
<div class="daohang">
  <div class="container Navigation">
    <ul id="topNavi">
      <li class="navli" href="#">首页</li>
    </ul>
  </div>
</div>


<div class="di">
  <div class="container Information">
  <!--------------------------左导航--------------------------------> 
    <div class="leftNav" id="leftNavi" >
      <div style="background:url(../images/r_1.jpg);line-height:30px;font-size:16px;color: #fff;height:30px;padding-left:20px;font-size:20px;margin:0;text-align:left;">菜单</div>
      <ul id="lefttree" style="padding-left:20px;padding-top:10px;text-align:left;">
      </ul>
    </div>


  <!--------------------------右内容-------------------------------->
    <div class="rightNav" id="rightNavi" style="min-height:710px;">


  <!--------------------------老人列表-------------------------------->
      <div id="eldershow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">老人信息中心</div>
        <div class="old">
          <div class="page-header">老人信息查询:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control" for="name">姓名:</label>
              <div class="col-smm-2">
                <input id="elder_name" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="col-ssm-1" for="name">房间:</label>
              <div class="col-smm-2">
                <input id="elder_bedid" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="col-smm-1" for="name">护理等级:</label>
              <div class="col-smm-2">
                <select id="elder_care_level" class="form-control"  value="">
                </select>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="elder-search" class="btn btn-default" onclick="doSearch()" style="margin-left:30px;" >搜索</button>
              </div>
            </div>
          </div>
          <div class="list" style="min-height:500px">
              <table id="elderpage"  class="easyui-datagrid" title="老人信息列表" style="height:400px;" data-options="onDblClickRow:elder.onElderDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'bed_id',width:120,align:'center'">房 间</th>
                    <th data-options="field:'name',width:120,align:'center'">姓 名</th>
                    <th data-options="field:'identity_no',width:160,align:'center'">身份证号</th>
                    <th data-options="field:'gender',width:80,align:'center'">性 别</th>
                    <th data-options="field:'age',width:80,align:'center'">年 龄</th>
                    <th data-options="field:'care_level',width:120,align:'center'">护理等级</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>





      <!--------------------------员工列表-------------------------------->
      <div id="staffshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">员工信息中心</div>
        <div class="old">
          <div class="page-header">员工信息查询:</div>
          <div class="Inquiry">
            <div class="form-group group">
              <label class="control">姓名:</label>
              <div class="col-smm-2">
                <input id="elder_name" class="form-control"  value=""></input>
              </div>
            </div>
            <div class="form-group group">
              <label class="col-smm-1">角色:</label>
              <div class="col-smm-2">
                <select id="elder_care_level" class="form-control"  value="">
                </select>
              </div>
            </div>
            <div class="form-group group"> 
              <div class="col-md-offset-2">
                <button id="elder-search" class="btn btn-default" onclick="doSearch()" style="margin-left:30px;" >搜索</button>
              </div>
            </div>
          </div>
          <div class="list" style="min-height:500px">
              <table id="staffpage"  class="easyui-datagrid" title="员工信息列表" style="height:400px;" data-options="onDblClickRow:staff.onStaffDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'role',width:100,align:'center'">角色</th>
                    <th data-options="field:'name',width:100,align:'center'">姓 名</th>
                    <th data-options="field:'identity_no',width:160,align:'center'">身份证号</th>
                    <th data-options="field:'gender',width:80,align:'center'">性 别</th>
                    <th data-options="field:'phone',width:100,align:'center'">电 话</th>
                    <th data-options="field:'email',width:120,align:'center'">邮 箱</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>





      <!--------------------------排班-------------------------------->
      <div id="arrangeshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">员工排班</div>
        <div class="old">
          <div id="calendar" class="fc fc-ltr fc-unthemed">
            <div class="fc-toolbar">
              <div class="fc-left">
                <div class="fc-button-group">
                  <button type="button" class="fc-prev-button fc-state-default"><span class="fc-icon fc-icon-left-single-arrow"></span></button>
                  <button type="button" class="fc-next-button fc-state-default"><span class="fc-icon fc-icon-right-single-arrow"></span></button>
                </div>
                <button type="button" class="fc-today-button fc-state-default ">今天</button>
              </div>
              <div class="fc-center"><h2>2015年2月9 — 15日</h2></div>
            </div>
            <div class="fc-view-container" >
              <div class="fc-view" >
                <table>
                  <thead >
                    <tr>
                      <th class="fc-name" >姓名</th>
                      <th class="fc-sun">周日 2/9</th>
                      <th class="fc-mon">周一 2/10</th>
                      <th class="fc-tue">周二 2/11</th>
                      <th class="fc-wed">周三 2/12</th>
                      <th class="fc-thu">周四 2/13</th>
                      <th class="fc-fri">周五 2/14</th>
                      <th class="fc-sat">周六 2/15</th>
                    </tr>
                  </thead>
                  <tbody class="fc-body">
                      <tr>
                        <td class="fc-name"><span>xxx</span></td>
                        <td class="arrange-work workday"></td>
                        <td class="arrange-work"></td>
                        <td class="arrange-work"></td>
                        <td class="arrange-work"></td>
                        <td class="arrange-work"></td>
                        <td class="arrange-work"></td>
                        <td class="arrange-work"></td>
                      </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>






    <!--------------------------院方角色列表-------------------------------->
      <div id="geroscheduleshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院角色管理</div>
        <div class="old">
          <div class="list" style="min-height:300px;padding-top:10px;">

          </div>
        </div>
      </div>



      <!--------------------------项目列表-------------------------------->
      <div id="itemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">项目总表</div>
        <div class="old">
          <div class="list" style="min-height:280px;padding-top:10px;">
              <table id="careitempage"  class="easyui-datagrid" title="专护项目列表" style="height:270px;" data-options="onDblClickRow:item.onCareDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'care_level',width:100,align:'center'">护理等级</th>
                    <th data-options="field:'notes',width:300,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
          <div class="list" style="min-height:300px">
              <table id="areaitempage"  class="easyui-datagrid" title="房护项目列表" style="height:270px;" data-options="onDblClickRow:item.onAreaDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'notes',width:400,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>

      <!--------------------------院方项目列表-------------------------------->
      <div id="geroitemshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院项目管理</div>
        <div class="old">
          <div class="list" style="min-height:280px;padding-top:10px;">
              <table id="geroitempage"  class="easyui-datagrid" title="项目列表" style="height:270px;" data-options="onDblClickRow:geroItem.onCareDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">名 称</th>
                    <th data-options="field:'care_level',width:100,align:'center'">护理等级</th>
                    <th data-options="field:'notes',width:300,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>

      <!--------------------------院方角色列表-------------------------------->
      <div id="geroroleshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">养老院角色管理</div>
        <div class="old">
          <div class="list" style="min-height:300px;padding-top:10px;">
              <table id="gerorolepage"  class="easyui-datagrid" title="角色列表" style="height:300px;width:500px;" data-options="onDblClickRow:role.onRoleDblClickRow">
                <thead>
                  <tr>
                    <th data-options="field:'id',hidden:true,align:'center'">标识号</th>
                    <th data-options="field:'name',width:140,align:'center'">角色名</th>
                    <th data-options="field:'notes',width:300,align:'center'">说 明</th>
                  </tr>
                </thead>
              </table>
          </div>
        </div>
      </div>



      <!--------------------------权限列表-------------------------------->
      <div id="authorityshow" class="inf hide" style="min-height:700px;">
        <div class="pers-s">权限中心</div>
        <div class="old">
          <div  id="authoritypanel" class="easyui-panel" title="权限列表" style="height:500px;width:500px;padding:10px;margin:0;overflow:scroll;" data-options="
            width:500,
            tools: [{ 
                iconCls: 'icon-add', 
                handler: function() { 
                  authority.addTreenode();
                } 
            }, '-',{ 
                iconCls: 'icon-remove', 
                handler: function(){ 
                  authority.delTreenode();
                }
            }]">
              <ul id="authoritytree" style="padding-left:20px;text-align:left;">
              </ul>
          </div>
          <div id="authorityInfo" class="hide">
              <li>name: <input type="text" id="pname" ></input></li>
              <li>permission: <input type="text" id="ppermission"></input></li>
              <li>href: <input type="text" id="phref"></input></li>
              <li>icon: <input type="text" id="picon"></input></li>
              <li>notes: <input type="text" id="pnotes"></input></li>
              <div class="col-md-offset-2">
                <button id="authoritybutton" class="btn btn-default" onclick="authority.buttonclk()" style="margin-left:180px;margin-top:10px;" >确定</button>
              </div>
          </div>
        </div>
      </div>


    </div><!--rightNav-->
  </div><!--inforamtion-->
</div><!--di-->

<!-----------------------老人个人信息------------------------------>
<div id="elder-dialog-form"  class="easyui-dialog" title="老人信息详情" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        toolbar: [{
          text:'修改',
          iconCls:'icon-edit',
          handler:function(){
            elder.editElderInfo();
          }
        }],
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            if(elder.method==='put' || elder.method==='post'  )
            {
              elder.buttonclk();
            }
            else $('#elder-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="elder-Info-card" class="info-card">
      <div id="elder-Info-card-a" class="info-card-a">
        <ul>
          <li>姓    名: <input id="ename"></input></li>
          <li>出生日期：<input id="ebirthday"></input></li>
          <li>性    别：<input id="egender"></input></li>
          <li>民    族：<input id="enationality"></input></li>
          <li>籍    贯：<input id="enative_place"></input></li>
          <li>护理等级：<input id="ecare_level"></input></li>
          <li>户口所在地：<input id="eresidence"></input></li>
          <li>政治面貌：<input id="epolitical_status"></input></li>
          <li>教育水平：<input id="eeducation"></input></li>
          <li>身份证号：<input id="eidentity_no"></input></li>
          <li>社保卡号：<input id="enssf_id"></input></li>
          <li>档案编号：<input id="earchive_id"></input></li>
          <li>入住床号：<input id="earea_id"></input></li>
          <li>联系人电话：<input id="ephone"></input></li>
          <li>家庭地址：<input id="eaddress"></input></li>
        </ul>
      </div>
      <div id="elder-Info-card-b" class="info-card-b"><img src="images/p_2.jpg"></div>
    </div>
</div>

<!-----------------------员工个人信息------------------------------>
<div id="staff-dialog-form"  class="easyui-dialog" title="员工信息详情" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        toolbar: [{
          text:'修改',
          iconCls:'icon-edit',
          handler:function(){
            staff.editStaffInfo();
          }
        }],
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            if(staff.method==='put' || staff.method==='post'  )
            {
              staff.buttonclk();
            }
            else $('#staff-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="staff-Info-card" class="info-card ">
      <div id="staff-Info-card-a" class="info-card-a">
        <ul>
          <li>姓    名: <input id="sname"></input></li>
          <li>电    话: <input id="sphone"></input></li>
          <li>电子邮箱：<input id="semail"></input></li>
          <li>角    色：<input id="srole_list"></input></li>
          <li>出生日期：<input id="sbirthday"></input></li>
          <li>身份证号：<input id="sidentity_no"></input></li>
          <li>性    别：<input id="sgender"></input></li>
          <li>户口所在地：<input id="sresidence_address"></input></li>
          <li>档案编号：<input id="sarchive_id"></input></li>
          <li>社保卡号：<input id="snssf_id"></input></li>
          <li>居住地址：<input id="shousehold_address"></input></li>
        </ul>
      </div>
      <div id="staff-Info-card-b"class="info-card-b"><img src="images/p_2.jpg"></div>
    </div>
</div>

<!-----------------------角色权限信息------------------------------>
<div id="role-dialog-form"  class="easyui-dialog" title="角色信息" style="width:700px;height:600px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-700)*0.5,
        top:($(window).height()-700)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            role.buttonclk();
            $('#role-dialog-form').dialog('close');
          }
        }]
      ">
    <div id="authority-Info-card" class="info-card">
      <ul id="authoritychecktree" class="easyui-tree" style="padding-left:20px;text-align:left;" data-options="lines:true,checkbox:true">
      </ul>
    </div>    
</div>

<div id="rolepost-dialog-form"  class="easyui-dialog" title="角色信息" style="width:300px;height:200px;padding:10px"
      data-options="
        modal:true,
        closed:true,
        fix:true,
        left:($(window).width()-300)*0.5,
        top:($(window).height()-200)*0.5,
        draggable:true,
        iconCls: 'icon-save',
        buttons: [{
          text:'确定',
          iconCls:'icon-ok',
          handler:function(){
            role.postrole();
            $('#rolepost-dialog-form').dialog('close');
          }
        }]
      ">
      <p>角色名: <input type="text" id="rname" /></p>
      <p>说  明: <input type="text" id="rnotes" /></p>  
</div>


<script type="text/javascript" src="js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script  type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/topleftNavi.js"></script>
<script type="text/javascript" src="js/elder.js"></script>
<script type="text/javascript" src="js/staff.js"></script>
<script type="text/javascript" src="js/item.js"></script>
<script type="text/javascript" src="js/geroitem.js"></script>
<script type="text/javascript" src="js/role.js"></script>
<script type="text/javascript" src="js/authority.js"></script>
<script type="text/javascript" src="js/arrange.js"></script>
</body>
</html>
